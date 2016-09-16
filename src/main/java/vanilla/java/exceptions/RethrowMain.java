package vanilla.java.exceptions;

import java.sql.SQLException;

/**
 * Created by peter on 15/09/2016.
 */
public class RethrowMain {
    public static <T extends Throwable> RuntimeException rethrow(Throwable throwable) throws T {
        throw (T) throwable; // rely on vacuous cast
    }

    public static void main(String[] args) {
        throw rethrow(new SQLException("O Noes"));
    }
}
