package vanilla.java.objects;

import sun.misc.Cleaner;

/**
 * Created by peter on 11/10/2016.
 */
public class CleanerMain {
    public static void main(String[] args) throws InterruptedException {
        Cleaner.create(new Integer(1), () ->
                new Throwable(Thread.currentThread().getName()).printStackTrace());

        System.gc();
        Thread.sleep(1000);
    }
}
