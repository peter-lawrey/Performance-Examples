package vanilla.java.perfeg.hwlimits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 */
public enum TestPools {
    ;
    private static ExecutorService[] SERVICES = new ExecutorService[6];

    static {
        ThreadFactory deamonThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        };
        for (int i = 0; i < 6; i++) {
            SERVICES[i] = Executors.newFixedThreadPool(1 << i, deamonThreadFactory);
        }
    }

    /**
     * Run the collection of tasks with different numbers of threads and time them.
     *
     * @param runs tasks to run.
     * @return average times.
     */
    public static <V> long[] runTests(List<Callable<V>> runs) throws ExecutionException, InterruptedException {
        long[] times = new long[SERVICES.length];
        long start = System.nanoTime();
        int count = 0;
        do {
            count++;
            for (int i = 0; i < SERVICES.length; i++) {
                long start2 = System.nanoTime();
                List<Future<?>> futures = new ArrayList<Future<?>>();
                for (Callable<V> run : runs)
                    futures.add(SERVICES[i].submit(run));
                for (Future<?> future : futures) {
                    future.get();
                }
                long time2 = System.nanoTime() - start2;
                times[i] += time2;
            }
        } while (System.nanoTime() < start + 5e9);
        for (int i = 0; i < times.length; i++)
            times[i] /= count;
        return times;
    }

    public static void report(String description, long[] times) {
        System.out.println(description);
        System.out.println("threads\tpercentage speed (1 == 100%)");
        for (int i = 0; i < times.length; i++)
            System.out.printf("%3d\t%d%%%n", 1 << i, times[0] * 100 / times[i]);
    }

    public static long[] add(long[] a, long[] b) {
        if (a == null) return b;
        if (b == null) return a;
        long[] ret = new long[Math.min(a.length, b.length)];
        for (int i = 0; i < ret.length; i++)
            ret[i] = a[i] + b[i];
        return ret;
    }
}
