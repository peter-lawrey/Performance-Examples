package vanilla.java.exceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by peter on 13/09/2016.
 */
public class PretendItDidntHappen2Main {

    public static void main(String[] args) throws IOException {

        readFile();
    }

    static void readFile() throws IOException {
        try (FileReader fr = new FileReader("my-file.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String line = br.readLine();
        }
    }
}
