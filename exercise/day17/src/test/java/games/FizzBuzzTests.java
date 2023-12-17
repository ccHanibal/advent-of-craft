package games;

import net.jqwik.api.*;

import static io.vavr.API.Some;
import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzTests {
    @Provide
    private static Arbitrary<Integer> toTextInputs() {
        return Arbitraries.integers()
                    .filter(v -> v > 0 && v <= 100)
                    .filter(v -> v % 3 != 0 && v % 5 != 0);

    }

    @Provide
    private static Arbitrary<Integer> fizzInputs() {
        return Arbitraries.integers()
                .filter(v -> v > 0 && v <= 100)
                .filter(v -> v % 3 == 0 && v % 5 != 0);

    }

    @Provide
    private static Arbitrary<Integer> buzzInputs() {
        return Arbitraries.integers()
                .filter(v -> v > 0 && v <= 100)
                .filter(v -> v % 3 != 0 && v % 5 == 0);

    }

    @Provide
    private static Arbitrary<Integer> fizzBuzzInputs() {
        return Arbitraries.integers()
                .filter(v -> v > 0 && v <= 100)
                .filter(v -> v % 15 == 0);

    }

    @Provide
    Arbitrary<Integer> invalidInputs() {
        return Arbitraries.integers()
                    .filter(v -> v <= 0 || v > 100);
    }

    @Property
    void a_number_not_divisible_by_three_or_five_gives_number_as_string(@ForAll("toTextInputs") int input) {
        assertThat(FizzBuzz.convert(input))
                .isEqualTo(Some(Integer.toString(input)));
    }

    @Property
    void a_number_divisible_by_three_but_not_five_gives_Fizz(@ForAll("fizzInputs") int input) {
        assertThat(FizzBuzz.convert(input))
                .isEqualTo(Some("Fizz"));
    }

    @Property
    void a_number_divisible_by_five_but_not_three_gives_Buzz(@ForAll("buzzInputs") int input) {
        assertThat(FizzBuzz.convert(input))
                .isEqualTo(Some("Buzz"));
    }

    @Property
    void a_number_divisible_by_three_and_five_gives_FizzBuzz(@ForAll("fizzBuzzInputs") int input) {
        assertThat(FizzBuzz.convert(input))
                .isEqualTo(Some("FizzBuzz"));
    }

    @Property
    void parse_fail_for_numbers_out_of_range(@ForAll("invalidInputs") int input) {
        assertThat(FizzBuzz.convert(input).isEmpty())
                .isTrue();
    }
}