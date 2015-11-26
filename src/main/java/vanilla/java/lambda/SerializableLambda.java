package vanilla.java.lambda;

import net.openhft.chronicle.core.util.SerializableConsumer;

/**
 * Created by peter.lawrey on 23/11/2015.
 */
public class SerializableLambda {
    public static void main(String[] args) {
//        Serializable printMe =
//                (Consumer<String> & Serializable)
//                        s -> System.out.println(s);
        new SerializableLambda().call();
    }

    private void call() {
        apply(s -> System.out.println(s));
    }

    public void apply(SerializableConsumer<String> cs) {
        cs.accept("Hello World");
    }
}
