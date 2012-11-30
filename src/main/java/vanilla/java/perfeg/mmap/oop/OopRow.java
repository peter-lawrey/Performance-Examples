package vanilla.java.perfeg.mmap.oop;

import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;

/**
 * @author plawrey
 */
public class OopRow implements Row {
    private final OopTable oopTable;
    private int index = 0;

    public OopRow(OopTable oopTable) {
        this.oopTable = oopTable;
    }

    @Override
    public void index(int index) {
        this.index = index;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public boolean nextRecord() {
        return index++ < oopTable.size();
    }

    @Override
    public long time() {
        return oopTable.list.get(index).time;
    }

    @Override
    public long get(Column column) {
        return ((OopColumn) column).get(oopTable.list.get(index));
    }

    @Override
    public void addEntry(long time) {
        index = oopTable.size();
        oopTable.list.add(new RowEntry(time));
    }

    @Override
    public void set(Column column, long value) {
        ((OopColumn) column).set(oopTable.list.get(index), value);
    }
}
