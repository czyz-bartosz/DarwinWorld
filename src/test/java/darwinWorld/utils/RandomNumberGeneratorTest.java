package darwinWorld.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomNumberGeneratorTest {

    @ParameterizedTest
    @CsvSource({
            "0, 10, 0, 0",
            "0, 10, 12341, 10",
            "-5, 5, 0, -5",
            "-5, 5, 67890, 5"
    })
    void integerFromRange_WithSeed_ReturnsCorrectBoundaryValues(int min, int max, long seed, int expected) {
        // Given
        RandomNumberGenerator.setSeed(seed);

        // When
        int result = RandomNumberGenerator.integerFromRange(min, max);

        // Then
        assertEquals(expected, result,
                "Result " + result + " does not match expected value " + expected + " for seed " + seed);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 99999",
            "0, 0, 11111",
            "-10, -10, 22222"
    })
    void integerFromRange_WithEqualMinAndMax_ReturnsSameValue(int min, int max, long seed) {
        // Given
        RandomNumberGenerator.setSeed(seed);

        // When
        int result = RandomNumberGenerator.integerFromRange(min, max);

        // Then
        assertEquals(min, result,
                "Result " + result + " does not equal the only possible value " + min);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 10, 33333",
            "-10, 10, 44444",
            "100, 200, 55555"
    })
    void integerFromRange_WithSeed_AlwaysReturnsWithinRange(int min, int max, long seed) {
        // Given
        RandomNumberGenerator.setSeed(seed);

        // When
        int result = RandomNumberGenerator.integerFromRange(min, max);

        // Then
        assertTrue(result >= min && result <= max,
                "Result " + result + " is not within the range [" + min + ", " + max + "]");
    }
}
