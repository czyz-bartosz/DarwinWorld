package darwinWorld.controllers;

import darwinWorld.model.simulation.Simulation;
import darwinWorld.views.utils.MapGridPaneUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class SimulationController {
    @FXML
    public GridPane mapGridPane;
    @FXML
    public Text numberOfAnimalsField;
    @FXML
    public Text numberOfGrassField;
    @FXML
    public Text numberOfFreeCellsField;
    @FXML
    public Text theMostPopularGenotypeField;
    @FXML
    public Text avgEnergyField;
    @FXML
    public Text avgLifeSpanField;
    @FXML
    public Text avgNumberOfChildrenField;

    Simulation simulation;

    private void updateSimulationStatsView() {
        numberOfAnimalsField.textProperty().setValue(Integer.toString(simulation.getStats().getNumberOfAnimals()));
        numberOfGrassField.textProperty().setValue(Integer.toString(simulation.getStats().getNumberOfGrass()));
        numberOfFreeCellsField.textProperty().setValue(Integer.toString(simulation.getStats().getNumberOfFreeCells()));
        theMostPopularGenotypeField.textProperty().setValue(simulation.getStats().getTheMostPopularGenotype().toString());
        avgEnergyField.textProperty().setValue(Double.toString(simulation.getStats().getAvgEnergy()));
        avgLifeSpanField.textProperty().setValue(Double.toString(simulation.getStats().getAvgLifeSpan()));
        avgNumberOfChildrenField.textProperty().setValue(Double.toString(simulation.getStats().getAvgNumberOfChildrenPerAnimal()));
    }

    public void update() {
        updateSimulationStatsView();
        Platform.runLater(() -> {
            MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap());
        });
    }

    public void init() {
        simulation = new Simulation(this);
        new Thread(simulation).start();
    }
}
