package vanilla.java.perfeg.hwlimits;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static vanilla.java.perfeg.hwlimits.TestPools.report;
import static vanilla.java.perfeg.hwlimits.TestPools.runTests;

/*
Ubuntu i7.
Fixed Point int scalability
threads	percentage speed (1 == 100%)
  1	100%
  2	196%
  4	360%
  8	564%
 16	558%
 32	552%

Win 7, i5
Fixed Point int scalability
threads	percentage speed (1 == 100%)
  1	100%
  2	184%
  4	261%
  8	269%
 16	273%
 32	285%
 */
public class FixedPointLimitMain {
    static class Complex {
        static final int FIXED_BITS = 16;
        static final int FACTOR = 1 << FIXED_BITS;

        int re, im;

        Complex(double re, double im) {
            this.re = (int) (re * FACTOR);
            this.im = (int) (im * FACTOR);
        }

        public void sqrtPlus(Complex a) {
            int re2 = (re * re - im * im) / FACTOR + a.re;
            int im2 = (re * im) / (FACTOR / 2) + a.im;
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
            report("Fixed Point int scalability", runTests(runs));
    }
}
