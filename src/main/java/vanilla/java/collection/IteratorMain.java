package vanilla.java.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class IteratorMain {
    public static void main(String... args) {
        List<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        strings.add("C");
        for (Iterator<String> iterator = strings.iterator(); iterator.hasNext(); ) {
            String string = iterator.next();
            iterator.remove();
        }
    }
}
