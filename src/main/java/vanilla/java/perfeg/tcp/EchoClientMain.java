package vanilla.java.perfeg.tcp;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author peter.lawrey
 */
// Loopback echo latency was 7.8/8.2 12.7/38.2 us for 50/90 99/99.9%tile
// 1m - Loopback echo latency was 7.8/8.2 12.3/34.9 81.7us for 50/90 99/99.9 99.99%tile
// 10m - Loopback echo latency was 7.8/8.2 12.3/40.6 121.1us for 50/90 99/99.9 99.99%tile
// 100m - Loopback echo latency was 7.8/7.8 11.9/35.3 56.2us for 50/90 99/99.9 99.99%tile

// tests=100k, repeats=10 - Loop back echo latency was 29.1/31.6 32.4/32.4 32.4us for 50/90 99/99.9 99.99%tile
public class EchoClientMain {
    static final int PORT = 54321;
    public static void main(String... args) throws IOException {
        String hostname = args[0];
        int port = args.length < 2 ? PORT : Integer.parseInt(args[1]);
        int tests = 200000, repeats = 2;
        long[] times = new long[tests * repeats];

        Socket[] sockets = new Socket[repeats];
        DataInputStream[] in = new DataInputStream[repeats];
        DataOutputStream[] out = new DataOutputStream[repeats];
        for (int j = 0; j < repeats; j++) {
            Socket socket = new Socket(hostname, port);
//            socket.setTcpNoDelay(true);
            sockets[j] = socket;
            out[j] = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            in[j] = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        int count = 0;
        for (int i = -20000; i < tests; i++) {
            long now = System.nanoTime();
            for (int j = 0; j < repeats; j++) {
                out[j].writeLong(now);
                out[j].flush();
            }

            for (int j = 0; j < repeats; j++) {
                long time = System.nanoTime() - in[j].readLong();
                if (i >= 0)
                    times[count++] = time;
            }
        }
        Arrays.sort(times);
        System.out.printf("Loop back echo latency was %.1f/%.1f %.1f/%.1f %.1fus for 50/90 99/99.9 99.99%%tile%n",
                times[tests / 2] / 1e3, times[tests * 9 / 10] / 1e3,
                times[tests - tests / 100] / 1e3, times[tests - tests / 1000] / 1e3,
                times[tests - tests / 10000] / 1e3);
        for (Closeable socket : sockets)
            socket.close();
    }
}
