package darwinWorld.controllers;

import darwinWorld.model.simulation.Simulation;
import darwinWorld.views.utils.MapGridPaneUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;


public class SimulationController {
    @FXML
    public GridPane mapGridPane;

    Simulation simulation;

    public void update() {
        Platform.runLater(() -> {
            MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap());
        });
    }

    public void init() {
        simulation = new Simulation(this);
        new Thread(simulation).start();
    }
}
