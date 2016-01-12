package vanilla.java.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
@State(Scope.Thread)
public class TimeBenchmark {
    public static void main(String[] args) throws RunnerException {
        long time = 10;
        Options opt = new OptionsBuilder()
                .include(TimeBenchmark.class.getSimpleName())
                .warmupIterations(6)
                .forks(1)
                .measurementTime(TimeValue.seconds(time))
                .timeUnit(TimeUnit.MICROSECONDS)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public LocalDateTime usingLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inSecs = now.truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime tomorrow = inSecs.plusDays(1);
        return tomorrow;
    }

    @Benchmark
    public long usingLong() {
        long now = System.currentTimeMillis();
        now -= now % 1000;
        now += TimeUnit.DAYS.toMillis(1);
        return now;
    }
}
