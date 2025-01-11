package darwinWorld.model.simulation.parameters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SimulationParametersValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "3, 4",
            "1, 1",
            "1, 5",
            "5, 1"
    })
    void validateMapSize_WithValidValues_DoesNotThrowException(int width, int height) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMapHeight(height)
                .setMapWidth(width)
                .build();

        assertDoesNotThrow(() -> SimulationParametersValidator.validate(params));
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "-1",
            "-100"
    })
    void validateMapSize_WithInvalidHeight_ThrowsException(int height) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMapHeight(height)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("mapHeight must be greater than 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "-1",
            "-100"
    })
    void validateMapSize_WithInvalidWidth_ThrowsException(int width) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMapWidth(width)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("mapWidth must be greater than 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1000000, 1000000, 1000000, 1000000",
            "1, 1, 1, 1",
            "100, 100, 100, 100",
            "2, 2, 2, 2",
            "0, 0, 0, 0"
    })
    void validateInitialParameters_WithValidValues_DoesNotThrowException(int grass, int animals, int energy, int growth) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setInitialGrassAmount(grass)
                .setInitialAnimalAmount(animals)
                .setInitialAnimalEnergy(energy)
                .setGrassGrowthPerDay(growth)
                .build();

        assertDoesNotThrow(() -> SimulationParametersValidator.validate(params));
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-100"
    })
    void validateInitialParameters_WithInvalidGrassAmount_ThrowsException(int initialGrassAmount) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setInitialGrassAmount(initialGrassAmount)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("initialGrassAmount must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-100"
    })
    void validateInitialParameters_WithInvalidGrassGrowthPerDay_ThrowsException(int grassGrowthPerDay) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setGrassGrowthPerDay(grassGrowthPerDay)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("grassGrowthPerDay must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-100"
    })
    void validateInitialParameters_WithInvalidAnimalAmount_ThrowsException(int initialAnimalAmount) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setInitialAnimalAmount(initialAnimalAmount)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("initialAnimalAmount must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-100"
    })
    void validateInitialParameters_WithInvalidInitialAnimalEnergy_ThrowsException(int initialAnimalEnergy) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setInitialAnimalEnergy(initialAnimalEnergy)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("initialAnimalEnergy must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "4, 2",
            "14, 14"
    })
    void validateReproductionParameters_WithValidValues_DoesNotThrowException(
            int minimalEnergyToReproduce,
            int energyUsedToReproduce
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMinimalEnergyToReproduce(minimalEnergyToReproduce)
                .setEnergyUsedToReproduce(energyUsedToReproduce)
                .build();

        assertDoesNotThrow(() -> SimulationParametersValidator.validate(params));
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-4",
            "-14"
    })
    void validateReproductionParameters_WithInvalidMinimalEnergyToReproduce_ThrowsException(
            int minimalEnergyToReproduce
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMinimalEnergyToReproduce(minimalEnergyToReproduce)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("minimalEnergyToReproduce must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-4",
            "-14"
    })
    void validateReproductionParameters_WithInvalidEnergyUsedToReproduce_ThrowsException(
            int energyUsedToReproduce
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setEnergyUsedToReproduce(energyUsedToReproduce)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("energyUsedToReproduce must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "4, 5",
            "14, 190"
    })
    void validateReproductionParameters_WithEnergyUsedToReproduceGreaterThanMinimalEnergyToReproduce_ThrowsException(
            int minimalEnergyToReproduce,
            int energyUsedToReproduce
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMinimalEnergyToReproduce(minimalEnergyToReproduce)
                .setEnergyUsedToReproduce(energyUsedToReproduce)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("energyUsedToReproduce must be less or equal minimalEnergyToReproduce", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 1",
            "4, 2, 43",
            "14, 14, 2"
    })
    void validateGenesParameters_WithValidValues_DoesNotThrowException(
            int maxMutations,
            int minMutations,
            int genesLength
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMaxMutations(maxMutations)
                .setMinMutations(minMutations)
                .setGenesLength(genesLength)
                .build();

        assertDoesNotThrow(() -> SimulationParametersValidator.validate(params));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "0, 5",
            "14, 190"
    })
    void validateGenesParameters_WithMaxMutationsLessThanMinMutations_ThrowsException(
            int maxMutations,
            int minMutations
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMaxMutations(maxMutations)
                .setMinMutations(minMutations)  // Invalid case: maxMutations < minMutations
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("maxMutations must be greater or equal minMutations", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-4",
            "-14"
    })
    void validateGenesParameters_WithInvalidMaxMutations_ThrowsException(
            int maxMutations
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMaxMutations(maxMutations)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("maxMutations must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "-4",
            "-14"
    })
    void validateGenesParameters_WithInvalidMinMutations_ThrowsException(
            int minMutations
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setMinMutations(minMutations)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("minMutations must be greater or equal 0", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "-1",
            "-4",
            "-14"
    })
    void validateGenesParameters_WithInvalidGenesLength_ThrowsException(
            int genesLength
    ) {
        SimulationParameters params = new SimulationParametersBuilder()
                .setGenesLength(genesLength)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> SimulationParametersValidator.validate(params));
        assertEquals("genesLength must be greater than 0", exception.getMessage());
    }
}
