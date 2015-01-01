package vanilla.java.unsafe;

import net.openhft.chronicle.Chronicle;
import net.openhft.chronicle.ExcerptAppender;
import net.openhft.chronicle.VanillaChronicle;

import java.io.IOException;

public class ChronicleQueueWriterMain {
    public static void main(String... ignored) throws IOException {
        Chronicle chronicle = new VanillaChronicle("/tmp/chronicle");
        ExcerptAppender appender = chronicle.createAppender();

        int runs = 1000000;
        long start = System.nanoTime();
        for (int i = 0; i < runs; i++) {
            appender.startExcerpt();
            appender.writeLong(System.nanoTime());
            appender.writeLong(0L);
            appender.writeLong(i);
            appender.writeUTFΔ("Hello World");
            appender.finish();
        }
        long time = System.nanoTime() - start;
        System.out.printf("Took an average of %,d ns%n", time / runs);
        chronicle.close();
    }
}
