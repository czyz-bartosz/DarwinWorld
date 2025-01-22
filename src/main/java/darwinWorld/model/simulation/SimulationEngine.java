package darwinWorld.model.simulation;

import darwinWorld.controllers.SimulationController;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;



public class SimulationEngine {
    HashMap<Simulation, Future<?>> futures = new HashMap<>();
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    private void configureStage(Stage primaryStage, VBox viewRoot, String title) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
    }

    private Simulation newSimulation(SimulationParameters sp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulationView.fxml"));
        VBox viewRoot = fxmlLoader.load();
        Stage stage = new Stage();
        configureStage(stage, viewRoot,"Simulation");
        SimulationController controller = fxmlLoader.getController();

        Simulation simulation = new Simulation(controller, sp);
        controller.setSimulation(simulation);

        stage.sizeToScene();
        stage.show();

        stage.setOnCloseRequest(event -> {
            endSimulation(simulation);
            stage.close();
        });

        return simulation;
    }

    public void addSimulation(SimulationParameters sp) {

        try {
            Simulation simulation = newSimulation(sp);
            Future<?> future = executorService.submit(simulation);
            futures.put(simulation, future);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void endSimulation(Simulation simulation) {
        if(futures.containsKey(simulation)) {
            futures.get(simulation).cancel(true);
            futures.remove(simulation);
        }
    }

    public void endSimulations() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

}
