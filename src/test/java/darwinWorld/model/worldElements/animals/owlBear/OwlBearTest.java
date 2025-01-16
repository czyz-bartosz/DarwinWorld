package darwinWorld.model.worldElements.animals.owlBear;

import darwinWorld.enums.MoveRotation;
import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.ILocationProvider;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.worldElements.animals.geneSelectionStrategies.IGeneSelectionStrategy;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

public class OwlBearTest {

    /**
     * The OwlBearTest class is designed to test the behavior of the OwlBear class,
     * focusing on the `getGraphicalRepresentation` method.
     */

    @Test
    public void getGraphicalRepresentationTest() {

        // Create a mock of `OwlBear` using Mockito 
        OwlBear owlBear = Mockito.mock(OwlBear.class, Mockito.CALLS_REAL_METHODS);

        // Test `getGraphicalRepresentation` method
        Pane pane = owlBear.getGraphicalRepresentation();

        // Test assertions
        Assertions.assertTrue(pane instanceof StackPane, "The pane should be an instance of StackPane");
        Assertions.assertEquals("transparent;", pane.getStyle().replace("-fx-background-color: ", ""), "Background color should be transparent");

        Node node = pane.getChildren().get(0);
        Assertions.assertTrue(node instanceof Circle, "The first child should be an instance of Circle");
        Circle circle = (Circle) node;
        Assertions.assertEquals(Color.web("#fc8803"), circle.getFill(), "Circle color should match #fc8803");
    }

    /**
     * The test for the 'move' method in the OwlBear class.
     * The owlbear should move to a new position updated based on the gene selection strategy and location provider.
     */
    @Test
    public void moveTest() {
        // Mock dependencies
        IGeneSelectionStrategy geneSelectionStrategy = Mockito.mock(IGeneSelectionStrategy.class);
        ILocationProvider locationProvider = Mockito.mock(ILocationProvider.class);

        // Prepare test data
        List<MoveRotation> genes = Collections.singletonList(MoveRotation.DEG_0);
        int currentGeneIndex = 0;
        List<Boundary> territory = Collections.singletonList(new Boundary(new Vector2d(0, 0), new Vector2d(5, 5)));
        OwlBear owlBear = new OwlBear(new Vector2d(0, 0), MoveRotation.DEG_0, genes, currentGeneIndex, territory);

        // Stub the behaviours of mocked dependencies
        Mockito.when(geneSelectionStrategy.selectNextGene(genes, currentGeneIndex)).thenReturn(0);
        Mockito.when(locationProvider.getPosition(new Vector2d(0, 1))).thenReturn(new Vector2d(0, 1));

        // Call the method under test
        owlBear.move(geneSelectionStrategy, locationProvider);

        // Validate the results
        Mockito.verify(geneSelectionStrategy).selectNextGene(genes, currentGeneIndex);
        Mockito.verify(locationProvider).getPosition(new Vector2d(0, 1));
        Assertions.assertEquals(new Vector2d(0, 1), owlBear.getPosition(), "OwlBear should move to the new position");
    }
}
