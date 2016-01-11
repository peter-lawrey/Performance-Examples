package vanilla.java.async;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by peter.lawrey on 07/12/2015.
 */
public class SubmitMain {
    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> {
            Files.lines(Paths.get("filename.txt")).forEach(System.out::println);
            return null;
        });
    }
}
