package vanilla.java.unsafe;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferMain {
    public static void main(String... ignored) throws IOException {
        String name = "/tmp/deleteme";
        FileChannel fc = new RandomAccessFile(name, "rw").getChannel();
        MappedByteBuffer map = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1 << 30);
//        map.putLong(0, 0x123456789ABCDEFL);
        long value = map.getLong(0);
        System.out.printf("Using 1 GB value= %x%n", value);
        for (; ; ) {
        }
    }
}
