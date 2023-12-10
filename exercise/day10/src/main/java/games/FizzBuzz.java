package games;

import java.util.Arrays;
import java.util.List;

public class FizzBuzz {
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int FIZZ = 3;
    public static final int BUZZ = 5;
    public static final int FIZZBUZZ = 15;

    private static final List<FizzBuzzStrategy> specials =
            Arrays.asList(
                    new FizzBuzzStrategy(FIZZBUZZ, "FizzBuzz"),
                    new FizzBuzzStrategy(FIZZ, "Fizz"),
                    new FizzBuzzStrategy(BUZZ, "Buzz"));

    private FizzBuzz() {
    }

    public static String convert(Integer input) throws OutOfRangeException {
        if (isOutOfRange(input)) {
            throw new OutOfRangeException();
        }
        return convertSafely(input);
    }

    private static String convertSafely(Integer input) {
        for (var special : specials) {
            if (special.applies(input))
                return special.getResultText();
        }

        return input.toString();
    }

    private static boolean isOutOfRange(Integer input) {
        return input <= MIN || input > MAX;
    }
}
