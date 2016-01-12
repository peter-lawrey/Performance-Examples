package vanilla.java.threads;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class LockConditionPingPongMain {
    final ReentrantLock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    boolean toggle = false;
    long count = 0;

    public static void main(String... args) throws InterruptedException {
        LockConditionPingPongMain ppm = new LockConditionPingPongMain();

        Thread t1 = ppm.createThread(true);
        Thread t2 = ppm.createThread(false);
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        t1.interrupt();
        t2.interrupt();
        long time = System.currentTimeMillis() - start;
        t1.join();
        t2.join();
        System.out.println("Counted to " + ppm.count * 1000 / time + " toggles per second.");
    }

    @NotNull
    Thread createThread(boolean flag) {
        Thread thread = new Thread(() -> runLoop(flag), "toggle-" + flag);
        thread.start();
        return thread;
    }

    void runLoop(boolean flag) {
//        Thread.currentThread().setName("toggle-"+flag);
        lock.lock();
        try {
            while (!Thread.currentThread().isInterrupted()) {
                while (toggle == flag) {
                    condition.await();
                }
                toggle = flag;
                count++;
                condition.signalAll();
            }
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
            System.out.println(flag + " - finished");
        }
    }
}
