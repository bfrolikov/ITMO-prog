package markup;

import java.util.List;

public abstract class AbstractContainer implements Convertable {
    private final List<Convertable> children;
    private final StringPair markdownTags;
    private final StringPair texTags;

    protected AbstractContainer(List<Convertable> children, StringPair markdownTags, StringPair texTags) {
        this.children = children;
        this.markdownTags = markdownTags;
        this.texTags = texTags;
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        s.append(markdownTags.getFirst());
        for (Convertable child : children) {
            child.toMarkdown(s);
        }
        s.append(markdownTags.getSecond());
    }

    @Override
    public void toTex(StringBuilder s) {
        s.append(texTags.getFirst());
        for (Convertable child : children) {
            child.toTex(s);
        }
        s.append(texTags.getSecond());
    }
}
