package games;

import io.vavr.control.Either;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class FizzBuzz {
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int FIZZ = 3;
    public static final int BUZZ = 5;
    public static final int FIZZBUZZ = 15;

    private static final Map<Predicate<Integer>, String> mapping;

    static {
        mapping = new LinkedHashMap<>();
        mapping.put(i -> is(FIZZBUZZ, i), "FizzBuzz");
        mapping.put(i -> is(FIZZ, i), "Fizz");
        mapping.put(i -> is(BUZZ, i), "Buzz");
    }

    private static boolean is(Integer divisor, Integer input) {
        return input % divisor == 0;
    }

    private static boolean isOutOfRange(Integer input) {
        return input <= MIN || input > MAX;
    }

    public static Either<OutOfRangeException, String> convert(Integer input) {
        if (isOutOfRange(input)) {
            return left(new OutOfRangeException());
        }
        return right(convertSafely(input));
    }

    private static String convertSafely(Integer input) {
        return mapping.entrySet()
                .stream()
                .filter(f -> f.getKey().test(input))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElseGet(input::toString);
    }
}
