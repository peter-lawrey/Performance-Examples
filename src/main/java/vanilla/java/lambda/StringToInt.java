package vanilla.java.lambda;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
@FunctionalInterface
public interface StringToInt {
    static boolean isStringToInt(Object o) {
        return o instanceof StringToInt;
    }

    long getLongFor(String s);

    default int getIntFor(String s) {
        return Math.toIntExact(getLongFor(s));
    }

    default int count() {
        return StringToInts.count(this);
    }
}
