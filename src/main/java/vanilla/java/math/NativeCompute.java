package vanilla.java.math;

/**
 * Created by peter.lawrey on 14/01/2016.
 */
public class NativeCompute {
    public static double access(BytesMatrix matrix, int row, int column) {
        return access(matrix.address(), row, column);
    }

    public static native double access(long address, int row, int column);
}
