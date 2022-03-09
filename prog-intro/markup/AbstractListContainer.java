package markup;

import java.util.List;

public abstract class AbstractListContainer extends AbstractContainer implements ListItemContent{
    protected AbstractListContainer(List<Convertable> children, StringPair texTags) {
        super(children, null, texTags);
    }

    @Override
    public void toMarkdown(StringBuilder s) {
        throw new UnsupportedOperationException("Cannot convert TeX-only element to markdown");
    }
}
