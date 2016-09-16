package vanilla.java.exceptions;

import java.sql.SQLException;

/**
 * Created by peter on 15/09/2016.
 */
public class ExceptionSizeMain {
    public static long memoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void main(String[] args) {
        // load classes etc.
        new SQLException("Hello").getStackTrace();
        long before = memoryUsed();
        new SQLException("Hello").getStackTrace();
        long after = memoryUsed();
        if (before == after)
            throw new AssertionError("Need to set -XX:-UseTLAB");
        System.out.println("Exception used " + (after - before) + " bytes.");
    }
}
