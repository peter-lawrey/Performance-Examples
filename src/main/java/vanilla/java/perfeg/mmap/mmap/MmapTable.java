package vanilla.java.perfeg.mmap.mmap;

import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;
import vanilla.java.perfeg.mmap.api.Table;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author plawrey
 */
public class MmapTable implements Table {
    static final Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private final Map<String, MmapColumn> colMap = new LinkedHashMap<String, MmapColumn>();
    private final String baseDir;
    private final FileChannel sizeFC;
    private final ByteBuffer sizeMM;
    final MmapColumn time;
    private int size;

    public MmapTable(String baseDir) throws IOException {
        this.baseDir = baseDir;
        new File(baseDir).mkdirs();
        File file = new File(baseDir, ".size");
        sizeFC = new RandomAccessFile(file, "rw").getChannel();
        sizeMM = sizeFC.map(FileChannel.MapMode.READ_WRITE, 0, 4).order(ByteOrder.nativeOrder());
        time = new LongColumn(new File(baseDir, "time"));
        size = sizeMM.getInt(0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Row createRow() {
        return new MmapRow(this);
    }

    @Override
    public Column acquireColumn(String name) {
        try {
            return new IntColumn(new File(baseDir, name));
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Override
    public void close() throws IOException {
        for (MmapColumn mmapColumn : colMap.values()) {
            mmapColumn.close();
        }
    }

    public int incrSize() {
        sizeMM.putInt(0, size + 1);
        return size++;
    }

    private class LongColumn extends MmapColumn {
        public LongColumn(File file) throws IOException {
            super(file, 8);
        }

        @Override
        long get(int index) {
            return unsafe.getLong(address + (index << 3));
        }

        @Override
        void set(int index, long value) {
            unsafe.putLong(address + (index << 3), value);
        }
    }

    private class IntColumn extends MmapColumn {
        public IntColumn(File file) throws IOException {
            super(file, 4);
        }

        @Override
        long get(int index) {
            return unsafe.getLong(address + (index << 2));
        }

        @Override
        void set(int index, long value) {
            unsafe.putLong(address + (index << 2), value);
        }
    }

    abstract class MmapColumn implements Column {
        private final String name;
        private final FileChannel fc;
        protected final ByteBuffer bb;
        protected final long address;
        private final int dataSize;

        MmapColumn(File file, int dataSize) throws IOException {
            this.dataSize = dataSize;
            this.name = file.getName();
            fc = new RandomAccessFile(file, "rw").getChannel();
            int size = Integer.MAX_VALUE / 8 * dataSize;
            bb = fc.map(FileChannel.MapMode.READ_WRITE, 0, size).order(ByteOrder.nativeOrder());
            address = ((DirectBuffer) bb).address();
        }

        @Override
        public String name() {
            return name;
        }

        abstract long get(int index);

        abstract void set(int index, long value);

        void close() throws IOException {
            fc.truncate(size() * dataSize);
            fc.close();
        }
    }
}
