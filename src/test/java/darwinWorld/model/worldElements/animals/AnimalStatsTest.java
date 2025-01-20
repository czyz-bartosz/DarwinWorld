package darwinWorld.model.worldElements.animals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

// The AnimalStatsTest class contains tests for the AnimalStats class,
// specifically for the 'getNumberOfEatenGrass' method. The 'getNumberOfEatenGrass'
// method returns the count of how many grass the animal has eaten in its lifetime.
public class AnimalStatsTest {
    private Animal animal;
    private AnimalStats animalStats;

    // Testing the case where the animal hasn't eaten any grass.
    @Test
    public void whenNoGrassEaten_thenReturnZero() {
        animal = Mockito.mock(Animal.class);
        animalStats = new AnimalStats(animal);

        assertEquals(0, animalStats.getNumberOfEatenGrass());
    }

    // Testing the case where the animal ate grass once.
    @Test
    public void whenOneGrassEaten_thenReturnOne() {
        animal = Mockito.mock(Animal.class);
        animalStats = new AnimalStats(animal);
        animalStats.incrementNumberOfEatenGrass();

        assertEquals(1, animalStats.getNumberOfEatenGrass());
    }

    // Testing the case where the animal ate grass multiple times.
    @Test
    public void whenMultipleGrassEaten_thenReturnTheSameNumber() {
        animal = Mockito.mock(Animal.class);
        animalStats = new AnimalStats(animal);
        for (int i = 0; i < 5; i++) {
            animalStats.incrementNumberOfEatenGrass();
        }

        assertEquals(5, animalStats.getNumberOfEatenGrass());
    }
}