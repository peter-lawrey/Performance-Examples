package vanilla.java.threads;

import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class NativeCASPingPongMain {
    static final int SIZE = 16;
    static final int COUNTER_OFFSET = 0;
    static final int TOGGLE_OFFSET = SIZE - 8;

    static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    final long address;

    public NativeCASPingPongMain() {
        address = UNSAFE.allocateMemory(SIZE);
        UNSAFE.putLong(address + COUNTER_OFFSET, 0L);
        UNSAFE.putInt(address + TOGGLE_OFFSET, 0);
    }

    public static void main(String... args) throws InterruptedException {
        NativeCASPingPongMain ppm = new NativeCASPingPongMain();

        Thread t1 = ppm.createThread(true);
        Thread t2 = ppm.createThread(false);
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        t1.interrupt();
        t2.interrupt();
        long time = System.currentTimeMillis() - start;
        t1.join();
        t2.join();
        System.out.printf("Counted to %,d  toggles per second.%n", ppm.getCount() * 1000 / time);
    }

    @NotNull
    Thread createThread(boolean flag) {
        Thread thread = new Thread(() -> runLoop(flag), "toggle-" + flag);
        thread.start();
        return thread;
    }

    void runLoop(boolean flag) {
        while (!Thread.currentThread().isInterrupted()) {
            if (toggleCompareAndSet(!flag, flag))
                countIncrement();
        }
        System.out.println(flag + " - finished");
    }

    public boolean toggleCompareAndSet(boolean from, boolean to) {
        return UNSAFE.compareAndSwapInt(null, address + TOGGLE_OFFSET, from ? 1 : 0, to ? 1 : 0);
    }

    public void countIncrement() {
        UNSAFE.getAndAddLong(null, address + COUNTER_OFFSET, 1);
    }

    public long getCount() {
        return UNSAFE.getLongVolatile(null, address + COUNTER_OFFSET);
    }
}
