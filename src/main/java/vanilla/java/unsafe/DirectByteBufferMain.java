package vanilla.java.unsafe;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by peter_2 on 26/11/2014.
 */
public class DirectByteBufferMain {
    public static void main(String[] args) throws IOException {
        ByteBuffer bb = ByteBuffer.allocateDirect(1 << 30);

/*
        long address = ((DirectBuffer) bb).address();
        System.out.printf("address: %x%n", address);
        long l = UnsafeDemo.UNSAFE.getLong(address);
        UnsafeDemo.UNSAFE.putLong(address, 123456L);
*/

        long l = bb.getLong(0);
        bb.putLong(0, 123456L);

        ByteBuffer bb2 = ByteBuffer.allocateDirect(1 << 30);
        ByteBuffer bb3 = ByteBuffer.allocateDirect(1 << 30);
        ByteBuffer bb4 = ByteBuffer.allocateDirect(1 << 30);
//        for(;;) { }
    }
}
