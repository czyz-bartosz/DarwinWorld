package darwinWorld;

import darwinWorld.model.simulation.Simulation;
import darwinWorld.model.simulation.parameters.SavedSimulationManager;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import javafx.application.Application;

import java.lang.reflect.RecordComponent;

public class Main {
    public static void main(String[] args) {
        SavedSimulationManager savedSimulationManager = new SavedSimulationManager("src/main/resources/savedSimulations.properties");
        Simulation simulation = new Simulation(savedSimulationManager.load().get("lmao"));
        simulation.run();
    }

}
