package markup;

import java.util.ArrayList;
import java.util.List;

public class Strong extends AbstractContainer implements InnerElement {
    public Strong(List<InnerElement> children) {
        super(new ArrayList<>(children), new StringPair("__", "__"), new StringPair("\\textbf{", "}"));
    }
}
