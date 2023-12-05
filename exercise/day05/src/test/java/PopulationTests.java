import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import people.Person;
import people.Pet;
import people.PetType;

import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static org.assertj.core.api.Assertions.assertThat;

class PopulationTests {
    private static List<Person> population;

    @BeforeAll
    static void setup() {
        population = Arrays.asList(
                new Person("Peter", "Griffin")
                        .addPet(PetType.CAT, "Tabby", 2),
                new Person("Stewie", "Griffin")
                        .addPet(PetType.CAT, "Dolly", 3)
                        .addPet(PetType.DOG, "Brian", 9),
                new Person("Joe", "Swanson")
                        .addPet(PetType.DOG, "Spike", 4),
                new Person("Lois", "Griffin")
                        .addPet(PetType.SNAKE, "Serpy", 1),
                new Person("Meg", "Griffin")
                        .addPet(PetType.BIRD, "Tweety", 1),
                new Person("Chris", "Griffin")
                        .addPet(PetType.TURTLE, "Speedy", 4),
                new Person("Cleveland", "Brown")
                        .addPet(PetType.HAMSTER, "Fuzzy", 1)
                        .addPet(PetType.HAMSTER, "Wuzzy", 2),
                new Person("Glenn", "Quagmire")
        );
    }

    @ParameterizedTest
    @CsvSource(delimiterString = ";", useHeadersInDisplayName = true,
            textBlock = """
                    personIndex;stringRepresentation
                    0;Peter Griffin who owns : Tabby
                    1;Stewie Griffin who owns : Dolly Brian
                    2;Joe Swanson who owns : Spike
                    3;Lois Griffin who owns : Serpy
                    4;Meg Griffin who owns : Tweety
                    5;Chris Griffin who owns : Speedy
                    6;Cleveland Brown who owns : Fuzzy Wuzzy
                    7;Glenn Quagmire
                      """)
    void personShouldHaveStringRepresentationOf(int personIndex, String stringRepresentation) {
        assertThat(population.get(personIndex))
                .hasToString(stringRepresentation);
    }

    @Test
    void whoOwnsTheYoungestPet() {
        var filtered = population.stream()
                .min(comparingInt(PopulationTests::youngestPetAgeOfThePerson))
                .orElse(null);

        assert filtered != null;
        assertThat(filtered.firstName()).isEqualTo("Lois");
    }

    private static int youngestPetAgeOfThePerson(Person person) {
        return person.pets()
                .stream()
                .mapToInt(Pet::age)
                .min()
                .orElse(Integer.MAX_VALUE);
    }
}
