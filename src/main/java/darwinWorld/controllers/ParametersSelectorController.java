package darwinWorld.controllers;

import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;
import darwinWorld.model.simulation.parameters.SimulationParametersValidator;
import darwinWorld.utils.InvalidArgumentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.Objects;

public class ParametersSelectorController {

    @FXML
    private CheckBox crazyMutationsCheckBox;

    @FXML
    private Spinner<Integer> energyUsedToReproduceSpinner;

    @FXML
    private Spinner<Integer> equatorSpanSpinner;

    @FXML
    private Spinner<Integer> genesLengthSpinner;

    @FXML
    private Spinner<Integer> grassEnergySpinner;

    @FXML
    private Spinner<Integer> grassGrowthPerDaySpinner;

    @FXML
    private Spinner<Integer> initialAnimalAmountSpinner;

    @FXML
    private Spinner<Integer> initialAnimalEnergySpinner;

    @FXML
    private Spinner<Integer> initialGrassAmountSpinner;

    @FXML
    private Spinner<Integer> mapHeightSpinner;

    @FXML
    private Spinner<Integer> mapWidthSpinner;

    @FXML
    private Spinner<Integer> maxMutationsSpinner;

    @FXML
    private Spinner<Integer> minMutationsSpinner;

    @FXML
    private Spinner<Integer> minimalEnergyToReproduceSpinner;

    @FXML
    private CheckBox saveToCSVCheckBox;

    @FXML
    private Button startButton;

    @FXML
    private CheckBox withOwlBearCheckBox;

    @FXML
    private ComboBox<String> savedConfigurationsSelector;

    @FXML
    private TextField saveConfigurationName;

    public SpinnerValueFactory.IntegerSpinnerValueFactory createSpinner(int min, int max, int init) {
        return createSpinner(min, max, init, 1);
    }

    public SpinnerValueFactory.IntegerSpinnerValueFactory createSpinner(int min, int max, int init, int step) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, init, step);
    }
    private void initializeSpinner(Spinner<Integer> spinner, int min, int max, int init) {
        spinner.setValueFactory(createSpinner(min, max, init));
    }
    private void initializeSpinner(Spinner<Integer> spinner, int min, int max, int init, int step) {
        spinner.setValueFactory(createSpinner(min, max, init, step));
    }
    @FXML
    public void initialize() {
        SimulationParameters sp = new SimulationParametersBuilder().build();
        setValues(sp);

        savedConfigurationsSelector.getItems().add("Default");
        savedConfigurationsSelector.getSelectionModel().select("Default");
    }
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message , ButtonType.OK);
        alert.showAndWait();
    }

    public void setValues(SimulationParameters sp) {
        initializeSpinner(mapHeightSpinner, 2, 100, sp.mapHeight(), 2);
        initializeSpinner(mapWidthSpinner, 1, 100, sp.mapWidth());
        initializeSpinner(equatorSpanSpinner, 0, 100, sp.equatorSpan());
        initializeSpinner(energyUsedToReproduceSpinner, 0, 100, sp.energyUsedToReproduce());
        initializeSpinner(minimalEnergyToReproduceSpinner, 0, 100, sp.minimalEnergyToReproduce());
        initializeSpinner(minMutationsSpinner, 0, 100, sp.minMutations());
        initializeSpinner(maxMutationsSpinner, 0, 100, sp.maxMutations());
        initializeSpinner(genesLengthSpinner, 0, 100, sp.genesLength());
        initializeSpinner(grassEnergySpinner, 0, 100, sp.grassEnergy());
        initializeSpinner(grassGrowthPerDaySpinner, 0, 100, sp.grassGrowthPerDay());
        initializeSpinner(initialGrassAmountSpinner, 0, 100, sp.initialGrassAmount());
        initializeSpinner(initialAnimalAmountSpinner, 0, 100, sp.initialAnimalAmount());
        initializeSpinner(initialAnimalEnergySpinner, 0, 100, sp.initialAnimalEnergy());
    }

    @FXML
    void startSimulation() {

        SimulationParametersBuilder sb = new SimulationParametersBuilder();

        sb.setMapHeight(mapHeightSpinner.getValue());
        sb.setMapWidth(mapWidthSpinner.getValue());
        sb.setEquatorSpan(equatorSpanSpinner.getValue());
        sb.setEnergyUsedToReproduce(energyUsedToReproduceSpinner.getValue());
        sb.setMinimalEnergyToReproduce(minimalEnergyToReproduceSpinner.getValue());
        sb.setMinMutations(minMutationsSpinner.getValue());
        sb.setMaxMutations(maxMutationsSpinner.getValue());
        sb.setGenesLength(genesLengthSpinner.getValue());
        sb.setGrassEnergy(grassEnergySpinner.getValue());
        sb.setInitialGrassAmount(initialGrassAmountSpinner.getValue());
        sb.setGrassGrowthPerDay(grassGrowthPerDaySpinner.getValue());
        sb.setInitialAnimalAmount(initialAnimalAmountSpinner.getValue());
        sb.setInitialAnimalEnergy(initialAnimalEnergySpinner.getValue());

        sb.setWithOwlBear(withOwlBearCheckBox.isSelected());
        sb.setSaveToCSV(saveToCSVCheckBox.isSelected());
        sb.setCrazyMutation(crazyMutationsCheckBox.isSelected());

        SimulationParameters sp = sb.build();

        try {
            SimulationParametersValidator.validate(sp);
        } catch (InvalidArgumentException e) {
            showError(e.getMessage());
            return;
        }

        System.out.println("GITARA");
    }

    public void saveConfiguration() {
        String name = saveConfigurationName.getText();
        if(Objects.equals(name, "")){
            showError("Configuration name cannot be empty");
            return;
        }
        if(savedConfigurationsSelector.getItems().contains(name)){
            showError("Configuration name already exists");
            return;
        }
        savedConfigurationsSelector.getItems().add(name);
        savedConfigurationsSelector.getSelectionModel().select("Default");
    }
}
