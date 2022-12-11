package Kasztany.Turtles.gui;

import Kasztany.Turtles.settings.GlobalSettings;
import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends Application {
    private final GlobalSettings globalSettings = new GlobalSettings();

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(windowEvent -> closeWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameSettings.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
        globalSettings.setScreenInTheMiddle(primaryStage);
    }

    private void closeWindow(){
        Platform.exit();
        System.exit(0);
    }
}
