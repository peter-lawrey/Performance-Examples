package vanilla.java.perfeg.hwlimits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.report;
import static vanilla.java.perfeg.hwlimits.TestPools.runTests;

/**
 * Created with IntelliJ IDEA.
 * User: plawrey
 * Date: 14/01/13
 * Time: 09:11
 * To change this template use File | Settings | File Templates.
 */
public class FloatingPointLimitMain {
    static class Complex {
        double re, im;

        Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }

        public void sqrtPlus(Complex a) {
            double re2 = re * re - im * im + a.re;
            double im2 = 2 * re * im + a.im;
            re = re2;
            im = im2;
        }
    }

    public static void main(String... ignored) throws ExecutionException, InterruptedException {
        List<Callable<Complex>> runs = new ArrayList<Callable<Complex>>();
        for (int i = -4; i <= 4; i++)
            for (int j = -4; j <= 4; j++) {
                final Complex start = new Complex(i / 1e3, j / 1e3);
                runs.add(new Callable<Complex>() {
                    @Override
                    public Complex call() throws Exception {
                        Complex complex = new Complex(0, 0);
                        for (int i = 0; i < 100000; i++)
                            complex.sqrtPlus(start);
                        return complex;
                    }
                });
            }
        for (int i = 0; i < 3; i++)
            report("FP scalability", runTests(runs));
    }
}
