package vanilla.java.perfeg.hwlimits;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.report;
import static vanilla.java.perfeg.hwlimits.TestPools.runTests;

/**
 * Windows with compressed files
 * File reading scalability
 * threads	percentage speed (1 == 100%)
 * 1	100%
 * 2	133%
 * 4	115%
 * 8	120%
 * 16	138%
 * 32	139%
 */
public class FileReadingMain {
    public static void main(String... memorySize) throws ExecutionException, InterruptedException, IOException {
        long sizeGB = memorySize.length > 0 ? Integer.parseInt(memorySize[0]) : 16;

        System.out.println("Writing temporary files of " + sizeGB + " GB");
        List<Callable<ByteBuffer>> runs = new ArrayList<Callable<ByteBuffer>>();
        for (int i = 0; i <= 64; i++) {
            final File tmpFile = new File("deleteme" + i + ".dat"); //File.createTempFile("deleteme", "dat");
            tmpFile.deleteOnExit();
            createFile(tmpFile, (sizeGB << 30) / 64);
            runs.add(new Callable<ByteBuffer>() {
                @Override
                public ByteBuffer call() throws Exception {
                    FileChannel fc = new FileInputStream(tmpFile).getChannel();
                    ByteBuffer bb = ByteBuffer.allocateDirect(64 * 1024);
                    do {
                        bb.clear();
                    } while (fc.read(bb) > 0);
                    fc.close();
                    return bb;
                }
            });
        }
        System.out.println("... done");

        for (int i = 0; i < 3; i++)
            report("File reading scalability", runTests(runs));
    }

    private static void createFile(File tmpFile, long sizeBytes) throws IOException {
        FileChannel fc = new FileOutputStream(tmpFile).getChannel();
        ByteBuffer bb = ByteBuffer.allocateDirect(64 * 1024);
        for (long i = 0; i < sizeBytes; i += bb.capacity()) {
            bb.clear();
            fc.write(bb);
        }
        fc.close();
    }
}
