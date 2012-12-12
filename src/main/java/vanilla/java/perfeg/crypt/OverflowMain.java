package vanilla.java.perfeg.crypt;

import java.math.BigInteger;
/*
True answer 4,169,684,317 took 33.971 μs, quick answer 4,169,684,317 took 2.290 μs
True answer 400,254,361 took 11.181 μs, quick answer 400,254,361 took 0.152 μs
True answer 3,612,330,789 took 7.723 μs, quick answer 3,612,330,789 took 0.095 μs
True answer 858,641,345 took 6.938 μs, quick answer 858,641,345 took 0.155 μs
True answer 2,169,526,829 took 7.011 μs, quick answer 2,169,526,829 took 0.156 μs
True answer 4,231,348,777 took 5.169 μs, quick answer 4,231,348,777 took 0.051 μs
True answer 1,807,631,477 took 4.922 μs, quick answer 1,807,631,477 took 0.051 μs
True answer 3,579,038,417 took 3.974 μs, quick answer 3,579,038,417 took 0.052 μs
True answer 3,923,287,037 took 4.679 μs, quick answer 3,923,287,037 took 0.051 μs
True answer 2,169,903,033 took 5.381 μs, quick answer 2,169,903,033 took 0.051 μs
 */
public class OverflowMain {
    public static void main(String... args) {
        BigInteger two32 = BigInteger.valueOf(2).pow(32);

        int prime = 2106945901;
        int runs = 5000;
        for (int i = 0; i < 10; i++) {
            long[] answer1 = new long[runs];
            long[] answer2 = new long[runs];
            long start = System.nanoTime();

            for (int j = 0; j < runs; j++)
                answer1[j] = BigInteger.valueOf(prime).modPow(BigInteger.valueOf(prime + j), two32).longValue();
            long mid = System.nanoTime();

            for (int j = 0; j < runs; j++)
                answer2[j] = powMod2_32(prime, prime+j);
            long end = System.nanoTime();
            System.out.printf("True answer %,d took %.3f μs, quick answer %,d took %.3f μs%n",
                    answer1[i], (mid - start) / 1e3 / runs, answer2[i], (end - mid) / 1e3 / runs);
        }
    }

    private static long powMod2_32(int prime, int number) {
        int answer2 = 1;
        int p = prime;
        for (int n = number; n > 0; n >>>= 1) {
            if ((n & 1) != 0)
                answer2 *= p;
            p *= p;
        }
        return answer2 & 0xFFFFFFFFL;
    }
}
