package vanilla.java.jmh;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * Created by peter.lawrey on 26/11/2015.
 */
public class MicroBenchmark {
    @Benchmark
    public void testMe() {
        for (int i = 0; i < 1000; i++) {

        }
    }
}
