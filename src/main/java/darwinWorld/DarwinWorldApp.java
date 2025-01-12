package darwinWorld;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DarwinWorldApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        configureStage(primaryStage);
        primaryStage.show();
    }

    private void configureStage(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulationView.fxml"));
        VBox viewRoot = fxmlLoader.load();
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
    }
}