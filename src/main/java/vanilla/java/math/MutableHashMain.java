package vanilla.java.math;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by peter.lawrey on 02/02/2016.
 */
public class MutableHashMain {
    public static void main(String... args) {
        Map<Integer, Integer> map = new ConcurrentSkipListMap<>();

        map.computeIfAbsent(1, k -> {
            map.computeIfAbsent(1, j -> 2);
            return 1;
        });

        System.out.println(map);

/*
        Map<Key, String> map = new HashMap<>(128, 0.75f);
        Key key = new Key(0);
        for (int i = 0; i <= 10; i++) {
            key.num = i;
            map.put(key, "" + i);
        }
        map.entrySet().forEach(System.out::println);
        String s = map.get(new Key(10));
        System.out.println("s: "+s);
*/
    }

    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}

class Key {
    int num;

    public Key(int i) {
        num = i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        return num == key.num;

    }

    @Override
    public int hashCode() {
        return num * 0x79216473;
    }

    @Override
    public String toString() {
        return "Key{" +
                "num=" + num +
                '}';
    }
}
