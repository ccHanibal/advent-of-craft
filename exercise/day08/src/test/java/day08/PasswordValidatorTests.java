package day08;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordValidatorTests {

    private PasswordValidator validator;

    @BeforeEach
    void setupTest() {
        validator = new PasswordValidator();
    }

    @Test
    void canValidateAPassword() {
        validator.verify("");
    }

    @Test
    void mustContainAtLeast8Characters() {
        assertThat(validator.verify("Au1#")).isFalse();
    }

    @Test
    void mustContainAtLeast1CapitalLetter() {
        assertThat(validator.verify("123456u#")).isFalse();
    }

    @Test
    void mustContainAtLeast1LowercaseLetter() {
        assertThat(validator.verify("12345678U#")).isFalse();
    }

    @Test
    void mustContainAtLeast1Digit() {
        assertThat(validator.verify("abcdefghU#")).isFalse();
    }

    @Test
    void mustContainAtLeast1SpecialDigit() {
        assertThat(validator.verify("abcdefghU1")).isFalse();
    }

    @Test
    void mustNotContainAnyUnauthorizedCharacters() {
        assertThat(validator.verify("abcdEFGH1234#)")).isFalse();
    }

    @Test
    void acceptsAValidPassword() {
        assertThat(validator.verify("abcdEFGH1234#%.")).isTrue();
    }
}
