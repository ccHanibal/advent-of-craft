package document;

import org.approvaltests.Approvals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DocumentTests {

    public static Stream<Arguments> allCombinations() {
        ArrayList<Arguments> combinations = new ArrayList<>();
        var allDocumentTypes =
                Arrays.stream(DocumentTemplateType.values())
                        .map(DocumentTemplateType::getDocumentType)
                        .distinct()
                        .toList();

        for (var docType : allDocumentTypes) {
            for (var recordType : RecordType.values()) {
                combinations.add(Arguments.of(docType, recordType.toString()));
            }
        }

        return combinations.stream();
    }

    public static Stream<Arguments> validCombinations() {
        var valids =
                new ArrayList<>(
                        Arrays.stream(DocumentTemplateType.values())
                                .map(a -> Arguments.of(a.getDocumentType(), a.getRecordType().toString()))
                                .toList());

        var docTypesWithRecordTypeAll =
                Arrays.stream(DocumentTemplateType.values())
                        .filter(dtt -> dtt.getRecordType().equals(RecordType.ALL))
                        .map(DocumentTemplateType::getDocumentType)
                        .toList();

        for (var docType : docTypesWithRecordTypeAll) {
            valids.add(Arguments.of(docType, RecordType.INDIVIDUAL_PROSPECT.toString()));
            valids.add(Arguments.of(docType, RecordType.LEGAL_PROSPECT.toString()));
        }

        return valids.stream();
    }

    public static Stream<Arguments> invalidCombinations() {
        var validCombinations =
                validCombinations()
                    .map(Arguments::get)
                    .toList();
        var result =
                new ArrayList<>(
                    allCombinations()
                        .map(Arguments::get)
                        .toList());

        for (var validCombination : validCombinations) {
            for (int a = 0; a < result.size(); a++) {
                var combination = result.get(a);
                if (combination[0].equals(validCombination[0]) && combination[1].equals(validCombination[1])) {
                    result.remove(a);
                    break;
                }
            }
        }

        return result.stream()
                .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("validCombinations")
    public void canParseDocumentTemplateTypeForValidValues(String documentType, String recordType)
    {
        Approvals.verify(
                DocumentTemplateType.fromDocumentTypeAndRecordType(documentType, recordType),
                Approvals.NAMES.withParameters(documentType, recordType));
    }

    @ParameterizedTest
    @MethodSource("invalidCombinations")
    public void failsToParseDocumentTemplateTypeForInvalidValues(String documentType, String recordType)
    {
        assertThatThrownBy(() -> DocumentTemplateType.fromDocumentTypeAndRecordType(documentType, recordType))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
