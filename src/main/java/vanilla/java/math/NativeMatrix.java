package vanilla.java.math;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by peter.lawrey on 14/01/2016.
 */
public class NativeMatrix {
    static final Unsafe UNSAFE;
    private static final long ROWS_OFFSET = 0L;
    private static final long COLUMNS_OFFSET = 4L;
    private static final long HEADER_SIZE = 8L;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private final long address;
    private final int rows;
    private final int columns;

    public NativeMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        long size = calcSize(rows, columns);
        address = UNSAFE.allocateMemory(size);
        UNSAFE.setMemory(address, size, (byte) 0);
        UNSAFE.putInt(address + ROWS_OFFSET, rows);
        UNSAFE.putInt(address + COLUMNS_OFFSET, columns);
    }

    private long calcSize(int rows, int columns) {
        return HEADER_SIZE + rows * columns * Double.SIZE;
    }

    @Override
    protected void finalize() throws Throwable {
        UNSAFE.freeMemory(address);
    }

    public void set(int row, int column, double value) {
        long index = index(row, column);
        UNSAFE.putDouble(index, value);
    }

    public double get(int row, int column) {
        long index = index(row, column);
        return UNSAFE.getDouble(index);
    }

    private long index(int row, int column) {
        if (row < 0 || row >= rows) throw new IllegalArgumentException();
        if (column < 0 || column >= columns) throw new IllegalArgumentException();
        return HEADER_SIZE + (row * columns + column) * Double.SIZE;
    }
}
