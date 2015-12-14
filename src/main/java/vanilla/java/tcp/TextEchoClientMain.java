package vanilla.java.tcp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
 * Created by plawrey on 12/14/15.
 * Throughput test
 *
 Throughput test wrote 44.2 MB/s

 Latency test for 100,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 528,477/1,069,998 1,163,770/1,173,065 (1,174,106) micro-seconds
 Latency test for 80,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 276,228/498,628 551,875/556,160 (556,636) micro-seconds
 Latency test for 60,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 14/22 1,759/3,031 (3,393) micro-seconds
 Latency test for 50,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 14/21 4,388/5,518 (5,641) micro-seconds
 Latency test for 40,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 14/15 22/485 (2,071) micro-seconds
 Latency test for 30,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 14/15 21/921 (3,360) micro-seconds
 Latency test for 20,000 TPS
 Latency distribution 50/90 99/99.9 (worst) was 15/19 24/46 (656) micro-seconds
 */
public class TextEchoClientMain {
    static final String HOST = System.getProperty("host", "localhost");
    static final int PORT = Integer.getInteger("port", 65432);

    public static void main(String[] args) throws IOException {
        Socket s = new Socket(HOST, PORT);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {


            System.out.println("Throughput test");
            char[] chars = new char[1000];
            Arrays.fill(chars, '*');
            String text = new String(chars);
            long start = System.currentTimeMillis();
            int count = 0;
            while (System.currentTimeMillis() < start + TimeUnit.SECONDS.toMillis(5)) {
                pw.println(text);
                br.readLine();
                count++;
            }
            long time = System.currentTimeMillis() - start;
            System.out.printf("Throughput test wrote %.1f MB/s%n%n", (double) count / time);

            int warmup = 1000000;
            for (int rate : new int[]{warmup, 100000, 80000, 60000, 50000, 40000, 30000, 20000}) {
                if (rate != warmup)
                    System.out.printf("Latency test for %,d TPS%n", rate);
                long[] times = new long[200000];
                long start2 = System.nanoTime();
                long spacing = (long) (1e9 / rate);
                long next = start2 + spacing;
                String smallText = "Hello World";

                for (int i = 0; i < times.length; i++) {
                    while (System.nanoTime() < next) ;
                    // take timing from when it should have sent the message, not when it did
                    long start3 = next; // System.nanoTime();
                    pw.println(smallText);
                    br.readLine();
                    long time3 = System.nanoTime() - start3;
                    times[i] = time3;
                    next += spacing;
                }
                Arrays.sort(times);
                if (rate != warmup)
                    System.out.printf("Latency distribution 50/90 99/99.9 (worst) was %,d/%,d %,d/%,d (%,d) micro-seconds%n",
                            times[times.length / 2] / 1000,
                            times[times.length * 9 / 10] / 1000,
                            times[times.length * 99 / 100] / 1000,
                            times[times.length * 999 / 1000] / 1000,
                            times[times.length - 1] / 1000
                    );
            }
        }
    }
}
