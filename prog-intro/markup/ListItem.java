package markup;

import java.util.ArrayList;
import java.util.List;

public class ListItem extends AbstractListContainer {
    public ListItem(List<ListItemContent> children) {
        super(new ArrayList<>(children), new StringPair("\\item ", ""));
    }
}
