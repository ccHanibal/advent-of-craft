package greeting;

public class DefaultFormality implements Formality {
    @Override
    public String greet() {
        return "Hello.";
    }
}
