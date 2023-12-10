package games;

public class FizzBuzzStrategy {

    private final int divisor;
    private final String resultText;

    public FizzBuzzStrategy(int divisor, String resultText) {

        this.divisor = divisor;
        this.resultText = resultText;
    }

    public boolean applies(int input) {
        return input % divisor == 0;
    }
    public String getResultText() {
        return resultText;
    }
}
