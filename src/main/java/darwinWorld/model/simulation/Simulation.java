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
    private volatile boolean isRunning = true;

    public Simulation() {
        SimulationParametersBuilder sb = new SimulationParametersBuilder();
        sp = sb.build();
        map = new WorldMap(this);
    }

    public boolean getIsRunning() {
        return isRunning;
    }

    public void toggleIsRunning() {
        synchronized (this) {
            isRunning = !isRunning;
        }
    }

    public SimulationParameters getParameters() {
        return sp;
    }

    public SimulationStats getStats() {
        return stats;
    }

    public Simulation(SimulationController simulationController, SimulationParameters sp) {
        this.sp = sp;
        map = new WorldMap(this);
        controller = simulationController;
    }

    public WorldMap getMap() {
        return map;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void run() {
        while (true){
            if(isRunning){
//                System.out.println(map.toString());
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
    }

    public void step(){
        map.step();
    }

}
