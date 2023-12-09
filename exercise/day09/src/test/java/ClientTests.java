import account.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

class ClientTests {
    private Client client;

    @BeforeEach
    void setupTest() {
        client = new Client(new LinkedHashMap<>() {{
            put("Tenet Deluxe Edition", 45.99);
            put("Inception", 30.50);
            put("The Dark Knight", 30.50);
            put("Interstellar", 23.98);
        }});
    }

    @Test
    void client_should_generate_summary() {
        String summary = client.generateSummary();

        assertThat(summary).isEqualTo(
                "Tenet Deluxe Edition for 45.99€" + lineSeparator() +
                        "Inception for 30.5€" + lineSeparator() +
                        "The Dark Knight for 30.5€" + lineSeparator() +
                        "Interstellar for 23.98€" + lineSeparator() +
                        "Total : 130.97€");
    }

    @Test
    void client_does_generate_same_summary_twice_in_a_row() {
        String summary1 = client.generateSummary();
        String summary2 = client.generateSummary();

        assertThat(summary1).isEqualTo(summary2);
    }

    @Test
    void client_can_give_total_amount_without_generating_summary_first() {
        assertThat(client.getTotalAmount()).isEqualTo(130.97);
    }

    @Test
    void client_should_generate_summary_fast() {
        String summary = client.generateSummaryFast();

        assertThat(summary).isEqualTo(
                "Tenet Deluxe Edition for 45.99€" + lineSeparator() +
                        "Inception for 30.5€" + lineSeparator() +
                        "The Dark Knight for 30.5€" + lineSeparator() +
                        "Interstellar for 23.98€" + lineSeparator() +
                        "Total : 130.97€");
    }

    @Test
    void client_does_generate_same_summary_twice_in_a_row_fast() {
        String summary1 = client.generateSummaryFast();
        String summary2 = client.generateSummaryFast();

        assertThat(summary1).isEqualTo(summary2);
    }

    @Test
    void client_does_generate_same_summary_slow_vs_fast() {
        String summary1 = client.generateSummary();
        String summary2 = client.generateSummaryFast();

        assertThat(summary1).isEqualTo(summary2);
    }
}