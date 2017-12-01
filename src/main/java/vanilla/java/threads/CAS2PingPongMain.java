package vanilla.java.threads;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class CAS2PingPongMain {
    final AtomicBoolean toggle = new AtomicBoolean();
    final AtomicLong count1 = new AtomicLong();
    final AtomicLong count2 = new AtomicLong();
    byte[] bytes1 = new byte[64];
    byte[] bytes2 = new byte[64];

    public static void main(String... args) throws InterruptedException {
        CAS2PingPongMain ppm = new CAS2PingPongMain();

        Thread t1 = ppm.createThread(true, ppm.count1);
        Thread t2 = ppm.createThread(false, ppm.count2);
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        t1.interrupt();
        t2.interrupt();
        long time = System.currentTimeMillis() - start;
        t1.join();
        t2.join();
        System.out.printf("Counted to %,d toggles per second.%n",
                (ppm.count1.get() + ppm.count2.get()) * 1000 / time);
    }

    @NotNull
    Thread createThread(boolean flag, AtomicLong count) {
        Thread thread = new Thread(() -> runLoop(flag, count), "toggle-" + flag);
        thread.start();
        return thread;
    }

    void runLoop(boolean flag, AtomicLong count) {
        while (!Thread.currentThread().isInterrupted()) {
            if (toggle.compareAndSet(!flag, flag)) {
//                count.getAndUpdate(l -> (l + 1) % 24);
                count.incrementAndGet();
            }
        }
        System.out.println(flag + " - finished");
    }
}
