package darwinWorld.model.worldElements.animals;

import darwinWorld.model.map.Vector2d;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import darwinWorld.enums.MoveRotation;
import darwinWorld.utils.RandomNumberGenerator;

import java.util.Arrays;
import java.util.List;

public class AnimalReproductionTest {

    @Test
        //The test checks the correctness of the reproduce method by comparing the energy and position of the child with the expectations 
    void reproduceCorrectly() {
        // Create mock parent1 and parent2
        Animal parent1 = Mockito.mock(Animal.class);
        Animal parent2 = Mockito.mock(Animal.class);

        // Set values to return from mocked parents for energy, position and genes
        when(parent1.getEnergy()).thenReturn(100);
        when(parent2.getEnergy()).thenReturn(100);
        when(parent1.getPosition()).thenReturn(new Vector2d(0, 0));
        List<MoveRotation> mockedGenes = Arrays.asList(MoveRotation.DEG_0, MoveRotation.DEG_90, MoveRotation.DEG_45);
        when(parent1.getGenes()).thenReturn(mockedGenes);
        when(parent2.getGenes()).thenReturn(mockedGenes);

        // Call reproduce
        Animal child = AnimalReproduction.reproduce(parent1, parent2, 50, 5, 1);

        // Check that the child has the right energy and position
        assertEquals(new Vector2d(0, 0), child.getPosition());
        assertEquals(100, child.getEnergy());

        // Check that the parents' afterReproduce method was called with the right parameters
        verify(parent1, times(1)).afterReproduce(50, child);
        verify(parent2, times(1)).afterReproduce(50, child);
    }
}