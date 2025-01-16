package darwinWorld.model.worldElements.animals;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.map.ILocationProvider;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class AnimalTest {

    @Test
    void getDescendants_NoChildren_ReturnEmptyCollection() {
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        assertTrue(animal.getDescendants().isEmpty());
    }

    @Test
    void getDescendants_WithChildren_ReturnChild() {
        Animal parent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        Animal child = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);

        parent.afterReproduce(1, child);

        Collection<Animal> descendants = parent.getDescendants();
        assertTrue(descendants.contains(child));
        assertEquals(1, descendants.size());
    }

    @Test
    void getDescendants_WithGrandChildren_ReturnAllDescendants() {
        Animal grandParent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        Animal parent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        Animal child = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);

        grandParent.afterReproduce(1, parent);
        parent.afterReproduce(1, child);

        Collection<Animal> descendants = grandParent.getDescendants();

        assertTrue(descendants.contains(parent));
        assertTrue(descendants.contains(child));
        assertEquals(2, descendants.size());
    }

    @Test
    void getChildren_NoChildren_ReturnsEmptyCollection() {
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        assertTrue(animal.getChildren().isEmpty());
    }

    @Test
    void getChildren_OneChild_ReturnsCollectionWithChild() {
        Animal parent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        Animal child = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);

        parent.afterReproduce(1, child);

        Collection<Animal> children = parent.getChildren();
        assertTrue(children.contains(child));
        assertEquals(1, children.size());
    }

    @Test
    void getChildren_MultipleChildren_ReturnsCollectionWithAllChildren() {
        Animal parent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        Animal child1 = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        Animal child2 = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);

        parent.afterReproduce(1, child1);
        parent.afterReproduce(1, child2);

        Collection<Animal> children = parent.getChildren();
        assertTrue(children.contains(child1));
        assertTrue(children.contains(child2));
        assertEquals(2, children.size());
    }

    @Test
    void afterReproduce_BeforeReproduction_EnergyChangesAndChildAddedAsDescendant() {
        Animal parent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 10);
        Animal child = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);

        int energyBefore = parent.getEnergy();
        parent.afterReproduce(7, child);

        assertEquals(3, parent.getEnergy());
        assertEquals(energyBefore - 7, parent.getEnergy());
        assertTrue(parent.getChildren().contains(child));
    }

    @Test
    void afterReproduce_AfterReproduction_EnergyDoesNotChangeAndNoExtraDescendant() {
        Animal parent = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 10);
        Animal child = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);

        int energyBefore = parent.getEnergy();
        parent.afterReproduce(2, child);
        int energyAfter = energyBefore - 2;

        assertEquals(energyAfter, parent.getEnergy());
    }

    @Test
    void eat_GainGivenEnergyAndIncrementEatenGrassCount() {
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 5);
        int initialEnergy = animal.getEnergy();
        int initialNumberOfEatenGrass = animal.getStats().getNumberOfEatenGrass();

        animal.eat(10);

        assertEquals(initialEnergy + 10, animal.getEnergy());
        assertEquals(initialNumberOfEatenGrass + 1, animal.getStats().getNumberOfEatenGrass());
    }

    @Test
    void move_WithMockedStrategyAndLocationProvider_MoveIsMadeAndEnergyDecreasedByOne() {
        //Mocking dependencies
        IGeneSelectionStrategy mockedGeneSelectionStrategy = Mockito.mock(IGeneSelectionStrategy.class);
        ILocationProvider mockedLocationProvider = Mockito.mock(ILocationProvider.class);

        //Preparing test object
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 10);

        //Setting up expectations for mock behavior
        Mockito.when(mockedGeneSelectionStrategy.selectNextGene(any(), anyInt())).thenReturn(0);
        Mockito.when(mockedLocationProvider.getPosition(any())).thenReturn(new Vector2d(6, 6));
        Mockito.when(mockedLocationProvider.getRotation(any(), any())).thenReturn(MoveRotation.DEG_0);

        int initialEnergy = animal.getEnergy();

        //Making actual method call
        animal.move(mockedGeneSelectionStrategy, mockedLocationProvider);

        //Asserting post conditions
        assertEquals(new Vector2d(6, 6), animal.getPosition());
        assertEquals(MoveRotation.DEG_0, animal.getRotation());
        assertEquals(initialEnergy - 1, animal.getEnergy());
    }

    @Test
    void move_WithMockedStrategyAndLocationProvider_PositionAndRotationAreChangedAsExpected() {
        //Mocking dependencies
        IGeneSelectionStrategy mockedGeneSelectionStrategy = Mockito.mock(IGeneSelectionStrategy.class);
        ILocationProvider mockedLocationProvider = Mockito.mock(ILocationProvider.class);

        //Preparing test object
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 10);

        //Setting up expectations for mock behavior
        Mockito.when(mockedGeneSelectionStrategy.selectNextGene(any(), anyInt())).thenReturn(0);
        Mockito.when(mockedLocationProvider.getPosition(any())).thenReturn(new Vector2d(6, 6));
        Mockito.when(mockedLocationProvider.getRotation(any(), any())).thenReturn(MoveRotation.DEG_180);

        //Making actual method call
        animal.move(mockedGeneSelectionStrategy, mockedLocationProvider);

        //Asserting post conditions
        assertEquals(new Vector2d(6, 6), animal.getPosition());
        assertEquals(MoveRotation.DEG_180, animal.getRotation());
    }

    @Test
    void kill_AnimalKilled_DayOfDeathInStatsUpdated() {
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 10);
        int currentDay = 100;

        animal.kill(currentDay);

        assertEquals(currentDay, animal.getStats().getDayOfDeath().getAsInt());
    }
    @Test
    void kill_SameAnimalKilledTwice_OnlyFirstKillDayIsApplied() {
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Collections.singletonList(MoveRotation.DEG_0), 0, 10);
        int firstDay = 50;
        int secondDay = 100;

        animal.kill(firstDay);
        animal.kill(secondDay);

        assertEquals(firstDay, animal.getStats().getDayOfDeath().getAsInt());
    }

    @Test
    void getGeneCurrentIndex_ReturnCorrectIndex() {
        Animal animal = new Animal(new Vector2d(5, 5), MoveRotation.DEG_0, Arrays.asList(MoveRotation.DEG_0, MoveRotation.DEG_90, MoveRotation.DEG_45), 0, 10);
        assertEquals(0, animal.getGeneCurrentIndex());
    }
}