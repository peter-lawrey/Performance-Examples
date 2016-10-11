package vanilla.java.exceptions;

/**
 * Created by peter on 13/09/2016.
 */
public class ThrowableMain {
    public static void main(String[] args) throws MyThrowable {
        try {
            throw new MyThrowable();

        } catch (Exception e) {
            System.out.println("Exception ");
            e.printStackTrace();

        } catch (Error e) {
            System.out.println("Error ");
            e.printStackTrace();
        }
    }

    static class MyThrowable extends Throwable {
    }
}
