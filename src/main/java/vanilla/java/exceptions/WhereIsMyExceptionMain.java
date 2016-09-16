package vanilla.java.exceptions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by peter on 15/09/2016.
 */
public class WhereIsMyExceptionMain {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
        BlockingQueue<RuntimeException> req = new LinkedBlockingQueue<>();
        es.submit(() -> req.add(new RuntimeException()));
        es.shutdown();
        throw req.take();
    }
}
