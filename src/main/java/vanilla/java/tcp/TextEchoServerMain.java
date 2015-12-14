package vanilla.java.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by plawrey on 12/14/15.
 */
public class TextEchoServerMain {
    static final int PORT = Integer.getInteger("port", 65432);

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            Socket s = ss.accept();
            new Thread(() -> {
                System.out.println("Accepted " + s);
                try {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                         PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                        for (String line; (line = br.readLine()) != null; )
                            handle(line, pw);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("... " + s + " closed");
                }
            }).start();
        }
    }

    static void handle(String line, PrintWriter pw) {
        pw.println(line);
    }
}
