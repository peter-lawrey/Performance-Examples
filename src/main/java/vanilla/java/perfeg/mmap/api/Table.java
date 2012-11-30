package vanilla.java.perfeg.mmap.api;

import java.io.IOException;

/**
 * @author plawrey
 */
public interface Table {
    public int size();

    public Row createRow();

    public <T> Column<T> acquireColumn(String name, Class<T> type);

    public void close() throws IOException;
}
