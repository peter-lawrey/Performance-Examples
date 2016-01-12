package vanilla.java.threads;

public class RequiresVolatileMain {
    static boolean value;

    public static void main(String... args) {
        new Thread(new MyRunnable(true), "Sets true").start();
        new Thread(new MyRunnable(false), "Sets false").start();
        new Thread(new MyValueMonitor()).start();
    }

    private static class MyRunnable implements Runnable {
        private final boolean target;

        private MyRunnable(boolean target) {
            this.target = target;
        }

        @Override
        public void run() {
            while (true) {
                if (value != target) {
                    value = target;
                }
            }
        }
    }

    static class MyValueMonitor implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(100);
                    System.out.println("value=" + value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}