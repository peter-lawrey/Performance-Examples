package vanilla.java.unsafe;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptTailer;
import net.openhft.chronicle.VanillaChronicle;

import java.io.IOException;

public class ChronicleQueueReaderMain {
    public static void main(String... ignored) throws IOException, InterruptedException {
        Chronicle chronicle = new VanillaChronicle("/tmp/chronicle");
        ExcerptTailer tailer = chronicle.createTailer();

        int runs = 0;
        long start = System.nanoTime(), lastTime = 0;
        while (tailer.nextIndex()) {
            long time = tailer.readLong();
/*
            long delay = Math.max(100, time - lastTime);
            if (delay > 0)
                Thread.sleep(delay);
            lastTime = time;
*/

            if (tailer.compareAndSwapLong(tailer.position(), 0, System.nanoTime())) {
                // mine !!!!
//                tailer.readLong();
                long num = tailer.readLong();
                String str = tailer.readEnum(String.class);
                runs++;
            }
//            tailer.finish();
        }
        long time = System.nanoTime() - start;
        System.out.printf("Took an average of %,d ns%n", time / runs);
        chronicle.close();
    }
}
