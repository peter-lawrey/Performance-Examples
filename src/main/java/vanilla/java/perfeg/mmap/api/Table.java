package vanilla.java.perfeg.mmap.api;

import java.io.IOException;

/**
 * @author plawrey
 */
public interface Table {
    public int size();

    public Row createRow();

    public Column acquireColumn(String name);

    public void close() throws IOException;
}
