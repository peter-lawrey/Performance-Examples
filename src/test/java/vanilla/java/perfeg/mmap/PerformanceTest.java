package vanilla.java.perfeg.mmap;

import org.junit.After;
import org.junit.Test;
import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;
import vanilla.java.perfeg.mmap.api.Table;
import vanilla.java.perfeg.mmap.mmap.MmapTable;
import vanilla.java.perfeg.mmap.oop.OopTable;

import java.io.File;
import java.io.IOException;

/**
 * @author plawrey
 */
public class PerformanceTest {
    static final String TMP = System.getProperty("java.io.tmpdir");

    @After
    public void cleanUp() {
        System.gc();
    }

    @Test
    public void testOops() throws IOException {
        {
            long start = System.nanoTime();
            OopTable table = new OopTable(TMP);
            new File(TMP, "data").deleteOnExit();
            DataGenerator.generateDataFor(table, 30 * 1000 * 1000);
            table.close();
            long time = System.nanoTime() - start;
            System.out.printf("OOPS: Took %.3f seconds to generate and save %,d entries%n", time / 1e9, table.size());
        }
        for (int i = 0; i < 3; i++) {
            long start2 = System.nanoTime();
            OopTable table2 = new OopTable(TMP);
            computeMidPriceBP(table2);
            System.gc();
            table2.close();
            long time2 = System.nanoTime() - start2;
            System.out.printf("OOPS: Took %.3f seconds calculate the mid BP, plus a GC and save %,d entries%n", time2 / 1e9, table2.size());
        }
    }

    @Test
    public void testMmap() throws IOException {
        String dir = TMP + "/mmap";
        {
            long start = System.nanoTime();
            MmapTable table = new MmapTable(dir);
            DataGenerator.generateDataFor(table, 250 * 1000 * 1000);
            table.close();
            long time = System.nanoTime() - start;
            System.out.printf("MMAP: Took %.3f seconds to generate and save %,d entries%n", time / 1e9, table.size());
            deleteOnExit(dir);
        }
        for (int i = 0; i < 3; i++) {
            long start2 = System.nanoTime();
            MmapTable table2 = new MmapTable(dir);
            computeMidPriceBP(table2);
            System.gc();
            table2.close();
            long time2 = System.nanoTime() - start2;
            System.out.printf("MMAP: Took %.3f seconds calculate the mid BP, plus a GC and save %,d entries%n", time2 / 1e9, table2.size());
        }
    }

    private void deleteOnExit(String dir) {
        File file = new File(dir);
        if (file.isDirectory())
            for (File f : file.listFiles()) {
                deleteOnExit(f.toString());
            }
        file.deleteOnExit();
    }

    private void computeMidPriceBP(Table table) {
        Row row = table.createRow();
        Column bidPrice = table.acquireColumn("bidPrice");
        Column askPrice = table.acquireColumn("askPrice");
        Column midBP = table.acquireColumn("midBP");
        long lastMp = (row.get(bidPrice) + row.get(askPrice)) / 2;
        while (row.nextRecord()) {
            long mp = (row.get(bidPrice) + row.get(askPrice)) / 2;
            long mpbp = 10000 * (mp - lastMp) / lastMp;
            row.set(midBP, mpbp);
        }
    }
}
