package vanilla.java.optimisation;

import net.openhft.koloboke.collect.map.hash.HashIntIntMap;
import net.openhft.koloboke.collect.map.hash.HashIntIntMaps;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

/**
 * Memory optimisation sample program.
 * <p>
 * This is the inefficient version.
 * <p>
 * Created by peter.
 */
public class MemoryOptimisationXMain {
    static final Logger LOGGER = Logger.getLogger(MemoryOptimisationXMain.class.getName());

    public static void main(String... args) {

        for (int i = 0; i < 5; i++) {
            for (int s = 1 << 16; s >= 64; s >>>= 1) {
                ConcurrentMap<Integer, Integer> map = new ConcurrentHashMap<>();
                HashIntIntMap map2 = HashIntIntMaps.newMutableMap(s);
                int[] array = new int[s];
                long t0 = System.nanoTime();
                for (int j = 0; j < 10000000; j += s) {
                    for (int k = 0; k < s; k++) {
                        Integer count = map.getOrDefault(k, 0);
                        map.put(k, count + 1);
                    }
                }
                long t1 = System.nanoTime();
                for (int j = 0; j < 10000000; j += s) {
                    for (int k = 0; k < s; k++) {
                        int count = map2.getOrDefault(k, 0);
                        map2.put(k, count + 1);
                    }
                }
                long t2 = System.nanoTime();
                for (int j = 0; j < 10000000; j += s) {
                    for (int k = 0; k < s; k++) {
                        int count = array[k];
                        array[k] = count + 1;
                    }
                }
                long t3 = System.nanoTime();
                System.out.printf("%,d : %,d vs %,d vs %,d : %.1f vs %.1f %n",
                        s, (t1 - t0) / 1000, (t2 - t1) / 1000, (t3 - t2) / 1000,
                        (double) (t1 - t0) / (t2 - t1), (double) (t2 - t1) / (t3 - t2));
            }
        }
    }
}
