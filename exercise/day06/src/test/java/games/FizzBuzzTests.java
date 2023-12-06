package games;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FizzBuzzTests {
    @ParameterizedTest
    @ValueSource(ints = {1, 67, 82})
    void returns_input_as_string_when_not_divisible_by_three_or_five(int number) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(number))
                .isEqualTo(Integer.toString(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 66, 99})
    void returns_Fizz_when_only_divisible_by_three(int number) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(number))
                .isEqualTo("Fizz");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 50, 85})
    void returns_Buzz_when_only_divisible_by_five(int number) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(number))
                .isEqualTo("Buzz");
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 30, 45})
    void returns_FizzBuzz_when_divisible_by_fivteen(int number) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(number))
                .isEqualTo("FizzBuzz");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 101, -1})
    void fails_on(int number) {
        assertThatThrownBy(() -> FizzBuzz.convert(number))
                .isInstanceOf(OutOfRangeException.class);
    }
}