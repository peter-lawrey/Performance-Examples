package vanilla.java.math;

import net.openhft.chronicle.bytes.NativeBytesStore;

/**
 * Created by peter.lawrey on 14/01/2016.
 */
public class BytesMatrix {
    private static final long ROWS_OFFSET = 0L;
    private static final long COLUMNS_OFFSET = ROWS_OFFSET + Integer.SIZE;
    private static final long HEADER_SIZE = COLUMNS_OFFSET + Integer.SIZE;

    private final NativeBytesStore<Void> bytes;
    private final int rows;
    private final int columns;

    public BytesMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        long size = calcSize(rows, columns);
        bytes = NativeBytesStore.nativeStoreWithFixedCapacity(size);
        bytes.writeInt(ROWS_OFFSET, rows);
        bytes.writeInt(COLUMNS_OFFSET, columns);
    }

    public long address() {
        return bytes.address(0);
    }

    private long calcSize(int rows, int columns) {
        return HEADER_SIZE + rows * columns * Double.SIZE;
    }

    public void set(int row, int column, double value) {
        long index = index(row, column);
        bytes.writeDouble(index, value);
    }

    public double get(int row, int column) {
        long index = index(row, column);
        return bytes.readDouble(index);
    }

    private long index(int row, int column) {
        assert row < 0 || row >= rows;
        assert column < 0 || column >= columns;
        return HEADER_SIZE + (row * columns + column) * Double.SIZE;
    }
}
