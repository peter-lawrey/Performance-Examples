package vanilla.java.math;

import net.openhft.chronicle.bytes.NativeBytesStore;

/**
 * Created by peter.lawrey on 14/01/2016.
 */
public class BytesMatrix {
    private static final long MAX_ROWS_OFFSET = 0L;
    private static final long USED_ROWS_OFFSET = MAX_ROWS_OFFSET + Integer.SIZE;
    private static final long COLUMNS_OFFSET = USED_ROWS_OFFSET + Integer.SIZE;
    private static final long HEADER_SIZE = COLUMNS_OFFSET + Integer.SIZE;

    private final NativeBytesStore<Void> bytes;
    private final int maxRows;
    private final int columns;
    private int rowsUsed = 0;

    public BytesMatrix(int maxRows, int columns) {
        this.maxRows = maxRows;
        this.columns = columns;
        long size = calcSize(maxRows, columns);
        bytes = NativeBytesStore.lazyNativeBytesStoreWithFixedCapacity(size);
        bytes.writeInt(MAX_ROWS_OFFSET, maxRows);
        bytes.writeInt(USED_ROWS_OFFSET, 0);
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
        assert row < 0 || row >= maxRows;
        assert column < 0 || column >= columns;
        if (row >= rowsUsed) {
            bytes.zeroOut(rowsUsed * columns * Double.SIZE, (row + 1) * columns * Double.SIZE);
            bytes.writeInt(USED_ROWS_OFFSET, rowsUsed = row + 1);
        }
        return HEADER_SIZE + (row * columns + column) * Double.SIZE;
    }
}
