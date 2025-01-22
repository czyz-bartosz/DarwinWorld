package darwinWorld.controllers;

import darwinWorld.model.map.Vector2d;
import darwinWorld.model.map.WorldMap;
import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.views.utils.MapGridPaneUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
    @FXML
    public Button stopBtn;
    @FXML
    public Text genotypeField;
    @FXML
    public Text currentIndexOfGenotypeField;
    @FXML
    public Text energyField;
    @FXML
    public Text eatenGrassField;
    @FXML
    public Text numberOfChildrenField;
    @FXML
    public Text numberOfDescendantsField;
    @FXML
    public Text daysOfLifeField;
    @FXML
    public Text dayOfDeathField;
    @FXML
    public ScrollPane gridScrollPane;
    Simulation simulation;
    Animal selectedAnimal = null;
    Vector2d center = new Vector2d(0, 0);

    private double lastMouseX;
    private double lastMouseY;

    public void addMouseControl(GridPane mapGridPane, WorldMap worldMap, Animal selectedAnimal) {
        mapGridPane.setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });

        mapGridPane.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastMouseX;
            double deltaY = event.getSceneY() - lastMouseY;

            int deltaCellsX = (int) (deltaX/4);
            int deltaCellsY = (int) (deltaY/4);

            center = new Vector2d(center.getX() - deltaCellsX, center.getY() + deltaCellsY);

            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();

            MapGridPaneUtils.generateGrid(mapGridPane, worldMap, selectedAnimal, center, this);
        });
    }

    private void updateSimulationStatsView() {
        numberOfAnimalsField.textProperty().setValue(Integer.toString(simulation.getStats().getNumberOfAnimals()));
        numberOfGrassField.textProperty().setValue(Integer.toString(simulation.getStats().getNumberOfGrass()));
        numberOfFreeCellsField.textProperty().setValue(Integer.toString(simulation.getStats().getNumberOfFreeCells()));
        theMostPopularGenotypeField.textProperty().setValue(simulation.getStats().getTheMostPopularGenotype().toString());
        avgEnergyField.textProperty().setValue(Double.toString(simulation.getStats().getAvgEnergy()));
        avgLifeSpanField.textProperty().setValue(Double.toString(simulation.getStats().getAvgLifeSpan()));
        avgNumberOfChildrenField.textProperty().setValue(Double.toString(simulation.getStats().getAvgNumberOfChildrenPerAnimal()));
    }

    private void updateAnimalStatsView() {
        if(selectedAnimal != null) {
            genotypeField.textProperty().setValue(selectedAnimal.getGenes().toString());
            currentIndexOfGenotypeField.textProperty().setValue(Integer.toString(selectedAnimal.getGeneCurrentIndex()));
            energyField.textProperty().setValue(Double.toString(selectedAnimal.getEnergy()));
            eatenGrassField.textProperty().setValue(Integer.toString(selectedAnimal.getStats().getNumberOfEatenGrass()));
            numberOfChildrenField.textProperty().setValue(Integer.toString(selectedAnimal.getStats().getNumberOfChildren()));
            numberOfDescendantsField.textProperty().setValue(Integer.toString(selectedAnimal.getStats().getNumberOfDescendants()));
            daysOfLifeField.textProperty().setValue(Integer.toString(selectedAnimal.getStats().getNumberOfDaysOfLife()));
            dayOfDeathField.textProperty().setValue(selectedAnimal.getStats().getDayOfDeath().toString());
        }
    }
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;

        addMouseControl(mapGridPane, simulation.getMap(), selectedAnimal);
    }
    public void update() {
        updateSimulationStatsView();
        updateAnimalStatsView();
        Platform.runLater(() -> {
            MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal, center, this);
        });
    }

    public void onClickShowPreferredCellsBtn(ActionEvent actionEvent) {
    }

    public void onClickShowDominantBtn(ActionEvent actionEvent) {
    }

    private void onSimulationStop() {
        stopBtn.setText("Start");
    }

    private void onSimulationStart() {
        stopBtn.setText("Stop");
    }

    public void setSelectedAnimal(Animal selectedAnimal) {
        this.selectedAnimal = selectedAnimal;
        MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal, center, this);
        updateAnimalStatsView();
    }

    public void onClickStopBtn(ActionEvent actionEvent) {
        simulation.toggleIsRunning();
        if(simulation.getIsRunning()) {
            onSimulationStart();
        }else {
            onSimulationStop();
        }
    }

}
