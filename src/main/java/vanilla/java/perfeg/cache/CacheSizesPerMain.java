package vanilla.java.perfeg.cache;

import java.util.Arrays;
import java.util.concurrent.*;

public class CacheSizesPerMain {
    static final ExecutorService[] pools = new ExecutorService[6];

    static class ScanTask implements Callable<Void> {
        final byte[] in = new byte[8 * 1024 * 1024];
        final byte[] out = new byte[8 * 1024 * 1024];

        @Override
        public Void call() throws Exception {
            double sum = 0;
            for (int i = 0; i < in.length; i++) {
                byte v = in[i];
                sum += out[i] = v;
            }
            if (sum != 0)
                throw new AssertionError();
            return null;
        }
    }

    public static void main(String... ignored) throws InterruptedException, ExecutionException {
        for (int i = 0; i < pools.length; i++)
            pools[i] = Executors.newFixedThreadPool(1 << i);
        Callable[] tasks = new Callable[64];
        for (int i = 0; i < tasks.length; i++)
            tasks[i] = new ScanTask();

        long[] times = new long[pools.length];
        for (int i = 0; i < pools.length; i++) {
            times[i] += timeTasks(pools[i], tasks);
        }

        for (int i = 0; i < pools.length; i++) {
            System.out.printf("%,d threads took %.3f ms%n",
                    1 << i, times[i] / 1e6);
        }
        for (ExecutorService pool : pools) {
            pool.shutdown();
        }
    }

    private static long timeTasks(ExecutorService pool, Callable<Void>[] tasks) throws InterruptedException, ExecutionException {
        long start = System.nanoTime();
        for (Future future : pool.invokeAll(Arrays.asList(tasks))) future.get();
        return System.nanoTime() - start;
    }

    private static long timeCorrel(float[] x, float[] y, int bits) {
        long start = System.nanoTime();
        int repeats = (1 << Math.max(1, 20 - bits));
        for (int r = 0; r < repeats; r++) {
            int count = 1 << (bits - 4); // bits of bytes / (2 * 8) for two floats.
            float sum_x = 0, sum_y = 0, sum_sq_x = 0, sum_sq_y = 0, co_product = 0;
            for (int i = 0; i < count; i++) {
                sum_x += x[i];
                sum_y += y[i];
                sum_sq_x += x[i] * x[i];
                sum_sq_y += y[i] * y[i];
                co_product += x[i] * y[i];
            }

            float correl = (count * co_product - sum_x * sum_y) /
                    (float) Math.sqrt((count * sum_sq_x - sum_x * sum_x) * (count * sum_sq_y - sum_y * sum_y));
            // it should be small, but not too small.
            if (Math.abs(correl) > 0.1 || correl == 0)
                throw new AssertionError("count=" + count + " correl=" + correl);
        }
        return (System.nanoTime() - start) / repeats;
    }

    private static float[] generateRandom(int count) {
        float[] d = new float[count];
        for (int i = 0; i < count; i++) d[i] = (float) Math.random();
        return d;
    }
}
