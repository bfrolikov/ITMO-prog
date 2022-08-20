package info.kgeorgiy.ja.frolikov.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Crawler implementation.
 * Performs a breadth-first search of the web
 *
 * @author Frolikov Boris
 */
public class WebCrawler implements Crawler {
    private final int perHost;
    private final Set<String> seenUrls = ConcurrentHashMap.newKeySet();
    private final ExecutorService downloadService;
    private final ExecutorService extractService;
    private final Downloader downloader;
    private final Map<String, HostInfo> hostInfoMap = new ConcurrentHashMap<>();

    /**
     * Creates a new {@link WebCrawler} with a {@link CachingDownloader} and constraints set through
     * command line arguments, then runs it starting with the specified URL.
     *
     * @param args command line arguments in the form of
     *             {@code url [depth [downloads [extractors [perHost]]]]}
     */
    public static void main(final String[] args) {
        try {
            if (args == null) {
                throw new ArgumentValidationException("Args can't be null");
            }
            if (args.length == 0) {
                throw new ArgumentValidationException("No arguments were provided");
            }
            if (args[0] == null) {
                throw new ArgumentValidationException("Host can't be null");
            }
            final String host = args[0];
            final int depth = parseNumericArgument(args, "Depth", 1, 1);
            final int downloads = parseNumericArgument(args, "Downloads", 2, 10);
            final int extractors = parseNumericArgument(args, "Exctractors", 3, 10);
            final int perHost = parseNumericArgument(args, "PerHost", 4, 10);
            final Crawler crawler = new WebCrawler(new CachingDownloader(), downloads, extractors, perHost);
            final Result result = crawler.download(host, depth);
            crawler.close();
            System.out.println("Downloaded:");
            for (final String url : result.getDownloaded()) {
                System.out.println(url);
            }
            System.out.println("Errors:");
            for (final Map.Entry<String, IOException> entry : result.getErrors().entrySet()) {
                System.out.printf("%s: %s%n", entry.getKey(), entry.getValue());
            }
        } catch (final ArgumentValidationException e) {
            System.err.println(e.getMessage());
        } catch (final IOException e) {
            System.err.println("Could not initialize downloader: " + e.getMessage());
        }

    }

    /**
     * Constructs an instance of {@link WebCrawler} with a set downloader and constraints.
     *
     * @param downloader  downlnoader to use
     * @param downloaders maximum number of simultaneously loaded pages
     * @param extractors  maximum number of pages from which links are simultaneously extracted
     * @param perHost     maximum number of pages loaded simultaneously from a single host
     */
    public WebCrawler(final Downloader downloader, final int downloaders, final int extractors, final int perHost) {
        downloadService = Executors.newFixedThreadPool(downloaders);
        extractService = Executors.newFixedThreadPool(extractors);
        this.downloader = downloader;
        this.perHost = perHost;
    }

    @Override
    public Result download(final String downloadUrl, final int depth) {
        final Queue<String> downloadedUrls = new ConcurrentLinkedQueue<>();
        final Map<String, IOException> errors = new ConcurrentHashMap<>();
        Queue<String> currentLayer = new ConcurrentLinkedQueue<>();
        currentLayer.add(downloadUrl);
        final Phaser downloadPhaser = new Phaser(1);
        final Phaser extractPhaser = new Phaser(1);
        for (int d = 1; d <= depth; d++) {
            final Queue<String> nextLayer = new ConcurrentLinkedQueue<>();
            for (final String url : currentLayer) {
                if (!seenUrls.contains(url)) {
                    seenUrls.add(url);
                    try {
                        final String host = URLUtils.getHost(url);
                        hostInfoMap.computeIfAbsent(host, h -> new HostInfo());
                        final HostInfo hostInfo = hostInfoMap.get(host);
                        final boolean finalLayer = d == depth;
                        final Runnable downloadTask = () -> {
                            try {
                                final Document res = downloader.download(url);
                                downloadedUrls.add(url);
                                if (!finalLayer) {
                                    extractPhaser.register();
                                    extractService.submit(() -> {
                                        try {
                                            nextLayer.addAll(res.extractLinks());
                                        } catch (final IOException e) {
                                            errors.put(url, e);
                                        }
                                        extractPhaser.arriveAndDeregister();
                                    });
                                }
                            } catch (final IOException e) {
                                errors.put(url, e);
                            } finally {
                                hostInfo.release();
                            }
                            downloadPhaser.arriveAndDeregister();
                        };
                        downloadPhaser.register();
                        hostInfo.connect(downloadTask);
                    } catch (final MalformedURLException e) {
                        errors.put(url, e);
                    }
                }
            }
            downloadPhaser.arriveAndAwaitAdvance();
            extractPhaser.arriveAndAwaitAdvance();
            currentLayer = nextLayer;
        }
        return new Result(new ArrayList<>(downloadedUrls), errors);
    }

    @Override
    public void close() {
        downloadService.shutdown();
        extractService.shutdown();
        if (awaitTermination()) {
            return;
        }
        downloadService.shutdownNow();
        extractService.shutdownNow();
        while (!awaitTermination()) {
        }
    }

    private boolean awaitTermination() {
        try {
            if (downloadService.awaitTermination(60, TimeUnit.SECONDS)
                    && downloadService.awaitTermination(60, TimeUnit.SECONDS)) {
                return true;
            }
        } catch (final InterruptedException e) {
            //Ignored
        }
        return false;
    }

    private final class HostInfo {
        private final Queue<Runnable> buffer = new ArrayDeque<>();
        private int availableConnections = perHost;

        private synchronized void connect(final Runnable task) {
            if (availableConnections > 0) {
                availableConnections--;
                downloadService.submit(task);
            } else {
                buffer.add(task);
            }
        }

        private synchronized void release() {
            if (!buffer.isEmpty()) {
                downloadService.submit(buffer.poll());
            } else {
                availableConnections++;
            }
        }
    }


    private static int parseNumericArgument(final String[] args, final String name, final int position, final int defaultValue)
            throws ArgumentValidationException {
        if (position < args.length) {
            try {
                return Integer.parseInt(args[position]);
            } catch (final NumberFormatException e) {
                throw new ArgumentValidationException("Argument " + name + "is not a valid number", e);
            }
        }
        return defaultValue;
    }

    private static class ArgumentValidationException extends Exception {
        public ArgumentValidationException(final String message) {
            super(message);
        }

        public ArgumentValidationException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }

}
