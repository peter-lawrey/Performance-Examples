package vanilla.java.perfeg.mmap.oop;

import vanilla.java.perfeg.mmap.api.Column;

/**
 * @author plawrey
 */
public abstract class OopColumn<T> implements Column<T> {
    private final String name;

    public OopColumn(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    public abstract long get(RowEntry rowEntry);

    public abstract void set(RowEntry rowEntry, long value);
}
