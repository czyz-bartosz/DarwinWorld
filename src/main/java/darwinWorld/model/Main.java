package darwinWorld.model;

import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;

public class Main {
    public static void main(String[] args) {
        SimulationEngine simulationEngine = new SimulationEngine();

        SimulationParameters sp = new SimulationParametersBuilder().build();
        Simulation simulation = new Simulation(sp);

        simulationEngine.addSimulation(simulation);
//
//        try {
//            simulationEngine.endSimulations();
//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }
    }
}
