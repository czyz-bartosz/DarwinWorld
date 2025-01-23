package darwinWorld.controllers;

import darwinWorld.model.simulation.SimulationEngine;
import darwinWorld.model.simulation.parameters.SavedSimulationManager;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;
import darwinWorld.model.simulation.parameters.SimulationParametersValidator;
import darwinWorld.utils.InvalidArgumentException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;
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
    private CheckBox withOwlBearCheckBox;

    @FXML
    private ComboBox<String> savedConfigurationsSelector;

    @FXML
    private TextField saveConfigurationName;

    @FXML
    private Button deleteConfigurationButton;

    SimulationEngine simulationEngine;
    Map<String,SimulationParameters> savedConfigurations;
    SavedSimulationManager savedManager = new SavedSimulationManager("src/main/resources/savedSimulations.properties");

    public SpinnerValueFactory.IntegerSpinnerValueFactory createSpinner(int min, int max, int init) {
        return createSpinner(min, max, init, 1);
    }
    public SpinnerValueFactory.IntegerSpinnerValueFactory createSpinner(int min, int max, int init, int step) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, init, step);
    }
    private void initializeSpinner(Spinner<Integer> spinner, int min, int max, int init) {
        spinner.setValueFactory(createSpinner(min, max, init));
        spinner.setEditable(true);
        spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinner.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String text = spinner.getEditor().getText();
                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        if (value >= min && value <= max) {
                            spinner.getValueFactory().setValue(value);
                        } else {
                            spinner.getEditor().setText(String.valueOf(spinner.getValue()));
                        }
                    } catch (NumberFormatException e) {
                        spinner.getEditor().setText(String.valueOf(spinner.getValue()));
                    }
                }
            }
        });
    }


    public void setSimulationEngine(SimulationEngine simulationEngine) {
        this.simulationEngine = simulationEngine;
    }

    @FXML
    public void initialize() {

        SimulationParameters sp = new SimulationParametersBuilder().build();
        setValues(sp);
        deleteConfigurationButton.setDisable(true);
        savedConfigurationsSelector.getItems().add("Default");
        savedConfigurationsSelector.getSelectionModel().select("Default");

        savedConfigurations = savedManager.load();
        for(String name: savedConfigurations.keySet()) {
            savedConfigurationsSelector.getItems().add(name);
        }
    }
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message , ButtonType.OK);
        alert.showAndWait();
    }

    public void setValues(SimulationParameters sp) {


        initializeSpinner(mapHeightSpinner, 2, 1000, sp.mapHeight());
        initializeSpinner(mapWidthSpinner, 1, 1000, sp.mapWidth());
        initializeSpinner(equatorSpanSpinner, 0, 1000, sp.equatorSpan());
        initializeSpinner(energyUsedToReproduceSpinner, 0, 1000, sp.energyUsedToReproduce());
        initializeSpinner(minimalEnergyToReproduceSpinner, 0, 1000, sp.minimalEnergyToReproduce());
        initializeSpinner(minMutationsSpinner, 0, 1000, sp.minMutations());
        initializeSpinner(maxMutationsSpinner, 0, 1000, sp.maxMutations());
        initializeSpinner(genesLengthSpinner, 0, 1000, sp.genesLength());
        initializeSpinner(grassEnergySpinner, 0, 1000, sp.grassEnergy());
        initializeSpinner(grassGrowthPerDaySpinner, 0, 1000, sp.grassGrowthPerDay());
        initializeSpinner(initialGrassAmountSpinner, 0, 1000, sp.initialGrassAmount());
        initializeSpinner(initialAnimalAmountSpinner, 0, 1000, sp.initialAnimalAmount());
        initializeSpinner(initialAnimalEnergySpinner, 0, 1000, sp.initialAnimalEnergy());

    }
    private SimulationParameters buildParameters() throws InvalidArgumentException {

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

        SimulationParametersValidator.validate(sp);

        return sp;
    }
    @FXML
    void startSimulation() {

        SimulationParameters sp;

        try {
            sp = buildParameters();
        } catch (InvalidArgumentException e) {
            showError(e.getMessage());
            return;
        }

        simulationEngine.addSimulation(sp);
    }

    public void saveConfiguration() {
        String name = saveConfigurationName.getText();
        String selected = savedConfigurationsSelector.getSelectionModel().getSelectedItem();

        SimulationParameters sp;
        try {
            sp = buildParameters();
        } catch (InvalidArgumentException e) {
            showError(e.getMessage());
            return;
        }

        if(Objects.equals(selected, name) && !Objects.equals(selected, "Default")){
            savedConfigurations.put(name,sp);
            savedManager.save(savedConfigurations);
            return;
        }

        if(Objects.equals(name, "")){
            showError("Configuration name cannot be empty");
            return;
        }
        if(savedConfigurationsSelector.getItems().contains(name)){
            showError("Configuration name already exists");
            return;
        }
        savedConfigurations.put(name,sp);
        savedManager.save(savedConfigurations);

        savedConfigurationsSelector.getItems().add(name);
        savedConfigurationsSelector.getSelectionModel().select(name);

    }
    @FXML
    public void deleteConfiguration() {
        String selected = savedConfigurationsSelector.getSelectionModel().getSelectedItem();
        savedConfigurationsSelector.getItems().remove(selected);
        savedConfigurations.remove(selected);
        savedManager.save(savedConfigurations);
    }

    public void selectionChanged() {
        String selected = savedConfigurationsSelector.getSelectionModel().getSelectedItem();
        deleteConfigurationButton.setDisable(Objects.equals(selected, "Default"));

        if(Objects.equals(selected, "Default")){
            setValues(new SimulationParametersBuilder().build());
            saveConfigurationName.setText("");
        }
        else {
            setValues(savedConfigurations.get(selected));
            saveConfigurationName.setText(selected);
        }
    }
}
