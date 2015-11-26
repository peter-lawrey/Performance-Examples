package vanilla.java;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class LatencyMain {
    public static void main(String... args) throws InterruptedException {
        long start = System.currentTimeMillis();
        int count = 5_000;
        for (int i = 0; i < count; i++) {
            Thread.sleep(1);
        }
        long time = System.currentTimeMillis() - start;
        double average = (double) time / count;
        System.out.printf("Average latency %.3f milli-seconds%n", average);
    }
}
