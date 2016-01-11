package vanilla.java.jmh;

import net.openhft.chronicle.bytes.Bytes;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.*;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
@State(Scope.Thread)
public class GregorianMicroBenchmark {
    Bytes bytes = Bytes.allocateDirect(64).unchecked(true);

    public static void main(String[] args) throws RunnerException, IOException {
        long time = 10;
        Options opt = new OptionsBuilder()
                .include(GregorianMicroBenchmark.class.getSimpleName())
                .warmupIterations(6)
                .forks(1)
                .mode(Mode.SampleTime)
                .measurementTime(TimeValue.seconds(time))
                .timeUnit(TimeUnit.NANOSECONDS)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public Object testGregorianCalendar() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(Calendar.getInstance());
        return new ObjectInputStream(
                new ByteArrayInputStream(
                        baos.toByteArray())).readObject();
    }

    @Benchmark
    public long testSystemCurrentTimeMillis() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream oos = new DataOutputStream(baos);
        oos.writeLong(System.currentTimeMillis());
        return new DataInputStream(
                new ByteArrayInputStream(
                        baos.toByteArray())).readLong();
    }

    @Benchmark
    public long usingBytes() throws IOException, ClassNotFoundException {
        bytes.clear();
        bytes.writeLong(System.currentTimeMillis());
        return bytes.readLong();
    }
}
