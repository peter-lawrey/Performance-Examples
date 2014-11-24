package vanilla.java.unsafe;

import net.openhft.lang.io.DirectBytes;
import net.openhft.lang.io.MappedStore;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MemoryMappingMain {
    public static void main(String... ignored) throws IOException {

        File file = new File("/tmp/shared");
        MappedStore ms = new MappedStore(file, FileChannel.MapMode.READ_WRITE, 4L * 1024 * 1024 * 1024);
        DirectBytes bytes = ms.bytes();
//        bytes.writeLong(0L, 1234);
        System.out.println("value=" + bytes.readLong(0L));
        System.in.read();
        ms.free();
    }
}
