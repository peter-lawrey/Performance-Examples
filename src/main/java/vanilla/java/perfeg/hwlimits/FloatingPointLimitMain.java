package vanilla.java.perfeg.hwlimits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.report;
import static vanilla.java.perfeg.hwlimits.TestPools.runTests;

/*
 * FP scalability
 * threads	percentage speed (1 == 100%)
 * 1	100%
 * 2	196%
 * 4	386%
 * 8	709%
 * 16	689%
 * 32	676%

Win 7, i5
FP scalability
threads	percentage speed (1 == 100%)
  1	100%
  2	195%
  4	308%
  8	309%
 16	318%
 32	333%
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
