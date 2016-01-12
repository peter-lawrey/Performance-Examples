package vanilla.java.lambda;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class Main {
    public static void main(String[] args) {

//        print(new ArrayList<>());
        print(Arrays.asList(1, 2, 3, 4));
    }

    public static <T extends Comparable<T>> void print(Collection<T> list) {

    }
}
