package darwinWorld.model.worldElements.animals.geneSelectionStrategies;

import darwinWorld.enums.MoveRotation;
import darwinWorld.utils.RandomNumberGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrazyGeneSelectionStrategyTest {
    private final CrazyGeneSelectionStrategy strategy = new CrazyGeneSelectionStrategy();

    @ParameterizedTest
    @CsvSource({
            "5, 4, 0, 0",  // 80% case, expects next index as currentGeneIndex + 1 (looping with modulo)
            "5, 3, 800, 4",  // 80% case with higher random seed
            "20, 10, 581430647310535, 3",  // 20% case, expects a random selection
    })
    void selectNextGene_UsingStrategy_ReturnsValidGeneIndex(int size, int currentIndex, long seed, int expected) {
        // Given
        List<MoveRotation> genes = generateGenes(size);
        RandomNumberGenerator.setSeed(seed);

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