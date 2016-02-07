package vanilla.java;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.StopCharTesters;

import java.util.Scanner;

public class SplitCompareMain {

    static volatile String a, b, c, d;
    static volatile int e, f, g, h;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            testSplit();
            testBytes();
        }
    }

    private static void testScanner() {
        for (int t = 0; t < 3; t++) {
            long start = System.currentTimeMillis();
            int count = 20000;
            for (int i = 0; i < count; i++) {
                String s = "a,b,c,d,1,2,3,4";
                Scanner scan = new Scanner(s).useDelimiter(",");
                a = scan.next();
                b = scan.next();
                c = scan.next();
                d = scan.next();
                e = scan.nextInt();
                f = scan.nextInt();
                g = scan.nextInt();
                h = scan.nextInt();
            }
            long time = (System.currentTimeMillis() - start) * 1000 / count;
            System.out.println("Average time " + time + "us.");
        }
    }

    private static void testSplit() {
        long start = System.currentTimeMillis();
        int count = 500000;
        for (int i = 0; i < count; i++) {
            String s = "a,b,c,d,1,2,3,4";
            String[] parts = s.split(",");
            a = parts[0];
            b = parts[1];
            c = parts[2];
            d = parts[3];
            e = Integer.parseInt(parts[4]);
            f = Integer.parseInt(parts[5]);
            g = Integer.parseInt(parts[6]);
            h = Integer.parseInt(parts[7]);
        }
        long time = (System.currentTimeMillis() - start) * 100000 / count;
        System.out.println("Split: Average time " + time / 1e2 + "us.");
    }

    private static void testBytes() {
        Bytes bytes = Bytes.allocateDirect(32);
        long start = System.currentTimeMillis();
        int count = 500000;
        for (int i = 0; i < count; i++) {
            bytes.clear();
            String s = "a,b,c,d,1,2,3,4";
            bytes.append8bit(s);
            a = bytes.parseUtf8(StopCharTesters.COMMA_STOP);
            b = bytes.parseUtf8(StopCharTesters.COMMA_STOP);
            c = bytes.parseUtf8(StopCharTesters.COMMA_STOP);
            d = bytes.parseUtf8(StopCharTesters.COMMA_STOP);
            e = (int) bytes.parseLong();
            f = (int) bytes.parseLong();
            g = (int) bytes.parseLong();
            h = (int) bytes.parseLong();
        }
        long time = (System.currentTimeMillis() - start) * 100000 / count;
        System.out.println("Bytes: Average time " + time / 1e2 + "us.");
    }
}
