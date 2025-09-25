package academy.prog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonMessages {
    private final List<Message> list = new ArrayList<>();
    private int newIndex;

    public JsonMessages() {
    }

    public JsonMessages(int newIndex) {
        this.newIndex = newIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public JsonMessages(List<Message> sourceList, int fromIndex) {
        for (int i = fromIndex; i < sourceList.size(); i++)
            list.add(sourceList.get(i));
    }

    public List<Message> getList() {
        return Collections.unmodifiableList(list);
    }

    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }
}
