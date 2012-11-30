package vanilla.java.perfeg.mmap;

import org.junit.Test;
import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;
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
        {
            long start = System.nanoTime();
            OopTable table = new OopTable(TMP);
            new File(TMP, "data").deleteOnExit();
            DataGenerator.generateDataFor(table, 10 * 1000 * 1000);
            table.close();
            long time = System.nanoTime() - start;
            System.out.printf("OOPS: Took %.3f seconds to generate and save %,d entries%n", time / 1e9, table.size());
        }
        for (int i = 0; i < 3; i++) {
            long start2 = System.nanoTime();
            OopTable table2 = new OopTable(TMP);
            computeMidPriceBP(table2);
            table2.close();
            long time2 = System.nanoTime() - start2;
            System.out.printf("OOPS: Took %.3f seconds calculate the mid BP and save %,d entries%n", time2 / 1e9, table2.size());
        }

    }

    private void computeMidPriceBP(OopTable table) {
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
