package vanilla.java.engine;

import net.openhft.chronicle.engine.api.map.MapEvent;
import net.openhft.chronicle.engine.api.map.MapView;
import net.openhft.chronicle.engine.api.pubsub.Reference;
import net.openhft.chronicle.engine.api.pubsub.TopicPublisher;
import net.openhft.chronicle.engine.api.tree.AssetTree;
import net.openhft.chronicle.engine.tree.VanillaAssetTree;

import java.io.IOException;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class MapPubSubMain {
    public static void main(String[] args) throws IOException {
        AssetTree tree = new VanillaAssetTree("client").forTesting();
        MapView<String, String> map = tree.acquireMap("/group/map", String.class, String.class);
        map.put("one", "hello");
        map.put("two", "bye");
        System.out.println(map);

        map.registerSubscriber(s -> System.out.println("key: " + s));

        Reference<String> three = tree.acquireReference("/group/map/one", String.class);
        three.registerSubscriber(true, 0, s -> System.out.println("one: " + s));
        three.publish("test");

        long len = three.syncUpdate(s -> s + 2, s -> (long) s.length());
        System.out.println("one.length() = " + len);
        three.asyncUpdate(s -> s.substring(0, 4) + " two");
        System.out.println("one.get() = " + three.get());

        tree.registerSubscriber("/group/map", MapEvent.class, event -> System.out.println("map-event: " + event));

        TopicPublisher<String, String> topicPublisher = tree.acquireTopicPublisher("/group/map", String.class, String.class);
        topicPublisher.publish("three", "333");
        topicPublisher.publish("four", "444");

        topicPublisher.publish("four", "4444");
        map.remove("four");
    }
}
