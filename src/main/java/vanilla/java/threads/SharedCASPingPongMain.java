package vanilla.java.threads;

import net.openhft.chronicle.core.OS;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class SharedCASPingPongMain {
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

    public SharedCASPingPongMain() throws IOException {
        RandomAccessFile raf = new RandomAccessFile("deleteme", "rw");
        MappedByteBuffer map = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, OS.pageSize());
        address = ((DirectBuffer) map).address();
        UNSAFE.putLong(address + COUNTER_OFFSET, 0L);
        UNSAFE.putInt(address + TOGGLE_OFFSET, 0);
    }

    public static void main(String... args) throws InterruptedException, IOException {
        SharedCASPingPongMain wnppm = new SharedCASPingPongMain();
        boolean flag = Boolean.parseBoolean(args[0]);
        Thread t1 = wnppm.createThread(flag);
        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        t1.interrupt();
        long time = System.currentTimeMillis() - start;
        t1.join();
        System.out.println("Counted to " + wnppm.getCount() * 1000 / time + " toggles per second.");
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
