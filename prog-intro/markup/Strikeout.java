package markup;

import java.util.ArrayList;
import java.util.List;

public class Strikeout extends AbstractContainer implements InnerElement {
    public Strikeout(List<InnerElement> children) {
        super(new ArrayList<>(children), new StringPair("~", "~"), new StringPair("\\textst{", "}"));
    }
}
