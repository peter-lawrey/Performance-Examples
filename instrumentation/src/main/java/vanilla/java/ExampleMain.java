package vanilla.java;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by peter_2 on 23/11/2014.
 */
public class ExampleMain {
    static {
        MyInstrumentation.loadAgent();
    }

    public static void main(String[] args) {
        Predicate<Integer> predicate = x -> x % 2 == 0;
        Stream.of(1, 2, 3, 4).filter(predicate).forEach(System.out::println);

        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out));
        ClassReader cr = new ClassReader(((RLambda) predicate).byte$());
        cr.accept(traceClassVisitor, 0);
    }
}
