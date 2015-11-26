package vanilla.java.threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class ExecutorServiceMain {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
//        int procs = Runtime.getRuntime().availableProcessors();
//        ExecutorService es = Executors.newCachedThreadPool();
        List<Thread> list = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            list.add(t);
        }
        for (Thread thread : list) {
            thread.join();
        }
        System.out.println("sent");
//        es.shutdown();
//        es.awaitTermination(10, TimeUnit.SECONDS);
        long time = System.currentTimeMillis() - start;
        System.out.printf("Time taken %.3f seconds%n", time / 1e3);
    }
}
