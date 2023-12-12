package greeting;

public class Greeter {
    private Formality formality = new DefaultFormality();

    public String greet() {
        return formality.greet();
    }

    public void setFormality(Formality formality) {
        this.formality = formality;

        if (this.formality == null)
            this.formality = new DefaultFormality();
    }
}
