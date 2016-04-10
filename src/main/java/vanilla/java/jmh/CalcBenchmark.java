package vanilla.java.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter on 10/04/16.
 */
public class CalcBenchmark {
    static int n = 10000;

    public static void main(String[] args) throws RunnerException, IOException {
        long time = 2;
        Options opt = new OptionsBuilder()
                .include(CalcBenchmark.class.getSimpleName())
                .warmupIterations(6)
                .forks(1)
                .measurementTime(TimeValue.seconds(time))
                .timeUnit(TimeUnit.NANOSECONDS)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public long calc() {
        return (n - 1L) * n / 2 * (n + 1) / 3 * (3 * n + 2) / 2;
    }
}
