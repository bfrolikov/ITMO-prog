package markup;

import java.util.ArrayList;
import java.util.List;

public class UnorderedList extends AbstractListContainer {
    public UnorderedList(List<ListItem> children) {
        super(new ArrayList<>(children), new StringPair("\\begin{itemize}", "\\end{itemize}"));
    }
}
