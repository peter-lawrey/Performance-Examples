package vanilla.java.math;

/**
 * Created by peter.lawrey on 26/01/2016.
 */
public class SqrtMain {
    public static double sqrt(double ans) {
        if (ans < 1)
            return 1.0 / sqrt(1.0 / ans);
        double guess = 1;
        double add = ans / 2;
        while (add >= Math.ulp(guess)) {
            double guess2 = guess + add;
            double result = guess2 * guess2;
            if (result < ans)
                guess = guess2;
            else if (result == ans)
                return guess2;
            add /= 2;
        }
        return guess;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++)
            System.out.println(i / 10.0 + ": " + sqrt(i / 10.0) + " vs " + Math.sqrt(i / 10.0));
    }
}
