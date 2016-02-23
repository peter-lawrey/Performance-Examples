package vanilla.java.memory;

/**
 * Created by peter on 23/02/16.
 */
public class ArrayAllocationMain {
    public static void main(String[] args) {

        long used1 = memoryUsed();
        int[][] array = new int[200][2];

        long used2 = memoryUsed();
        int[][] array2 = new int[2][200];

        long used3 = memoryUsed();
        if (used1 == used2) {
            System.err.println("You need to turn off the TLAB with -XX:-UseTLAB");
        } else {
            System.out.printf("Space used by int[200][2] is " + (used2 - used1) + " bytes%n");
            System.out.printf("Space used by int[2][200] is " + (used3 - used2) + " bytes%n");
        }
    }

    public static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }
}
