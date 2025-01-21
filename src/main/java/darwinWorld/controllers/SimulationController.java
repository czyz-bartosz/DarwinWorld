package darwinWorld.controllers;

import darwinWorld.model.map.WorldMapUtils;
import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.worldElements.animals.Animal;
import darwinWorld.views.utils.MapGridPaneUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    public ChoiceBox<Animal> animalChoiceBox;
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
    ObservableList<Animal> items = FXCollections.observableArrayList();
    Simulation simulation;
    Animal selectedAnimal = null;

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
    }
    public void update() {
        updateSimulationStatsView();
        updateAnimalStatsView();
        Platform.runLater(() -> {
            MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal);
        });
    }

    @FXML
    public void initialize() {
//        simulation = new Simulation(this);
//        new Thread(simulation).start();
//        simulation.run();
        animalChoiceBox.setItems(items);
    }

    public void onClickShowPreferredCellsBtn(ActionEvent actionEvent) {
    }

    public void onClickShowDominantBtn(ActionEvent actionEvent) {
    }

    private void onSimulationStop() {
        stopBtn.setText("Start");
        animalChoiceBox.setDisable(false);
        items.clear();
        items.addAll(WorldMapUtils.getCollectionOfAnimals(simulation.getMap()));
    }

    private void onSimulationStart() {
        stopBtn.setText("Stop");
        animalChoiceBox.setDisable(true);
    }

    public void onClickStopBtn(ActionEvent actionEvent) {
        simulation.toggleIsRunning();
        if(simulation.getIsRunning()) {
            onSimulationStart();
        }else {
            onSimulationStop();
        }
    }

    public void onChoiceAnimalChoiceBox(ActionEvent actionEvent) {
        selectedAnimal = animalChoiceBox.getSelectionModel().getSelectedItem();
        updateAnimalStatsView();
        MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal);
    }
}
