package vanilla.java.threads;

/**
 * Created by peter.lawrey on 12/01/2016.
 */
public class EncapsulationMain {
    private final String name;
    private final int activeCount;

    public EncapsulationMain(String name, int activeCount) {
        this.name = name;
        this.activeCount = activeCount;
    }

    public static void main(String[] args) {
        new EncapsulationMain("Hello", 1).run();
    }

    public void run() {
        new Thread(() ->
                System.out.println("name: " + getName() + " count: " + activeCount())
        ).start();
    }

    public String getName() {
        return name;
    }

    public int activeCount() {
        return activeCount;
    }
}
