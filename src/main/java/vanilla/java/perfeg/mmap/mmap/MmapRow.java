package vanilla.java.perfeg.mmap.mmap;

import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;

/**
 * @author plawrey
 */
public class MmapRow implements Row {
    private final MmapTable mmapTable;
    private int index = 0;

    public MmapRow(MmapTable mmapTable) {
        this.mmapTable = mmapTable;
    }

    @Override
    public void index(int index) {
        this.index = index;
    }

    @Override
    public int index() {
        return index();
    }

    @Override
    public boolean nextRecord() {
        return index++ < mmapTable.size();
    }

    @Override
    public long time() {
        return mmapTable.time.get(index);
    }

    @Override
    public long get(Column column) {
        return ((MmapTable.MmapColumn) column).get(index);
    }

    @Override
    public void addEntry(long time) {
        index = mmapTable.incrSize();
    }

    @Override
    public void set(Column column, long value) {
        ((MmapTable.MmapColumn) column).set(index, value);
    }
}
