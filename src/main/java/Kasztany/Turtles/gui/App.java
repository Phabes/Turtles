package Kasztany.Turtles.gui;

import Kasztany.Turtles.TurtlesApplication;
import Kasztany.Turtles.settings.GlobalSettings;
import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@Controller
@ComponentScan("Kasztany.Turtles")
public class App extends Application {

    //private ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
    private ConfigurableApplicationContext applicationContext;
    private final GlobalSettings globalSettings = new GlobalSettings();

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(windowEvent -> closeWindow());
        FXMLLoader loader = applicationContext.getBean(FXMLLoader.class);
        loader.setControllerFactory(applicationContext::getBean);
        loader.setLocation(getClass().getResource("/view/GameSettings.fxml"));
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
    public void init(){
        this.applicationContext = springBootApplicationContext();
    }

    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
        String[] args = getParameters().getRaw().toArray(String[]::new);
        return builder.run(args);
    }
    
}
