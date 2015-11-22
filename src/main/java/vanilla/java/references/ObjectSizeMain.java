package vanilla.java.references;

/**
 * Created by peter.lawrey on 22/11/2015.
 */
public class ObjectSizeMain {
    public static void main(String[] args) {
        long used1 = memoryUsed();

        Object o = new Object();

        long used2 = memoryUsed();
        if (used1 == used2)
            System.err.println("You need to turn off the TLAB with -XX:-UseTLAB");
        else
            System.out.printf("Space used by one object is " + (used2 - used1) + " bytes");
    }

    public static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
