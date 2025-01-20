package darwinWorld.model.simulation;

import darwinWorld.model.map.*;
import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.AnimalStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SimulationStatsTest {

    @Test
    @DisplayName("Check getNumberOfAnimals method in normal flow")
    public void testGetNumberOfAnimals() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        WorldMap mapMock = Mockito.mock(WorldMap.class);
        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        when(simulationMock.getMap()).thenReturn(mapMock);
        HashMap<Vector2d, HashSet<Animal>> animals = new HashMap<>();
        HashSet<Animal> animalsInOneCell = new HashSet<>();
        animalsInOneCell.add(Mockito.mock(Animal.class));
        animals.put(new Vector2d(0, 0), animalsInOneCell);
        animals.put(new Vector2d(0, 1), new HashSet<>());
        when(mapMock.getAnimals()).thenReturn(animals);

        SimulationStats simulationStats = new SimulationStats(simulationMock);
        assertEquals(1, simulationStats.getNumberOfAnimals());
    }

    @Test
    @DisplayName("Check getNumberOfGrass method in normal flow")
    public void testGetNumberOfGrass() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        WorldMap mapMock = Mockito.mock(WorldMap.class);
        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        when(simulationMock.getMap()).thenReturn(mapMock);
        HashMap<Vector2d, Grass> grass = new HashMap<>();
        grass.put(new Vector2d(0, 0), Mockito.mock(Grass.class));
        when(mapMock.getGrass()).thenReturn(grass);

        SimulationStats simulationStats = new SimulationStats(simulationMock);
        assertEquals(1, simulationStats.getNumberOfGrass());

    }

    @Test
    @DisplayName("Check getNumberOfFreeCells method in normal flow")
    public void testGetNumberOfFreeCells() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        WorldMap mapMock = Mockito.mock(WorldMap.class);
        Earth earthAreaMock = Mockito.mock(Earth.class);

        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        when(simulationMock.getMap()).thenReturn(mapMock);
        when(mapMock.getEarth()).thenReturn(earthAreaMock);
        when(earthAreaMock.getBoundary()).thenReturn(new Boundary(new Vector2d(0, 0), new Vector2d(4,4)));
        SimulationStats simulationStats = new SimulationStats(simulationMock);
        try(MockedStatic<WorldMapUtils> utils = Mockito.mockStatic(WorldMapUtils.class)) {
            utils.when(() -> WorldMapUtils.getOccupiedPositions(mapMock)).thenReturn(List.of(new Vector2d(0, 0), new Vector2d(1, 1), new Vector2d(2, 2), new Vector2d(3, 3)));
            assertEquals(21, simulationStats.getNumberOfFreeCells());
        }
        assertEquals(25, simulationStats.getNumberOfFreeCells());
    }

    @Test
    @DisplayName("Check getAvgEnergy method in normal flow")
    public void testGetAvgEnergy() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        WorldMap mapMock = Mockito.mock(WorldMap.class);
        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        when(simulationMock.getMap()).thenReturn(mapMock);
        when(mapMock.getAnimals()).thenReturn(new HashMap<>());

        SimulationStats simulationStats = new SimulationStats(simulationMock);
        assertEquals(0.0, simulationStats.getAvgEnergy());
    }

    @Test
    @DisplayName("Check getStats method in normal flow")
    public void testGetStats() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        WorldMap mapMock = Mockito.mock(WorldMap.class);
        Earth earthAreaMock = Mockito.mock(Earth.class);

        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        when(simulationMock.getMap()).thenReturn(mapMock);
        when(mapMock.getAnimals()).thenReturn(new HashMap<>());
        when(mapMock.getGrass()).thenReturn(new HashMap<>());
        when(mapMock.getEarth()).thenReturn(earthAreaMock);
        when(earthAreaMock.getBoundary()).thenReturn(new Boundary(new Vector2d(0, 0), new Vector2d(4,4)));

        SimulationStats simulationStats = new SimulationStats(simulationMock);
        Map<String, String> stats = simulationStats.getStats();

        assertEquals("0", stats.get("numberOfAnimals"));
        assertEquals("0", stats.get("numberOfGrass"));
        assertEquals("Optional.empty", stats.get("theMostPopularGenotype"));
        assertTrue(Double.compare(0.0, Double.parseDouble(stats.get("avgEnergy"))) == 0);
    }

    @Test
    @DisplayName("Check getSumOfDeadAnimalsLifeSpan method with no dead animals")
    public void testGetSumOfDeadAnimalsLifeSpan_noDeadAnimals() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        SimulationStats simulationStats = new SimulationStats(simulationMock);
        assertEquals(0, simulationStats.getSumOfDeadAnimalsLifeSpan());
    }

    @Test
    @DisplayName("Check getSumOfDeadAnimalsLifeSpan method after 1 animal has died")
    public void testGetSumOfDeadAnimalsLifeSpan_oneDeadAnimal() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));
        SimulationStats simulationStats = new SimulationStats(simulationMock);
        Animal animalMock = createMockedAnimal(2);

        simulationStats.onAnimalDeath(animalMock);
        assertEquals(2, simulationStats.getSumOfDeadAnimalsLifeSpan());
    }

    @Test
    @DisplayName("Check getSumOfDeadAnimalsLifeSpan method after multiple animals have died")
    public void testGetSumOfDeadAnimalsLifeSpan_multipleDeadAnimals() {
        Simulation simulationMock = Mockito.mock(Simulation.class);
        when(simulationMock.getUuid()).thenReturn(new UUID(0, 0));

        SimulationStats simulationStats = new SimulationStats(simulationMock);

        // Use helper method to create and mock animals
        Animal firstAnimal = createMockedAnimal(3);
        Animal secondAnimal = createMockedAnimal(5);

        // Simulate the death of mocked animals
        simulationStats.onAnimalDeath(firstAnimal);
        simulationStats.onAnimalDeath(secondAnimal);

        // Assert that the sum of lifespans is correctly calculated
        assertEquals(8, simulationStats.getSumOfDeadAnimalsLifeSpan());
    }

    // Helper method to reduce repeated mocking code
    private Animal createMockedAnimal(int lifeSpan) {
        Animal animalMock = Mockito.mock(Animal.class);
        AnimalStats animalStatsMock = Mockito.mock(AnimalStats.class);

        when(animalStatsMock.getNumberOfDaysOfLife()).thenReturn(lifeSpan);
        when(animalMock.getStats()).thenReturn(animalStatsMock);

        return animalMock;
    }

}
