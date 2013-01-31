package vanilla.java.perfeg.hwlimits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.*;

/*
Data copy scalability for 64 x 8 KB
threads	percentage speed (1 == 100%)
  1	100%
  2	167%
  4	178%
  8	148%
 16	137%
 32	125%
Data copy scalability for 64 x 1 MB
threads	percentage speed (1 == 100%)
  1	100%
  2	133%
  4	145%
  8	136%
 16	138%
 32	139%

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
        List<Callable<long[]>> runsS = new ArrayList<Callable<long[]>>();
        for (int i = 0; i <= 1024 * 1024; i++) {
            final long[] data = new long[8]; // 64-byte cache line.
            runsS.add(new Callable<long[]>() {
                @Override
                public long[] call() throws Exception {
                    return data.clone();
                }
            });
        }

        List<Callable<long[]>> runsM = new ArrayList<Callable<long[]>>();
        for (int i = 0; i <= 8 * 1024; i++) {
            final long[] data = new long[1024]; // 8 KB.
            runsM.add(new Callable<long[]>() {
                @Override
                public long[] call() throws Exception {
                    return data.clone();
                }
            });
        }

        List<Callable<long[]>> runsL = new ArrayList<Callable<long[]>>();
        for (int i = 0; i <= 64; i++) {
            final long[] data = new long[128 * 1024]; // 1 MB.
            runsL.add(new Callable<long[]>() {
                @Override
                public long[] call() throws Exception {
                    return data.clone();
                }
            });
        }
        long[] sumS = null, sumM = null, sumL = null;
        for (int i = 0; i < 5; i++) {
            sumS = add(sumS, runTests(runsS));
            sumM = add(sumM, runTests(runsM));
            sumL = add(sumL, runTests(runsL));
            // re-arrange memory.
            System.gc();
        }
        report("Data copy scalability for 64 MB as 64 B", sumS);
        report("Data copy scalability for 64 MB as 8 KB", sumM);
        report("Data copy scalability for 64 MB as 1 MB", sumL);
    }
}
