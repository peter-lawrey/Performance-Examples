package vanilla.java.perfeg.mmap.oop;

import vanilla.java.perfeg.mmap.api.Column;
import vanilla.java.perfeg.mmap.api.Row;
import vanilla.java.perfeg.mmap.api.Table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author plawrey
 */
public class OopTable implements Table {
    final List<RowEntry> list = new ArrayList<RowEntry>();
    final String baseDir;
    final Map<String, Column> columnMap = new LinkedHashMap<String, Column>();

    public OopTable(String baseDir) throws IOException {
        this.baseDir = baseDir;

        File data = new File(baseDir, "data");
        if (data.exists()) {
            FileChannel fc = new FileInputStream(data).getChannel();
            ByteBuffer bb = ByteBuffer.allocateDirect(64 * 1024).order(ByteOrder.nativeOrder());
            while (fc.read(bb) > 0) {
                bb.flip();
                while (bb.remaining() >= RowEntry.SIZE)
                    list.add(new RowEntry(bb));
                bb.compact();
            }
            fc.close();
        }
    }

    {
        columnMap.put("bidPrice", new OopColumn("bidPrice") {
            @Override
            public long get(RowEntry rowEntry) {
                return rowEntry.bidPrice & 0xFFFFFFFFL; // unsigned int
            }

            @Override
            public void set(RowEntry rowEntry, long value) {
                rowEntry.bidPrice = (int) value;
            }
        });
        columnMap.put("askPrice", new OopColumn("askPrice") {
            @Override
            public long get(RowEntry rowEntry) {
                return rowEntry.askPrice & 0xFFFFFFFFL; // unsigned int
            }

            @Override
            public void set(RowEntry rowEntry, long value) {
                rowEntry.askPrice = (int) value;
            }
        });
        columnMap.put("midBP", new OopColumn("midBP") {
            @Override
            public long get(RowEntry rowEntry) {
                return rowEntry.midBP & 0xFFFFFFFFL; // unsigned int
            }

            @Override
            public void set(RowEntry rowEntry, long value) {
                rowEntry.midBP = (int) value;
            }
        });
    }


    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Row createRow() {
        return new OopRow(this);
    }

    @Override
    public <T> Column<T> acquireColumn(String name, Class<T> type) {
        return columnMap.get(name);
    }

    @Override
    public void close() throws IOException {
        new File(baseDir).mkdirs();
        File data = new File(baseDir, "data");
        FileChannel fc = new FileOutputStream(data).getChannel();
        ByteBuffer bb = ByteBuffer.allocateDirect(64 * 1024).order(ByteOrder.nativeOrder());
        for (RowEntry rowEntry : list) {
            if (bb.remaining() < RowEntry.SIZE) {
                bb.flip();
                fc.write(bb);
                bb.compact();
            }
            rowEntry.writeTo(bb);
        }
        bb.flip();
        fc.write(bb);
        fc.close();
    }
}
