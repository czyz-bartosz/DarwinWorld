package darwinWorld.controllers;

import darwinWorld.model.map.Boundary;
import darwinWorld.model.map.Vector2d;
import darwinWorld.model.map.WorldMap;
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
    @FXML
    public ScrollPane gridScrollPane;
    ObservableList<Animal> items = FXCollections.observableArrayList();
    Simulation simulation;
    Animal selectedAnimal = null;
    Vector2d center = new Vector2d(0, 0);

    private double lastMouseX; // Ostatnia pozycja myszy w osi X
    private double lastMouseY; // Ostatnia pozycja myszy w osi Y;

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

            // Aktualizacja środka
            center = new Vector2d(center.getX() - deltaCellsX, center.getY() + deltaCellsY);

            // Zapamiętaj ostatnią pozycję myszy
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();

            // Odśwież siatkę
            MapGridPaneUtils.generateGrid(mapGridPane, worldMap, selectedAnimal, center);
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
            MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal, center);
        });
    }

    @FXML
    public void initialize() {
//        simulation = new Simulation(this);
//        new Thread(simulation).start();
//        simulation.run();
        animalChoiceBox.setItems(items);
    }

    private void updateCenterAndGrid(GridPane mapGridPane, WorldMap worldMap, Animal selectedAnimal, ScrollPane scrollPane) {
        double hValue = scrollPane.getHvalue();
        double vValue = scrollPane.getVvalue();

        // Zakładamy, że mapa ma wymiary i `DEFAULT_WIDTH` i `DEFAULT_HEIGHT` to rozmiar siatki w komórkach
        int totalWidth = worldMap.getEarth().getBoundary().upperRight().getX();
        int totalHeight = worldMap.getEarth().getBoundary().upperRight().getY();

        int visibleWidth = MapGridPaneUtils.DEFAULT_WIDTH;
        int visibleHeight = MapGridPaneUtils.DEFAULT_HEIGHT;

        int centerX = (int) (hValue * (totalWidth - visibleWidth) + visibleWidth / 2);
        int centerY = (int) (vValue * (totalHeight - visibleHeight) + visibleHeight / 2);

        center = new Vector2d(centerX, centerY);

        Boundary visibleBounds = new Boundary(
                new Vector2d(centerX - visibleWidth / 2, centerY - visibleHeight / 2),
                new Vector2d(centerX + visibleWidth / 2, centerY + visibleHeight / 2)
        );

        MapGridPaneUtils.updateVisibleGrid(mapGridPane, worldMap, selectedAnimal, visibleBounds);
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
        MapGridPaneUtils.generateGrid(mapGridPane, simulation.getMap(), selectedAnimal, center);
    }


}
