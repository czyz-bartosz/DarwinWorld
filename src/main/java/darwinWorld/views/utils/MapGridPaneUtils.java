package darwinWorld.views.utils;

import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.map.WorldMap;
import darwinWorld.model.worldElements.IWorldElement;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class MapGridPaneUtils {
    private static void clearGrid(GridPane mapGridPane) {
        mapGridPane.getChildren().retainAll(mapGridPane.getChildren().getFirst());
        mapGridPane.getColumnConstraints().clear();
        mapGridPane.getRowConstraints().clear();
    }

    public static void generateGrid(GridPane mapGridPane, WorldMap worldMap) {
        clearGrid(mapGridPane);
        Boundary bounds = worldMap.getEarth().getBoundary();
        int shiftX = bounds.lowerLeft().getX();
        int shiftY = bounds.lowerLeft().getY();
        int height = bounds.upperRight().getY() - bounds.lowerLeft().getY();

        for (int x = bounds.lowerLeft().getX(); x <= bounds.upperRight().getX(); x++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setMinWidth(10);
            mapGridPane.getColumnConstraints().add(column);
        }
        for (int y = bounds.lowerLeft().getY(); y <= bounds.upperRight().getY(); y++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(10);
            mapGridPane.getRowConstraints().add(row);
        }

        for (int y = bounds.lowerLeft().getY(); y <= bounds.upperRight().getY(); y++) {
            for (int x = bounds.lowerLeft().getX(); x <= bounds.upperRight().getX(); x++) {
                if(worldMap.objectsAt(new Vector2d(x, y)) == null) {
                    continue;
                }
                IWorldElement worldElement = worldMap.objectsAt(new Vector2d(x, y)).iterator().next();
                mapGridPane.add(worldElement.getGraphicalRepresentation(), x - shiftX, height - y + shiftY);
            }
        }
    }
}
