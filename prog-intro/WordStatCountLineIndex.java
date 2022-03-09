import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordStatCountLineIndex {
    private static String getSeparated(IntListPair arrPair) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arrPair.getFirst().size(); i++) {
            builder.append(arrPair.getFirst().get(i)).append(":").append(arrPair.getSecond().get(i));
            if (i != arrPair.getFirst().size() - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        try {
            Map<String, IntListPair> indexMap = new HashMap<>();
            FastScanner scanner = new FastScanner(new FileInputStream(args[0]), "UTF-8", true,
                    new FastScanner.TokenCharChecker() {

                        @Override
                        public boolean isTokenChar(char c) {
                            return Character.isAlphabetic(c) 
                                || Character.getType(c) == Character.DASH_PUNCTUATION
                                || c == '\'';
                        }
                    });
            try {
                int lineNumber = 1;
                int lineIndex = 1;
                while (scanner.hasNext()) {
                    String next = scanner.next();
                    if (scanner.readLineBreak()) {
                        lineNumber++;
                        lineIndex = 1;
                    } else {
                        next = next.toLowerCase();
                        IntListPair occurences = indexMap.get(next);
                        if (occurences == null) {
                            occurences = new IntListPair();
                            indexMap.put(next, occurences);
                        }
                        occurences.getFirst().add(lineNumber);
                        occurences.getSecond().add(lineIndex);
                        lineIndex++;
                    }
                }
            } finally {
                scanner.close();
            }

            List<Map.Entry<String, IntListPair>> entryList = new ArrayList<>(indexMap.entrySet());
            Collections.sort(entryList, new Comparator<Map.Entry<String, IntListPair>>() {
                @Override
                public int compare(Map.Entry<String, IntListPair> a, Map.Entry<String, IntListPair> b) {
                    IntListPair aPair = a.getValue();
                    IntListPair bPair = b.getValue();
                    int sizeDiff = aPair.getFirst().size() - bPair.getFirst().size();
                    if (sizeDiff == 0) {
                        int lineDiff = aPair.getFirst().get(0) - bPair.getFirst().get(0);
                        if (lineDiff == 0) {
                            return aPair.getSecond().get(0) - bPair.getSecond().get(0);
                        } else {
                            return lineDiff;
                        }
                    } else {
                        return sizeDiff;
                    }
                }
            });

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"));
            String lineBreak = System.lineSeparator();
            try {
                for (Map.Entry<String, IntListPair> entry : entryList) {
                    writer.write(entry.getKey() + " " + entry.getValue().getFirst().size() + " "
                            + getSeparated(entry.getValue()) + lineBreak);
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}