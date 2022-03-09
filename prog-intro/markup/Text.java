package markup;

public class Text implements InnerElement {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        s.append(text);
    }

    @Override
    public void toTex(StringBuilder s) {
        s.append(text);
    }
}
