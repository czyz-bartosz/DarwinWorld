package darwinWorld.model.simulation.parameters;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationParametersBuilderTest {

    @Test
    void build_WithDefaultParameters_ReturnsSimulationParametersWithDefaultValues() {
        // Given&When
        SimulationParameters defaultParams = new SimulationParametersBuilder().build();

        // Then
        assertEquals(10, defaultParams.mapHeight());
        assertEquals(15, defaultParams.mapWidth());
        assertFalse(defaultParams.withOwlBear());
        assertEquals(40, defaultParams.initialGrassAmount());
        assertEquals(6, defaultParams.initialAnimalAmount());
        assertEquals(10, defaultParams.initialAnimalEnergy());
        assertEquals(6, defaultParams.grassGrowthPerDay());
        assertEquals(5, defaultParams.minimalEnergyToReproduce());
        assertEquals(2, defaultParams.energyUsedToReproduce());
        assertEquals(0, defaultParams.maxMutations());
        assertEquals(0, defaultParams.minMutations());
        assertFalse(defaultParams.crazyMutation());
        assertEquals(3, defaultParams.grassEnergy());
        assertEquals(5, defaultParams.genesLength());
        assertFalse(defaultParams.saveToCSV());
    }

    @Test
    void build_WithCustomParameters_ReturnsSimulationParametersWithCustomValues() {
        // Given&When
        SimulationParameters customParams = new SimulationParametersBuilder()
                .setMapHeight(20)
                .setMapWidth(25)
                .setWithOwlBear(true)
                .setInitialGrassAmount(100)
                .setInitialAnimalAmount(15)
                .setInitialAnimalEnergy(50)
                .setGrassGrowthPerDay(10)
                .setMinimalEnergyToReproduce(20)
                .setEnergyUsedToReproduce(10)
                .setMaxMutations(3)
                .setMinMutations(1)
                .setCrazyMutation(true)
                .setGrassEnergy(5)
                .setGenesLength(8)
                .setSaveToCSV(true)
                .build();

        // Then
        assertEquals(20, customParams.mapHeight());
        assertEquals(25, customParams.mapWidth());
        assertTrue(customParams.withOwlBear());
        assertEquals(100, customParams.initialGrassAmount());
        assertEquals(15, customParams.initialAnimalAmount());
        assertEquals(50, customParams.initialAnimalEnergy());
        assertEquals(10, customParams.grassGrowthPerDay());
        assertEquals(20, customParams.minimalEnergyToReproduce());
        assertEquals(10, customParams.energyUsedToReproduce());
        assertEquals(3, customParams.maxMutations());
        assertEquals(1, customParams.minMutations());
        assertTrue(customParams.crazyMutation());
        assertEquals(5, customParams.grassEnergy());
        assertEquals(8, customParams.genesLength());
        assertTrue(customParams.saveToCSV());
    }
}
