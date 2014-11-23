package vanilla.java;

import com.sun.tools.attach.VirtualMachine;
import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.security.ProtectionDomain;
import java.util.Arrays;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by peter
 */
public class MyInstrumentation {
    public static final String RLAMBDA = RLambda.class.getName().replace('.', '/');

    private static Instrumentation instrumentation;

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public static void setInstrumentation(Instrumentation instrumentation) {
        MyInstrumentation.instrumentation = instrumentation;
        instrumentation.addTransformer(new MyClassFileTransformer());
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain " + agentArgs + " " + inst);
        setInstrumentation(inst);

    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain " + agentArgs + " " + inst);
        setInstrumentation(inst);
    }

    public static void loadAgent() {
        if (instrumentation != null) return;

        System.out.println("dynamically loading javaagent");
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);

        String path = MyInstrumentation.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (!path.endsWith(".jar"))
            path = System.getProperty("user.home") + "/.m2/repository/vanilla/java/instrumentation/1.0/instrumentation-1.0.jar";
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(path, "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        loadAgent();
    }

    static class MyClassFileTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, final byte[] classfileBuffer) throws IllegalClassFormatException {

            try {
                if (className == null || className.startsWith("vanilla/java/test/")) {

                    final boolean[] altered = {false};
                    final ClassWriter classWriter = new ClassWriter(0);
//                    TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new ASMifier(), new PrintWriter(System.out));

                    final ClassVisitor cv = new ClassVisitor(ASM5, classWriter) {
                        String name = "";

                        /**
                         * Called when a class is visited. This is the method called first
                         */
                        @Override
                        public void visit(int version, int access, String name,
                                          String signature, String superName, String[] interfaces) {
                            if (interfaces.length > 0 && !Arrays.asList(interfaces).contains(RLAMBDA)) {
                                altered[0] = true;
                                System.out.println("Visiting class: " + name + ", Class Major Version: " + version + ", Super class: " + superName);
                                if (!name.startsWith("java/")) {
                                    String[] sarr = new String[interfaces.length + 1];
                                    System.arraycopy(interfaces, 0, sarr, 0, interfaces.length);
                                    sarr[interfaces.length] = RLAMBDA;
                                    interfaces = sarr;
                                }
                            }
                            this.name = name;
                            super.visit(version, access, name, signature, superName, interfaces);
                        }

                        /**
                         * When a field is encountered
                         */
                        @Override
                        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
//                            System.out.println("Field: " + name + " " + desc + " value:" + value);
                            return super.visitField(access, name, desc, signature, value);
                        }

                        @Override
                        public void visitEnd() {
                            if (altered[0]) {
                                MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "byte$", "()[B", null, null);
                                mv.visitCode();
                                Label l0 = new Label();
                                mv.visitLabel(l0);
                                mv.visitLineNumber(35, l0);
                                mv.visitLdcInsn(new String(classfileBuffer, StandardCharsets.ISO_8859_1));
                                mv.visitFieldInsn(GETSTATIC, "java/nio/charset/StandardCharsets", "ISO_8859_1", "Ljava/nio/charset/Charset;");
                                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "getBytes", "(Ljava/nio/charset/Charset;)[B", false);
                                mv.visitInsn(ARETURN);
                                Label l1 = new Label();
                                mv.visitLabel(l1);
                                mv.visitLocalVariable("this", "L" + name + ";", null, l0, l1, 0);
                                mv.visitMaxs(2, 1);
                                mv.visitEnd();
                            }

//                            System.out.println("Method ends here");
                            super.visitEnd();
                        }

                    };
                    new ClassReader(classfileBuffer).accept(cv, 0);
                    if (altered[0])
                        return classWriter.toByteArray();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return classfileBuffer;
        }
    }
}
