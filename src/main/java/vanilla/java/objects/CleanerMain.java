package vanilla.java.objects;

import sun.misc.Cleaner;

import java.util.RandomAccess;

/**
 * Created by peter on 11/10/2016.
 */
public class CleanerMain {
    public static void main(String[] args) throws InterruptedException {
        new RandomAccess() {
            public void finalize() {
                new Throwable(Thread.currentThread().getName()).printStackTrace();
            }
        };
        Cleaner.create(new Integer(1), () ->
                new Throwable(Thread.currentThread().getName()).printStackTrace());
        System.gc();
        Thread.sleep(1000);
    }
}
