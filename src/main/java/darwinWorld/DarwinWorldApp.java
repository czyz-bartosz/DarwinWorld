package darwinWorld;

import darwinWorld.controllers.SimulationController;
import darwinWorld.controllers.ParametersSelectorController;
import darwinWorld.model.simulation.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DarwinWorldApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/parametersSelectorView.fxml"));
        VBox viewRoot = fxmlLoader.load();
        configureStage(primaryStage, viewRoot);
        ParametersSelectorController controller = fxmlLoader.getController();

        primaryStage.sizeToScene();
        primaryStage.show();

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulationView.fxml"));
//        VBox viewRoot = fxmlLoader.load();
//        configureStage(primaryStage, viewRoot);
//        SimulationController controller = fxmlLoader.getController();
//
//        primaryStage.sizeToScene();
//        primaryStage.show();


    }


    private void configureStage(Stage primaryStage, VBox viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
    }

    private void newSimulation() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulationView.fxml"));
        VBox viewRoot = fxmlLoader.load();
        Stage stage = new Stage();
        configureStage(stage, viewRoot);

        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(DarwinWorldApp.class, args);
    }

}