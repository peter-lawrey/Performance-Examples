package vanilla.java.exceptions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peter on 15/09/2016.
 */
public class SuddenIOExceptionMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Path file = Paths.get(args[0]);
        es.submit(() -> {
            Files.lines(file).forEach(System.out::println);
            return null;
        });

        es.submit(() -> {
//    Files.lines(file).forEach(System.out::println);
        });
    }
}
