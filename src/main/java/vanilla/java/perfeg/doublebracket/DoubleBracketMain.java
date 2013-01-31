package vanilla.java.perfeg.doublebracket;

import java.util.*;

/**
 * @author plawrey
 */
public class DoubleBracketMain {
    public static void main(String... args) {
        final List<String> list1 = new ArrayList<String>() {
            {
                add("Hello");
                add("World");
                add("!!!");
            }
        };
        List<String> list2 = new ArrayList<String>(list1);
        Set<String> set1 = new LinkedHashSet<String>() {
            {
                addAll(list1);
            }
        };
        Set<String> set2 = new LinkedHashSet<String>();
        set2.addAll(list1);
        Map<Integer, String> map1 = new LinkedHashMap<Integer, String>() {
            {
                put(1, "one");
                put(2, "two");
                put(3, "three");
            }
        };
        Map<Integer, String> map2 = new LinkedHashMap<Integer, String>();
        map2.putAll(map1);

        for (int i = 0; i < 10; i++) {
            long dbTimes = timeComparison(list1, list1)
                    + timeComparison(set1, set1)
                    + timeComparison(map1.keySet(), map1.keySet())
                    + timeComparison(map1.values(), map1.values());
            long times = timeComparison(list2, list2)
                    + timeComparison(set2, set2)
                    + timeComparison(map2.keySet(), map2.keySet())
                    + timeComparison(map2.values(), map2.values());
            if (i > 0)
                System.out.printf("double braced collections took %,d ns and plain collections took %,d ns%n", dbTimes, times);
        }
    }

    public static long timeComparison(Collection a, Collection b) {
        long start = System.nanoTime();
        int runs = 1000000;
        for (int i = 0; i < runs; i++)
            compareCollections(a, b);
        long rate = (System.nanoTime() - start) / runs;
        return rate;
    }

    public static void compareCollections(Collection a, Collection b) {
        if (!a.equals(b) && a.hashCode() != b.hashCode() && !a.toString().equals(b.toString()))
            throw new AssertionError();
    }
}
