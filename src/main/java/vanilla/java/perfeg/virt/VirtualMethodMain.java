package vanilla.java.perfeg.virt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
On A 3 GHz Xeon
1 classes, time in ms: 74.0
2 classes, time in ms: 110.0
2 classes in 2s, time in ms: 47.9
3 classes, time in ms: 736.5
3 classes in 3s, time in ms: 50.0
4 classes, time in ms: 885.5
4 classes in 2s, time in ms: 136.9
4 classes in 4s, time in ms: 46.7
6 classes, time in ms: 861.0
6 classes in 2s, time in ms: 657.6
6 classes in 3s, time in ms: 135.8
6 classes in 6s, time in ms: 50.9
8 classes, time in ms: 975.4
8 classes in 2s, time in ms: 681.1
8 classes in 4s, time in ms: 115.4
8 classes in 8s, time in ms: 53.7
12 classes, time in ms: 858.6
12 classes in 2s, time in ms: 749.4
12 classes in 3s, time in ms: 792.5
12 classes in 4s, time in ms: 523.0
12 classes in 6s, time in ms: 119.4

On an i2.5 GHz i5,
1 classes, time in ms: 48.4
2 classes, time in ms: 115.8
2 classes in 2s, time in ms: 46.6
3 classes, time in ms: 285.3
3 classes in 3s, time in ms: 46.9
4 classes, time in ms: 668.9
4 classes in 2s, time in ms: 80.5
4 classes in 4s, time in ms: 43.7
6 classes, time in ms: 561.6
6 classes in 2s, time in ms: 282.5
6 classes in 3s, time in ms: 92.8
6 classes in 6s, time in ms: 48.7
8 classes, time in ms: 498.4
8 classes in 2s, time in ms: 281.0
8 classes in 4s, time in ms: 87.1
8 classes in 8s, time in ms: 54.9
12 classes, time in ms: 529.6
12 classes in 2s, time in ms: 275.1
12 classes in 3s, time in ms: 275.2
12 classes in 4s, time in ms: 270.7
12 classes in 6s, time in ms: 111.5
 */
public class VirtualMethodMain {
    static long i = 0;

    interface R extends Runnable {
    }

    static class A implements R {
        public void run() {
            i++;
        }
    }

    static class B implements R {
        public void run() {
            i++;
        }
    }

    static class C implements R {
        public void run() {
            i++;
        }
    }

    static class D implements R {
        public void run() {
            i++;
        }
    }

    static class E implements R {
        public void run() {
            i++;
        }
    }

    static class F implements R {
        public void run() {
            i++;
        }
    }

    static class G implements R {
        public void run() {
            i++;
        }
    }

    static class H implements R {
        public void run() {
            i++;
        }
    }

    static class I implements R {
        public void run() {
            i++;
        }
    }

    static class J implements R {
        public void run() {
            i++;
        }
    }

    static class K implements R {
        public void run() {
            i++;
        }
    }

    static class L implements R {
        public void run() {
            i++;
        }
    }

    public static void main(String[] args) {
        int size = 16 * 3;

        Map<Integer, List<R>> lists = new LinkedHashMap<Integer, List<R>>();
        int[] sizes = {1, 2, 3, 4, 6, 8, 12};
        for (int i : sizes) {
            lists.put(i, generateList(size, i));
        }

        for (int j = 0; j < 5; j++) {
            for (Map.Entry<Integer, List<R>> entry : lists.entrySet()) {
                for (int k : sizes) {
                    try {
                        String name = "test" + k + "s" + entry.getKey();
                        Method method = VirtualMethodMain.class.getDeclaredMethod(name, Integer.class, List.class);
                        method.invoke(null, entry.getKey(), entry.getValue());
                    } catch (NoSuchMethodException ignored) {
                    } catch (InvocationTargetException e) {
                        throw new AssertionError(e);
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    }
                }
            }
        }
        System.out.println("i: " + i);
    }

    public static List<R> generateList(int size, int classes) {
        List<R> ret = new ArrayList<R>();
        try {
            for (int i = 0; i < size; i += classes) {
                for (int j = 0; j < classes; j++)
                    ret.add((R) Class.forName(VirtualMethodMain.class.getName() + "$" + (char) ('A' + j)).newInstance());
            }
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        return ret;
    }

    static void test1s1(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test1s2(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test1s3(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test1s4(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test1s6(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test1s8(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test1s12(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i++)
                list.get(i).run();
        System.out.println(classes + " classes, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test2s2(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 2) {
                list.get(i).run();
                list.get(i + 1).run();
            }
        System.out.println(classes + " classes in 2s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test2s4(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 2) {
                list.get(i).run();
                list.get(i + 1).run();
            }
        System.out.println(classes + " classes in 2s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test2s6(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 2) {
                list.get(i).run();
                list.get(i + 1).run();
            }
        System.out.println(classes + " classes in 2s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test2s8(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 2) {
                list.get(i).run();
                list.get(i + 1).run();
            }
        System.out.println(classes + " classes in 2s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test2s12(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 2) {
                list.get(i).run();
                list.get(i + 1).run();
            }
        System.out.println(classes + " classes in 2s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test3s3(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 3) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
            }
        System.out.println(classes + " classes in 3s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test3s6(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 3) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
            }
        System.out.println(classes + " classes in 3s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test3s12(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 3) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
            }
        System.out.println(classes + " classes in 3s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test4s4(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 4) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
                list.get(i + 3).run();
            }
        System.out.println(classes + " classes in 4s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test4s8(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 4) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
                list.get(i + 3).run();
            }
        System.out.println(classes + " classes in 4s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test4s12(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 4) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
                list.get(i + 3).run();
            }
        System.out.println(classes + " classes in 4s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test6s6(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 6) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
                list.get(i + 3).run();
                list.get(i + 4).run();
                list.get(i + 5).run();
            }
        System.out.println(classes + " classes in 6s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test6s12(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 6) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
                list.get(i + 3).run();
                list.get(i + 4).run();
                list.get(i + 5).run();
            }
        System.out.println(classes + " classes in 6s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }

    static void test8s8(Integer classes, List<R> list) {
        long begin = System.nanoTime();
        for (int j = 0; j < 1000000; j++)
            for (int i = 0; i < list.size(); i += 8) {
                list.get(i).run();
                list.get(i + 1).run();
                list.get(i + 2).run();
                list.get(i + 3).run();
                list.get(i + 4).run();
                list.get(i + 5).run();
                list.get(i + 6).run();
                list.get(i + 7).run();
            }
        System.out.println(classes + " classes in 8s, time in ms: " + (System.nanoTime() - begin) / 100000 / 10.0);
    }
}

