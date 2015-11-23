package vanilla.java.engine;

import net.openhft.chronicle.engine.api.map.MapView;
import net.openhft.chronicle.engine.api.pubsub.Reference;
import net.openhft.chronicle.engine.api.tree.AssetTree;
import net.openhft.chronicle.engine.tree.VanillaAssetTree;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class MapPubSubMain {
    public static void main(String[] args) {
        AssetTree tree = new VanillaAssetTree("test").forTesting();
        MapView<String, String> map = tree.acquireMap("/group/map", String.class, String.class);
        map.put("one", "hello");
        map.put("two", "bye");
        System.out.println(map);

        Reference<String> three = tree.acquireReference("/group/map/one", String.class);
        three.registerSubscriber(true, 0, s -> System.out.println("one: " + s));
        three.publish("test");
        int len = three.syncUpdate(s -> s + 2, String::length);
        System.out.println("three.length() = " + len);
        three.asyncUpdate(s -> s.substring(0, 4) + " two");
        System.out.println("three.get() = " + three.get());
    }
}
