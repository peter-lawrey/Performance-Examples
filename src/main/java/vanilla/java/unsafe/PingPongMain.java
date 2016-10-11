package vanilla.java.unsafe;

import sun.nio.ch.DirectBuffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Created by peter on 16/09/2016.
 */
public class PingPongMain {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("deleteme.too", "rw");
        ByteBuffer bb = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1 << 20).order(ByteOrder.nativeOrder());
        long address = ((DirectBuffer) bb).address();

        boolean odd = args[0].equals("true");
        int from = odd ? 0 : 1;
        int to = odd ? 1 : 0;
        long start = 0;
        for (int count = 0; count <= 10000001; ) {
            if (UnsafeDemo.UNSAFE.compareAndSwapLong(null, address, from, to)) {
                count++;
                if (count == 1) {
                    start = System.nanoTime();
                    System.out.println("started");
                }
            } else {
//                System.out.println(count + ": " + UnsafeDemo.UNSAFE.getLongVolatile(null, address));
            }
        }
        long time = System.nanoTime() - start;
        System.out.printf("Took %.3f seconds%n", time / 1e9);
        System.out.println(bb.getLong(0));
        raf.close();
    }
}
