package vanilla.java.perfeg.mmap;

import org.junit.Test;
import vanilla.java.perfeg.mmap.oop.OopTable;

import java.io.File;
import java.io.IOException;

/**
 * @author plawrey
 */
public class PerformanceTest {
    static final String TMP = System.getProperty("java.io.tmpdir");

    @Test
    public void testOops() throws IOException {
        long start = System.nanoTime();
        OopTable table = new OopTable(TMP);
        new File(TMP, "data").deleteOnExit();
        DataGenerator.generateDataFor(table, 10 * 1000 * 1000);
        table.close();
        long time = System.nanoTime() - start;
        System.out.printf("OOPS: Took %.3f seconds to generate and save %,d entries%n", time / 1e9, table.size());
    }
}
