package markup;

public interface Convertable {
    void toMarkdown(StringBuilder s);
    void toTex(StringBuilder s);
}
