package vanilla.java;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class LatencyMain {
    public static void main(String... args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int count = 10_000_000;
        for (int i = 0; i < count; i++) {
            Thread.yield();
        }
        long time = System.currentTimeMillis() - start;
        double throughput = count * 1e3 / time;
        System.out.printf("Throughput %.1f per second%n", throughput);
    }
}
