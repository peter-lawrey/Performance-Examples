package vanilla.java.threads;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.MappedBytes;
import net.openhft.chronicle.core.OS;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class ChronicleBytesCASPingPongMain {
    static final int SIZE = 16;
    static final int COUNTER_OFFSET = 0;
    static final int TOGGLE_OFFSET = SIZE - 8;

    final Bytes bytes;

    public ChronicleBytesCASPingPongMain() throws IOException {
        bytes = MappedBytes.mappedBytes("deleteme", OS.pageSize());

        bytes.writeLong(COUNTER_OFFSET, 0L);
        bytes.writeInt(TOGGLE_OFFSET, 0);
    }

    public static void main(String... args) throws InterruptedException, IOException {
        ChronicleBytesCASPingPongMain ppm = new ChronicleBytesCASPingPongMain();
        boolean flag = Boolean.parseBoolean(args[0]);
        for (int i = 0; i < 10; )
            if (ppm.toggleCompareAndSet(!flag, flag))
                i++;
        System.out.println("toggling");

        Thread t1 = ppm.createThread(flag);

        long start = System.currentTimeMillis();
        Thread.sleep(5000);
        t1.interrupt();
        long time = System.currentTimeMillis() - start;
        t1.join();
        System.out.printf("Counted to %,d toggles per second. %n", ppm.getCount() * 1000 / time);
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
        return bytes.compareAndSwapInt(TOGGLE_OFFSET, from ? 1 : 0, to ? 1 : 0);
    }

    public void countIncrement() {
        bytes.addAndGetInt(COUNTER_OFFSET, 1);
    }

    public long getCount() {
        return bytes.readVolatileLong(COUNTER_OFFSET);
    }
}
