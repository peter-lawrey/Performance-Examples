package vanilla.java.unsafe;

import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Created by peter_2 on 26/11/2014.
 */
public class DirectByteBufferMain {
    public static void main(String[] args) throws IOException {
//        ByteBuffer bb = ByteBuffer.allocateDirect(1 << 30).order(ByteOrder.nativeOrder());
        RandomAccessFile raf = new RandomAccessFile("deleteme", "rw");
        ByteBuffer bb = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1 << 20).order(ByteOrder.nativeOrder());

        long address = ((DirectBuffer) bb).address();
        System.out.printf("address: %x%n", address);
        long l = UnsafeDemo.UNSAFE.getLong(address);
        System.out.printf("value was : %x%n", l);
        UnsafeDemo.UNSAFE.putLong(address, l + 0x101);
        UnsafeDemo.UNSAFE.getLong(0L);

        System.out.printf("value written: %x%n", bb.getLong(0));
    }
}
