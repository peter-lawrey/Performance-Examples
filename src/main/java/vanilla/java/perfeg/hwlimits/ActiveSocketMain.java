package vanilla.java.perfeg.hwlimits;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.report;
import static vanilla.java.perfeg.hwlimits.TestPools.runTests;

/*
Socket echo scalability
threads	percentage speed (1 == 100%)
  1	100%
  2	196%
  4	367%
  8	504%
 16	505%
 32	505%
 */
public class ActiveSocketMain {
    public static void main(String... ignored) throws ExecutionException, InterruptedException, IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(0));

        List<Callable<Integer>> runs = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 64; i++) {
            final SocketChannel s = SocketChannel.open(new InetSocketAddress("localhost", ssc.socket().getLocalPort()));
            final SocketChannel s2 = ssc.accept();

            runs.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    ByteBuffer send = ByteBuffer.allocateDirect(1024);
                    ByteBuffer recv = ByteBuffer.allocateDirect(1024);
                    int count = 0;
                    for (int i = 0; i < 1000; i++) {
                        // send one way
                        send.clear();
                        while (send.hasRemaining())
                            s.write(send);

                        recv.clear();
                        while (recv.hasRemaining())
                            s2.read(recv);

                        // send back.
                        send.clear();
                        while (send.hasRemaining())
                            s2.write(send);

                        recv.clear();
                        while (recv.hasRemaining())
                            s.read(recv);

                        count++;
                    }
                    return count;
                }
            });
        }
        ssc.close();
        for (int i = 0; i < 3; i++)
            report("Socket echo scalability", runTests(runs));

        // todo close all the sockets.
    }
}
