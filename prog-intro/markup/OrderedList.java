package markup;

import java.util.ArrayList;
import java.util.List;

public class OrderedList extends AbstractListContainer {
    public OrderedList(List<ListItem> children) {
        super(new ArrayList<>(children), new StringPair("\\begin{enumerate}", "\\end{enumerate}"));
    }

}
