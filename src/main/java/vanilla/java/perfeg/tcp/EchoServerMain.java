package vanilla.java.perfeg.tcp;

import net.openhft.affinity.AffinitySupport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author peter.lawrey
 */
public class EchoServerMain {
    public static void main(String... args) throws IOException {
        AffinitySupport.setAffinity(1 << 2L);
        int port = args.length < 1 ? EchoClientMain.PORT : Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(port);
        System.out.println("listening on " + ss);
        while (true) {
            final Socket socket = ss.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Connected " + socket);
                        InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();
                        byte[] bytes = new byte[8192];
                        for (int len; (len = in.read(bytes)) > 0; ) {
                            out.write(bytes, 0, len);
                        }
                    } catch (IOException ignored) {
                    } finally {
                        System.out.println("... disconnected " + socket);
                        try {
                            socket.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }).start();
        }
    }
}
