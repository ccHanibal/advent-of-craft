package greeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {
    private Greeter greeter;

    @BeforeEach
    void setupTests() {
        greeter = new Greeter();
    }

    @Test
    void saysHello() {
        assertThat(greeter.greet())
                .isEqualTo("Hello.");
    }

    @Test
    void saysHelloFormally() {
        greeter.setFormality(new FormalFormality());

        assertThat(greeter.greet())
                .isEqualTo("Good evening, sir.");
    }

    @Test
    void saysHelloCasually() {
        greeter.setFormality(new CasualFormality());

        assertThat(greeter.greet())
                .isEqualTo("Sup bro?");
    }

    @Test
    void saysHelloIntimately() {
        greeter.setFormality(new IntimateFormality());

        assertThat(greeter.greet())
                .isEqualTo("Hello Darling!");
    }
}
