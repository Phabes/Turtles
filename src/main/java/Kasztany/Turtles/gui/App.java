package Kasztany.Turtles.gui;

import Kasztany.Turtles.parser.OptionsParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class App extends Application {
    private final OptionsParser optionsParser = new OptionsParser();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        SettingsPanel settings = new SettingsPanel();
        settings.getStartButton().setOnAction((e) -> {
            int numberOfPlayers = optionsParser.getInt(settings.getNumberOfPlayers());
            int boardSize = optionsParser.getInt(settings.getBoardSize());
            System.out.println("Players: " + numberOfPlayers + ", Size: " + boardSize);
            PlayersConfiguration playersConfiguration = new PlayersConfiguration(numberOfPlayers);
            playersConfiguration.getStartButton().setOnAction((e2) -> {
                if(playersConfiguration.checkStart()){
                    HashMap<Integer, List<String>> players = playersConfiguration.getPlayers();
                    for (int key : players.keySet() ) {
                        System.out.println(key + " " + players.get(key));
                    }
                    System.out.println("START");
                }
            });
            Scene playersConfigurationScene = new Scene(playersConfiguration.getConfiguration());
            primaryStage.setTitle("Players Configuration");
            primaryStage.setScene(playersConfigurationScene);
        });
        Scene settingsScene = new Scene(settings.getSettings());
        primaryStage.setTitle("Settings");
        primaryStage.setScene(settingsScene);
        primaryStage.show();
        this.setScreenInTheMiddle(primaryStage);
    }

    private void setScreenInTheMiddle(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
