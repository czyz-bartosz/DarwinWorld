package darwinWorld.model.worldElements.animals.geneSelectionStrategies;

import darwinWorld.enums.MoveRotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SequentialGeneSelectionStrategyTest {
    private final SequentialGeneSelectionStrategy strategy = new SequentialGeneSelectionStrategy();

    @ParameterizedTest
    @CsvSource({
            "5, 4, 0",
            "5, 3, 4"
    })
    void selectNextGene_UsingStrategy_ReturnsValidGeneIndex(int size, int currentIndex, int expected) {
        // Given
        List<MoveRotation> genes = generateGenes(size);

        // When
        int result = strategy.selectNextGene(genes, currentIndex);

        // Then
        assertTrue(result >= 0 && result < size,
                "Result " + result + " is out of valid range [0, " + (size - 1) + "]");
        assertEquals(expected, result,
                "Result " + result + " does not match expected value " + expected);
    }

    private List<MoveRotation> generateGenes(int size) {
        return Arrays.asList(new MoveRotation[size]);
    }
}