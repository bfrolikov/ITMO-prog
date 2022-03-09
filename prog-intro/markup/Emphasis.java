package markup;

import java.util.ArrayList;
import java.util.List;

public class Emphasis extends AbstractContainer implements InnerElement {
    public Emphasis(List<InnerElement> children) {
        super(new ArrayList<>(children), new StringPair("*", "*"), new StringPair("\\emph{", "}"));
    }
}
