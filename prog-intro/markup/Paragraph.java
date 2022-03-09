package markup;

import java.util.ArrayList;
import java.util.List;

public class Paragraph extends AbstractContainer implements ListItemContent {
    public Paragraph(List<InnerElement> children) {
        super(new ArrayList<>(children), new StringPair("", ""), new StringPair("", ""));
    }
}
