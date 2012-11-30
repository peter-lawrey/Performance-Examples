package vanilla.java.perfeg.mmap;

import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;
import vanilla.java.perfeg.mmap.api.Table;

import java.util.Random;

/**
 * @author plawrey
 */
public enum DataGenerator {
    ;

    public static void generateDataFor(Table table, int size) {
        Row row = table.createRow();
        Column bidPrice = table.acquireColumn("bidPrice");
        Column askPrice = table.acquireColumn("askPrice");
        Random rand = new Random(size);
        long bp = 1000, ap = 1001;
        int tickSize = 10;
        assert table.size() == 0;

        for (int i = 0; i < size; i++) {
            bp += (rand.nextInt(1) - rand.nextInt(1)) * tickSize;
            ap += (rand.nextInt(1) - rand.nextInt(1)) * tickSize;
            if (ap <= bp) {
                ap += tickSize;
                bp -= tickSize;
            }
            if (ap > bp + 10 * tickSize) {
                if (rand.nextBoolean() || bp < 500)
                    bp += tickSize;
                else
                    ap -= tickSize;
            }
            row.addEntry(i * 10);
            row.set(bidPrice, bp);
            row.set(askPrice, ap);

        }
        assert table.size() == size;
    }
}
