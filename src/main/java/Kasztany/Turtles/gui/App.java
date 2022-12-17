package Kasztany.Turtles.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Kasztany.Turtles.TurtlesApplication;
import Kasztany.Turtles.FXMLLoaderProvider;
import Kasztany.Turtles.settings.GlobalSettings;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class App extends Application {

    private ConfigurableApplicationContext applicationContext;
    private final GlobalSettings globalSettings = new GlobalSettings();

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(windowEvent -> closeWindow());
        FXMLLoaderProvider loaderProvider = applicationContext.getBean(FXMLLoaderProvider.class);
        FXMLLoader loader = loaderProvider.getLoader(getClass().getResource("/view/GameSettings.fxml"));
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

    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(TurtlesApplication.class).run();
    }

}
