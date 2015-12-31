package vanilla.java.bits;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.BitSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter on 31/12/15.
 */

@State(Scope.Thread)
public class BitSetScanMain {

    public static final int NBITS = 1_000_000;
    final BitSet bs = new BitSet(NBITS);
    final byte[] bytes = new byte[100000000];

    {
        for (int i = 0; i + i < NBITS; i++) {
            bs.set(i);
            bytes[i] = 1;
        }
    }

    public static void main(String[] args) throws RunnerException {
        long time = 10;
        Options opt = new OptionsBuilder()
                .include(BitSetScanMain.class.getSimpleName())
                .warmupIterations(6)
                .forks(1)
                .mode(Mode.SampleTime)
                .measurementTime(TimeValue.seconds(time))
                .timeUnit(TimeUnit.MICROSECONDS)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public int countBitSet() {
        int count = 0;
        for (int i = 0; (i = bs.nextSetBit(i + 1)) >= 0; ) {
            count += i;
        }
        return count;
    }

    @Benchmark
    public int countBytesSet() {
        int count = 0;
        for (int i = 0, bytesLength = bytes.length; i < bytesLength; i++) {
            byte b = bytes[i];
            if (b == 1)
                count += i;
        }
        return count;
    }
}
