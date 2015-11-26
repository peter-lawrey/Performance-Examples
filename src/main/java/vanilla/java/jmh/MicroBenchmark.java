package vanilla.java.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
@State(Scope.Thread)
public class MicroBenchmark {
    public static void main(String[] args) throws RunnerException {
        long time = 10;
        Options opt = new OptionsBuilder()
                .include(MicroBenchmark.class.getSimpleName())
                .warmupIterations(6)
                .forks(1)
                .mode(Mode.SampleTime)
                .measurementTime(TimeValue.seconds(time))
                .timeUnit(TimeUnit.MICROSECONDS)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public long testMe() {
        LockSupport.parkNanos(1);
        return 0;
    }
}
