package darwinWorld.controllers;

import darwinWorld.model.map.Vector2d;
import darwinWorld.model.map.WorldMap;
import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.simulation.SimulationStats;
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
        SimulationStats stats = simulation.getStats();
        String numberOfAnimals = Integer.toString(stats.getNumberOfAnimals());
        String numberOfGrass = Integer.toString(stats.getNumberOfGrass());
        String numberOfFreeCells = Integer.toString(stats.getNumberOfFreeCells());
        String theMostPopularGenotype = stats.getTheMostPopularGenotype().toString();
        String avgEnergy = Double.toString(stats.getAvgEnergy());
        String avgLifeSpan = Double.toString(stats.getAvgLifeSpan());
        String avgNumberOfChildren = Double.toString(stats.getAvgNumberOfChildrenPerAnimal());

        Platform.runLater(() -> {
            numberOfAnimalsField.textProperty().setValue(numberOfAnimals);
            numberOfGrassField.textProperty().setValue(numberOfGrass);
            numberOfFreeCellsField.textProperty().setValue(numberOfFreeCells);
            theMostPopularGenotypeField.textProperty().setValue(theMostPopularGenotype);
            avgEnergyField.textProperty().setValue(avgEnergy);
            avgLifeSpanField.textProperty().setValue(avgLifeSpan);
            avgNumberOfChildrenField.textProperty().setValue(avgNumberOfChildren);
        });
    }

    private void updateAnimalStatsView() {
        if (selectedAnimal != null) {
            String genotype = selectedAnimal.getGenes().toString();
            String currentIndexOfGenotype = Integer.toString(selectedAnimal.getGeneCurrentIndex());
            String energy = Double.toString(selectedAnimal.getEnergy());
            String eatenGrass = Integer.toString(selectedAnimal.getStats().getNumberOfEatenGrass());
            String numberOfChildren = Integer.toString(selectedAnimal.getStats().getNumberOfChildren());
            String numberOfDescendants = Integer.toString(selectedAnimal.getStats().getNumberOfDescendants());
            String daysOfLife = Integer.toString(selectedAnimal.getStats().getNumberOfDaysOfLife());
            String dayOfDeath = selectedAnimal.getStats().getDayOfDeath().toString();

            Platform.runLater(() -> {
                genotypeField.textProperty().setValue(genotype);
                currentIndexOfGenotypeField.textProperty().setValue(currentIndexOfGenotype);
                energyField.textProperty().setValue(energy);
                eatenGrassField.textProperty().setValue(eatenGrass);
                numberOfChildrenField.textProperty().setValue(numberOfChildren);
                numberOfDescendantsField.textProperty().setValue(numberOfDescendants);
                daysOfLifeField.textProperty().setValue(daysOfLife);
                dayOfDeathField.textProperty().setValue(dayOfDeath);
            });
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
        updateAnimalStatsView();
        Platform.runLater(()->{
            MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal, center, this);
        });
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
