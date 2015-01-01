package vanilla.java;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;

public class OffHeapMapMain {
    public static void main(String... ignored) throws IOException {
        ChronicleMap<String, CharSequence> map = ChronicleMapBuilder
                .of(String.class, CharSequence.class)
                .createPersistedTo(new File("/tmp/shared"));
        map.put("Hello", "world");
        CharSequence s = map.get("Hello");
        System.out.println("s= " + s);
        map.close();
    }
}
