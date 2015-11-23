package vanilla.java.lambda;

import net.openhft.chronicle.core.util.SerializableConsumer;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class SerializableLambda {
    public static void main(String[] args) {
        SerializableConsumer<String> cs =
                s -> System.out.println(s);
    }
}
