package vanilla.java.lambda;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class NestedPutIfAbsentMain {
    public static void main(String[] args) {
        Map<Key, Integer> map = new ConcurrentHashMap<>();
        map.computeIfAbsent(Key.Hello, s -> {
            map.computeIfAbsent(Key.Hello, t -> 1);
            return 2;
        });
        System.out.println(map);
    }

    enum Key {Hello}

}
