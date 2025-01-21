package darwinWorld.model.simulation;

import darwinWorld.controllers.SimulationController;
import darwinWorld.model.simulation.parameters.SimulationParameters;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class SimulationEngine {
    List<Simulation> simulations = new ArrayList<>();
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

        return simulation;
    }

    public void addSimulation(SimulationParameters sp) {

        try {
            Simulation simulation = newSimulation(sp);
            simulations.add(simulation);
            executorService.submit(simulation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void endSimulations() throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }

}
