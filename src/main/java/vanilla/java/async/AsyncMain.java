package vanilla.java.async;

import net.openhft.lang.thread.NamedThreadFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peter_2 on 27/11/2014.
 */
public class AsyncMain {
    static final ExecutorService main = Executors.newSingleThreadExecutor();
    static final ExecutorService async = Executors.newCachedThreadPool(new NamedThreadFactory("async", true));

    // Asynchronous processing example.
    public static void main(String[] args) {
        mainRun(() -> startOutput(0, 100));
    }

    public static void startOutput(int i, int max) {
        if (i < max)
            asyncRun(() -> {
                        System.out.println(i + " and 1000/i is " + 1000/i);
                        return null;
                    },
                    r -> startOutput(i + 1, max)
            );
        else
            main.shutdown();
    }

    public static void mainRun(Runnable run) {
        main.submit(run);
    }

    public static <R> void asyncRun(Callable<R> task, ResultProcessor<R> rp) {
        async.submit(() -> {
            try {
                R result = task.call();
                main.submit(() -> rp.accept(result));
            } catch (Throwable e) {
                main.submit(() -> rp.onThrowable(e));
            }
            return null;
        });
    }
}

interface ResultProcessor<R> {
    void accept(R result);
    default void onThrowable(Throwable t) {
        t.printStackTrace(System.out);
        accept(errorResult());
    }
    default R errorResult() {
        return null;
    }
}
