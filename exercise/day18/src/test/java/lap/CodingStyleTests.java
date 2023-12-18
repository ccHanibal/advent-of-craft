package lap;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

public class CodingStyleTests {

    private static final JavaClasses importedClasses =
            new ClassFileImporter()
                    .withImportOption(new ImportOption.DoNotIncludeTests())
                    .importPackages("lap");

    @Test
    void getter_methods_should_return_something() {
        var rule = methods().that().haveNameStartingWith("get")
                .should().bePublic()
                .andShould(notBeVoid());

        rule.check(importedClasses);
    }

    @Test
    void flag_methods_should_return_boolean() {
        var rule = methods().that().haveNameStartingWith("is")
                .should().bePublic()
                .andShould().haveRawReturnType(boolean.class);

        rule.check(importedClasses);
    }

    @Test
    void fieldnames_should_not_contain_consecutive_unterscores() {
        var rule = fields().that().arePrivate()
                .or().areProtected()
                .should().haveNameNotMatching(".*__.*");

        rule.check(importedClasses);
    }

    @Test
    void fieldnames_should_not_start_with_unterscores() {
        var rule = fields().that().arePrivate()
                .or().areProtected()
                .should().haveNameNotStartingWith("_");

        rule.check(importedClasses);
    }

    private static ArchCondition<JavaMethod> notBeVoid() {
        return new ArchCondition<JavaMethod>("return type is not void") {
            @Override
            public void check(JavaMethod javaMethod, ConditionEvents conditionEvents) {
                if (javaMethod.getReturnType().getName().equals("void"))
                    throw new AssertionError("Method " + javaMethod.getName() + " has void return type");
            }
        };
    }
}
