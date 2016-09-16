package vanilla.java.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by peter on 15/09/2016.
 */
public class NewInstanceMain {
    NewInstanceMain() throws IOException {
        Files.lines(Paths.get("no-such-file")).forEach(System.out::println);
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        NewInstanceMain.class.newInstance();
    }
}
