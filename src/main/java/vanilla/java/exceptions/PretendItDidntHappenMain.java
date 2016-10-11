package vanilla.java.exceptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by peter on 13/09/2016.
 */
public class PretendItDidntHappenMain {

    public static void main(String[] args) {

        FileReader fr = null;
        try {
            fr = new FileReader("my-file.txt");
        } catch (FileNotFoundException e) {
            // who know??
        }
        BufferedReader br = new BufferedReader(fr);
    }
}
