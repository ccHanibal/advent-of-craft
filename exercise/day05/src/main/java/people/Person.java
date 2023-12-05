package people;

import java.util.ArrayList;
import java.util.List;

public record Person(String firstName, String lastName, List<Pet> pets) {
    public Person(String firstName, String lastName) {
        this(firstName, lastName, new ArrayList<>());
    }

    public Person addPet(PetType petType, String name, int age) {
        pets.add(new Pet(petType, name, age));
        return this;
    }

    @Override
    public String toString() {
        final var result = new StringBuilder();

        result.append(firstName).append(" ").append(lastName);
        if (!pets.isEmpty()) {
            result.append(" who owns : ");

            var petNames = String.join(" ", pets.stream().map(pet -> pet.name()).toList());
            result.append(petNames);
        }

        return result.toString();
    }
}
