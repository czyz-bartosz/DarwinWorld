package darwinWorld.model.map;

import darwinWorld.model.worldElements.Grass;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.model.worldElements.animals.owlBear.OwlBear;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WorldMapUtilsTest {

    @Test
    void getOccupiedPositionsTest() {
        WorldMap mockMap = mock(WorldMap.class);
        HashMap<Vector2d, HashSet<Animal>> animalsMap = new HashMap<>();
        HashMap<Vector2d, Grass> grassMap = new HashMap<>();

        // Add animals
        Animal animal = mock(Animal.class);
        animalsMap.put(new Vector2d(1, 1), new HashSet<>(Collections.singleton(animal)));
        animalsMap.put(new Vector2d(2, 2), new HashSet<>());

        // Add Grass
        grassMap.put(new Vector2d(3, 3), new Grass(new Vector2d(3, 3)));
        grassMap.put(new Vector2d(4, 4), new Grass(new Vector2d(3, 3)));

        // Set up mock behavior
        when(mockMap.getAnimals()).thenReturn(animalsMap);
        when(mockMap.getGrass()).thenReturn(grassMap);

        // Test method
        Collection<Vector2d> result = WorldMapUtils.getOccupiedPositions(mockMap);

        // Assertions
        assertEquals(3, result.size());
        assertTrue(result.contains(new Vector2d(1, 1)));
        assertFalse(result.contains(new Vector2d(2, 2)));
        assertTrue(result.contains(new Vector2d(3, 3)));
        assertTrue(result.contains(new Vector2d(4, 4)));
    }

    @Test
    void getOccupiedPositionsWithOwlBearTest() {
        WorldMap mockMap = mock(WorldMap.class);
        HashMap<Vector2d, HashSet<Animal>> animalsMap = new HashMap<>();
        HashMap<Vector2d, Grass> grassMap = new HashMap<>();
        OwlBear mockOwlBear = mock(OwlBear.class);

        // Add animals
        Animal animal = mock(Animal.class);
        animalsMap.put(new Vector2d(1, 1), new HashSet<>(Collections.singleton(animal)));
        animalsMap.put(new Vector2d(2, 2), new HashSet<>());

        // Add Grass
        grassMap.put(new Vector2d(3, 3), new Grass(new Vector2d(3, 3)));
        grassMap.put(new Vector2d(4, 4), new Grass(new Vector2d(4, 4)));

        // Set up owl bear
        when(mockOwlBear.getPosition()).thenReturn(new Vector2d(5, 5));
        when(mockMap.getOwlBear()).thenReturn(Optional.of(mockOwlBear));

        // Set up mock behavior
        when(mockMap.getAnimals()).thenReturn(animalsMap);
        when(mockMap.getGrass()).thenReturn(grassMap);

        // Test method
        Collection<Vector2d> result = WorldMapUtils.getOccupiedPositions(mockMap);

        // Assertions
        assertEquals(4, result.size());
        assertTrue(result.contains(new Vector2d(1, 1)));
        assertFalse(result.contains(new Vector2d(2, 2)));
        assertTrue(result.contains(new Vector2d(3, 3)));
        assertTrue(result.contains(new Vector2d(4, 4)));
        assertTrue(result.contains(new Vector2d(5, 5)));
    }

    @Test
    void getCollectionOfAnimalsEmptyMapTest() {
        WorldMap mockMap = mock(WorldMap.class);
        HashMap<Vector2d, HashSet<Animal>> animalsMap = new HashMap<>();

        // Set up mock behavior
        when(mockMap.getAnimals()).thenReturn(animalsMap);

        // Test method
        Collection<Animal> result = WorldMapUtils.getCollectionOfAnimals(mockMap);

        // Assertions
        assertTrue(result.isEmpty());
    }

    @Test
    void getCollectionOfAnimalsNonEmptyMapTest() {
        WorldMap mockMap = mock(WorldMap.class);
        HashMap<Vector2d, HashSet<Animal>> animalsMap = new HashMap<>();
        Animal mockAnimal1 = mock(Animal.class);
        Animal mockAnimal2 = mock(Animal.class);

        // Add animals
        HashSet<Animal> animalHashSet = new HashSet<>();
        animalHashSet.add(mockAnimal1);
        animalHashSet.add(mockAnimal2);
        animalsMap.put(new Vector2d(1, 1), animalHashSet);

        // Set up mock behavior
        when(mockMap.getAnimals()).thenReturn(animalsMap);

        // Test method
        Collection<Animal> result = WorldMapUtils.getCollectionOfAnimals(mockMap);

        // Assertions
        assertEquals(2, result.size());
        assertTrue(result.contains(mockAnimal1));
        assertTrue(result.contains(mockAnimal2));
    }
}
