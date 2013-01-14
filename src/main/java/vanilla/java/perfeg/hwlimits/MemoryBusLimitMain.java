package vanilla.java.perfeg.hwlimits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.report;
import static vanilla.java.perfeg.hwlimits.TestPools.runTests;

/**
 * Data copy scalability
 * threads	percentage speed (1 == 100%)
 * 1	100%
 * 2	142%
 * 4	132%
 * 8	125%
 * 16	113%
 * 32	102%
 */
public class MemoryBusLimitMain {
    public static void main(String... ignored) throws ExecutionException, InterruptedException {
        List<Callable<long[]>> runs = new ArrayList<Callable<long[]>>();
        for (int i = 0; i <= 64; i++) {
            final long[] data = new long[1024]; // 8 KB.
            runs.add(new Callable<long[]>() {
                @Override
                public long[] call() throws Exception {
                    return data.clone();
                }
            });
        }
        for (int i = 0; i < 3; i++)
            report("Data copy scalability for 64 x 8KB", runTests(runs));

        List<Callable<long[]>> runs2 = new ArrayList<Callable<long[]>>();
        for (int i = 0; i <= 64; i++) {
            final long[] data = new long[128 * 1024]; // 1 MB.
            runs2.add(new Callable<long[]>() {
                @Override
                public long[] call() throws Exception {
                    return data.clone();
                }
            });
        }
        for (int i = 0; i < 3; i++)
            report("Data copy scalability for 64 x 1 MB", runTests(runs));
    }
}
