package darwinWorld.views.utils;

import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.map.WorldMap;
import darwinWorld.model.worldElements.IWorldElement;
import darwinWorld.model.worldElements.animals.Animal;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Set;

public class MapGridPaneUtils {
    public static final int DEFAULT_WIDTH = 75;
    public static final int DEFAULT_HEIGHT = 53;

    private static void clearGrid(GridPane mapGridPane) {
        mapGridPane.getChildren().retainAll(mapGridPane.getChildren().getFirst());
        mapGridPane.getColumnConstraints().clear();
        mapGridPane.getRowConstraints().clear();
    }

    public static void generateGrid(GridPane mapGridPane, WorldMap worldMap, Animal selectedAnimal, Vector2d center) {
        clearGrid(mapGridPane);

        int halfWidth = DEFAULT_WIDTH / 2;
        int halfHeight = DEFAULT_HEIGHT / 2;

        Boundary visibleBounds = new Boundary(
                new Vector2d(center.getX() - halfWidth, center.getY() - halfHeight),
                new Vector2d(center.getX() + halfWidth, center.getY() + halfHeight)
        );

        for (int x = visibleBounds.lowerLeft().getX(); x <= visibleBounds.upperRight().getX(); x++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(10);
            mapGridPane.getColumnConstraints().add(column);
        }

        for (int y = visibleBounds.lowerLeft().getY(); y <= visibleBounds.upperRight().getY(); y++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(10);
            mapGridPane.getRowConstraints().add(row);
        }

        updateVisibleGrid(mapGridPane, worldMap, selectedAnimal, visibleBounds);
    }

    public static void updateVisibleGrid(GridPane mapGridPane, WorldMap worldMap, Animal selectedAnimal, Boundary visibleBounds) {
        int shiftX = visibleBounds.lowerLeft().getX();
        int shiftY = visibleBounds.lowerLeft().getY();
        int height = visibleBounds.upperRight().getY() - visibleBounds.lowerLeft().getY();

        for (int y = visibleBounds.lowerLeft().getY(); y <= visibleBounds.upperRight().getY(); y++) {
            for (int x = visibleBounds.lowerLeft().getX(); x <= visibleBounds.upperRight().getX(); x++) {
                Vector2d position = new Vector2d(x, y);

                Pane cellPane = new Pane();
                if (worldMap.getEarth().getBoundary().contains(position)) {
                    // Highlight the map area
                    cellPane.setStyle("-fx-background-color: lightgray;");

                    if (worldMap.objectsAt(position) != null) {
                        IWorldElement worldElement = worldMap.objectsAt(position).iterator().next();
                        cellPane = worldElement.getGraphicalRepresentation();
                    }
                } else {
                    // Empty grid remains white
                    cellPane.setStyle("-fx-background-color: white;");
                }

                mapGridPane.add(cellPane, x - shiftX, height - y + shiftY);
            }
        }
    }
}
