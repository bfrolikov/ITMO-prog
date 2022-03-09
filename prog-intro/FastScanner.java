import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class FastScanner {
    private final BufferedReader reader;
    private final TokenCharChecker checker;
    private final String lineBreak;
    private final boolean needToParseLineBreak;
    private String savedToken;
    private boolean savedTokenEmpty;
    private char lastReadChar;
    private boolean readLineBreak;

    public FastScanner(InputStream input, String encoding, boolean needToParseLineBreak, TokenCharChecker checker)
            throws UnsupportedEncodingException {
        reader = new BufferedReader(new InputStreamReader(input, encoding), 1024);
        this.checker = checker;
        this.needToParseLineBreak = needToParseLineBreak;
        savedTokenEmpty = true;
        lineBreak = System.lineSeparator();
        lastReadChar = (char) 0;
        readLineBreak = false;
    }

    public boolean hasNext() throws IOException {
        return !savedTokenEmpty || parseNextToken();
    }

    public String next() throws IOException {
        if (hasNext()) {
            savedTokenEmpty = true;
            return savedToken;
        } else {
            return null;
        }
    }

    public void close() throws IOException {
        reader.close();
    }

    public boolean readLineBreak() {
        return readLineBreak;
    }

    private boolean parseNextToken() throws IOException {
        StringBuilder nextTokenBuilder = new StringBuilder();
        int c;
        readLineBreak = false;
        while ((c = reader.read()) != -1) {
            if (checkLineBreak((char) c, lastReadChar) && needToParseLineBreak) {
                readLineBreak = true;
                return saveTokenAndReturn(lineBreak);
            }
            while (c != -1 && checker.isTokenChar((char) c)) {
                nextTokenBuilder.append((char) c);
                c = reader.read();
                lastReadChar = (char) c;
            }
            if (nextTokenBuilder.length() > 0) {
                return saveTokenAndReturn(nextTokenBuilder.toString());
            }
            lastReadChar = (char) c;
        }
        return false;
    }

    private boolean checkLineBreak(char c, char cPrev) {
        return lineBreak.equals("\r") && c == '\r' 
            || lineBreak.equals("\n") && c == '\n'
            || lineBreak.equals("\r\n") && cPrev == '\r' && c == '\n';
    }

    private boolean saveTokenAndReturn(String token) {
        savedToken = token;
        savedTokenEmpty = false;
        return true;
    }

    public interface TokenCharChecker {
        boolean isTokenChar(char c);
    }

}
