package vanilla.java;

import net.openhft.chronicle.core.util.Histogram;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class LatencyMain {
    public static void main(String... args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int count = 500_000;
        Histogram hist = new Histogram();
        for (int i = 0; i < count; i++) {
            long start2 = System.nanoTime();

            doWork(i);

            long time2 = System.nanoTime() - start2;
            hist.sample(time2);
        }

        System.out.println(hist.toMicrosFormat());

        long time = System.currentTimeMillis() - start;
        double average = (double) time / count;
        System.out.printf("Average latency %.3f milli-seconds%n", average);
    }

    private static void doWork(int i) throws InterruptedException {
        Thread.sleep(1);
        if (i % 500 == 0)
            Thread.sleep(10);
    }
}
