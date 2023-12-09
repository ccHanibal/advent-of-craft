package account;

import java.util.Map;
import java.util.stream.Collectors;

public class Client {
    private final Map<String, Double> orderLines;

    public Client(Map<String, Double> orderLines) {
        this.orderLines = orderLines;
    }

    /**
     * Generates a summary of all orders, their prices and the total amount.
     */
    @Deprecated
    public String generateSummary() {
        return orderLines.entrySet().stream()
                .map(entry -> formatLine(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()))
                .concat(System.lineSeparator() + "Total : " + getTotalAmount() + "€");
    }
    /**
     ** Memory optimized version of {@link #generateSummary()} by summing
     * the total amount on the way, therefore iterating the orders only once.
     **/
    public String generateSummaryFast() {
        double totalAmount = 0.0;
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Double> entry : orderLines.entrySet()) {
            totalAmount += entry.getValue();
            result.append(formatLine(entry.getKey(), entry.getValue()))
                    .append(System.lineSeparator());
        }

        return result.append("Total : ").append(totalAmount).append("€")
                .toString();
    }

    /**
     * Sums up all prices of the order.
     */
    public double getTotalAmount() {
        return orderLines.values().stream()
                .mapToDouble(v -> v)
                .sum();
    }

    private String formatLine(String name, Double value) {
        return name + " for " + value + "€";
    }
}

