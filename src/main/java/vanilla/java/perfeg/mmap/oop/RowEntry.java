package vanilla.java.perfeg.mmap.oop;

import java.nio.ByteBuffer;

/**
 * @author plawrey
 */
class RowEntry {
    static final int SIZE = 8 + 3 * 4;

    final long time;
    int bidPrice;
    int askPrice;
    int midBP;

    public RowEntry(long time) {
        this.time = time;
    }

    public RowEntry(ByteBuffer bb) {
        time = bb.getLong();
        bidPrice = bb.getInt();
        askPrice = bb.getInt();
        midBP = bb.getInt();
    }

    public void writeTo(ByteBuffer bb) {
        bb.putLong(time);
        bb.putInt(bidPrice);
        bb.putInt(askPrice);
        bb.putInt(midBP);
    }
}
