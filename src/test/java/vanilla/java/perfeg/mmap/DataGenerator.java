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
        Column<Long> bidPrice = table.acquireColumn("bidPrice", long.class);
        Column<Long> askPrice = table.acquireColumn("askPrice", long.class);
        Random rand = new Random(size);
        long bp = 10000, ap = 10001;

        for (int i = 0; i < size; i++) {
            bp += rand.nextInt(1) - rand.nextInt(1);
            ap += rand.nextInt(1) - rand.nextInt(1);
            if (ap <= bp) {
                ap++;
                bp--;
            }
            if (ap > bp + 10) {
                long l = rand.nextBoolean() ? bp++ : ap--;
            }
            row.addEntry(i * 10);
            row.set(bidPrice, bp);
            row.set(askPrice, ap);
        }
    }
}
