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
/*
# JMH 1.11.2 (released 75 days ago)
# VM version: JDK 1.8.0_60, VM 25.60-b23
# VM invoker: C:\Program Files\Java\jdk1.8.0_60\jre\bin\java.exe
# VM options: -Didea.launcher.port=7533 -Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA Community Edition 14.1.4\bin -Dfile.encoding=windows-1252
# Warmup: 6 iterations, 1 s each
# Measurement: 20 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: vanilla.java.jmh.TimeBenchmark.usingLocalDateTime

# Run progress: 0.00% complete, ETA 00:06:52
# Fork: 1 of 1
# Warmup Iteration   1: 1.316 ops/us
# Warmup Iteration   2: 3.805 ops/us
# Warmup Iteration   3: 4.262 ops/us
# Warmup Iteration   4: 4.183 ops/us
# Warmup Iteration   5: 4.444 ops/us
# Warmup Iteration   6: 4.582 ops/us
Iteration   1: 4.622 ops/us
Iteration   2: 4.669 ops/us
Iteration   3: 4.532 ops/us
Iteration   4: 4.655 ops/us
Iteration   5: 4.491 ops/us
Iteration   6: 4.225 ops/us
Iteration   7: 4.574 ops/us
Iteration   8: 4.593 ops/us
Iteration   9: 4.676 ops/us
Iteration  10: 4.676 ops/us
Iteration  11: 4.641 ops/us
Iteration  12: 4.678 ops/us
Iteration  13: 4.691 ops/us
Iteration  14: 4.673 ops/us
Iteration  15: 4.678 ops/us
Iteration  16: 4.681 ops/us
Iteration  17: 4.576 ops/us
Iteration  18: 4.683 ops/us
Iteration  19: 4.679 ops/us
Iteration  20: 4.678 ops/us


Result "usingLocalDateTime":
  4.619 ±(99.9%) 0.095 ops/us [Average]
  (min, avg, max) = (4.225, 4.619, 4.691), stdev = 0.109
  CI (99.9%): [4.524, 4.713] (assumes normal distribution)


# JMH 1.11.2 (released 75 days ago)
# VM version: JDK 1.8.0_60, VM 25.60-b23
# VM invoker: C:\Program Files\Java\jdk1.8.0_60\jre\bin\java.exe
# VM options: -Didea.launcher.port=7533 -Didea.launcher.bin.path=C:\Program Files (x86)\JetBrains\IntelliJ IDEA Community Edition 14.1.4\bin -Dfile.encoding=windows-1252
# Warmup: 6 iterations, 1 s each
# Measurement: 20 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: vanilla.java.jmh.TimeBenchmark.usingLong

# Run progress: 50.00% complete, ETA 00:03:26
# Fork: 1 of 1
# Warmup Iteration   1: 122.478 ops/us
# Warmup Iteration   2: 122.398 ops/us
# Warmup Iteration   3: 125.233 ops/us
# Warmup Iteration   4: 125.909 ops/us
# Warmup Iteration   5: 125.710 ops/us
# Warmup Iteration   6: 124.877 ops/us
Iteration   1: 125.416 ops/us
Iteration   2: 125.175 ops/us
Iteration   3: 125.472 ops/us
Iteration   4: 125.844 ops/us
Iteration   5: 125.726 ops/us
Iteration   6: 120.867 ops/us
Iteration   7: 125.745 ops/us
Iteration   8: 124.924 ops/us
Iteration   9: 125.814 ops/us
Iteration  10: 125.529 ops/us
Iteration  11: 125.533 ops/us
Iteration  12: 125.703 ops/us
Iteration  13: 125.839 ops/us
Iteration  14: 125.394 ops/us
Iteration  15: 125.790 ops/us
Iteration  16: 125.580 ops/us
Iteration  17: 125.432 ops/us
Iteration  18: 125.378 ops/us
Iteration  19: 125.355 ops/us
Iteration  20: 124.910 ops/us


Result "usingLong":
  125.271 ±(99.9%) 0.931 ops/us [Average]
  (min, avg, max) = (120.867, 125.271, 125.844), stdev = 1.072
  CI (99.9%): [124.340, 126.202] (assumes normal distribution)


# Run complete. Total time: 00:06:53

Benchmark                          Mode  Cnt    Score   Error   Units
TimeBenchmark.usingLocalDateTime  thrpt   20    4.619 ± 0.095  ops/us
TimeBenchmark.usingLong           thrpt   20  125.271 ± 0.931  ops/us

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
