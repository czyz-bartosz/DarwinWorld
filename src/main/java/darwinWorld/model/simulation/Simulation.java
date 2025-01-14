package darwinWorld.model.simulation;

import darwinWorld.controllers.SimulationController;
import darwinWorld.model.map.WorldMap;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import darwinWorld.model.simulation.parameters.SimulationParametersBuilder;

import java.util.UUID;

public class Simulation implements Runnable {
    private final UUID uuid = UUID.randomUUID();
    private final SimulationParameters sp;
    private final WorldMap map;
    private final SimulationStats stats = new SimulationStats(this);
    private SimulationController controller;

    public Simulation() {
        SimulationParametersBuilder sb = new SimulationParametersBuilder();
        sp = sb.build();
        map = new WorldMap(this);
    }

    public SimulationParameters getParameters() {
        return sp;
    }

    public SimulationStats getStats() {
        return stats;
    }

    public Simulation(SimulationController simulationController) {
        this();
        controller = simulationController;
    }

    public WorldMap getMap() {
        return map;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void run() {
        for(int i = 0; i < 10; i++) {
            System.out.println(map.toString());
            if(controller != null) {
                controller.update();
            }
            step();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void step(){
        map.step();
    }

}
