package vanilla.java.perfeg.mmap.api;

/**
 * @author plawrey
 */
public interface Row {
    public void index(int index);

    public int index();

    public boolean nextRecord();

    public long time();

    public long get(Column column);

    void addEntry(long time);

    void set(Column column, long value);
}
