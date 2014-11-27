package vanilla.java.unsafe;

import net.openhft.lang.io.DirectBytes;
import net.openhft.lang.io.MappedStore;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MemoryMappingMain {
    public static void main(String... ignored) throws IOException {
        // this example only works on Linux
        File file = new File("/tmp/shared");
        MappedStore ms = new MappedStore(file, FileChannel.MapMode.READ_WRITE, 64L << 40);
        DirectBytes bytes = ms.bytes();
//        bytes.writeLong(0L, 1234);
        System.out.println("value=" + bytes.readLong(0L));
        long end = System.currentTimeMillis() + 30 * 1000;
        while (end > System.currentTimeMillis()) ;
        ms.free();
    }
}
