package darwinWorld.model.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class SimulationEngine {
    List<Simulation> simulations = new ArrayList<>();
    private final ScheduledExecutorService executorService = newScheduledThreadPool(1);

    public void addSimulation(Simulation simulation) {
        simulations.add(simulation);
        executorService.submit(simulation);
    }

    public void endSimulations() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

}
