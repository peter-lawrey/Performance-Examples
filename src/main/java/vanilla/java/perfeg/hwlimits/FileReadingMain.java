package vanilla.java.perfeg.hwlimits;

import java.io.File;
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
 */
public class FileReadingMain {
    public static void main(String... memorySize) throws ExecutionException, InterruptedException, IOException {
        long sizeGB = memorySize.length > 0 ? Integer.parseInt(memorySize[0]) : 32;
        File tmpFile = File.createTempFile("deleteme", "dat");
        tmpFile.deleteOnExit();

        System.out.println("Writing temporary file of " + sizeGB + " GB");
        FileChannel fc = new FileOutputStream(tmpFile).getChannel();
        ByteBuffer bb = ByteBuffer.allocateDirect(64 * 1024);
        for (long i = 0; i < sizeGB << 30; i += bb.capacity()) {
            bb.clear();
            fc.write(bb);
        }
        fc.close();
        System.out.println("... done");

        List<Callable<ByteBuffer>> runs = new ArrayList<Callable<ByteBuffer>>();
        for (int i = -4; i <= 4; i++)
            for (int j = -4; j <= 4; j++) {
                runs.add(new Callable<ByteBuffer>() {
                    @Override
                    public ByteBuffer call() throws Exception {
                        if (true) throw new Error("Not complete");
                        return null;
                    }
                });
            }
        for (int i = 0; i < 3; i++)
            report("File reading scalability", runTests(runs));
    }
}
