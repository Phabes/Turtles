package Kasztany.Turtles.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.*;

public class PlayersConfiguration {
    private final int numberOfPlayers;
    private final VBox configuration = new VBox();
    private final Button startButton = new Button("Start");
    private final String[] colors = {"ff0000", "00ff00", "0000ff", "ffc0cb", "ffa500", "800080"};
    private final List<HBox> playersTextBoxes = new ArrayList<>();
    private final List<HBox> colorsBoxes = new ArrayList<>();
    private final List<TextField> playersNames = new ArrayList<>();
    private final HashMap<Integer, String> indexPlayers = new HashMap<>();

    public PlayersConfiguration(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        int optionsSpace = 20;
        int textFieldSize = 100;
        for (int i = 0; i < numberOfPlayers; i++) {
            Text playerText = new Text("Player " + i);
            HBox playerTextBox = new HBox(playerText);
            playersTextBoxes.add(playerTextBox);
            playerTextBox.setAlignment(Pos.CENTER);
            playerTextBox.setStyle("-fx-background-color: #454242;");
            TextField playerName = new TextField("Player " + i);
            playersNames.add(playerName);
            playerName.setPrefWidth(textFieldSize);
            HBox playerColorBox = new HBox();
            playerColorBox.setPrefHeight(20);
            drawColors(playerColorBox, i);
            colorsBoxes.add(playerColorBox);
            HBox playerBox = new HBox(playerTextBox, playerName, playerColorBox);
            playerBox.setAlignment(Pos.CENTER);
            playerBox.setSpacing(optionsSpace);
            configuration.getChildren().add(playerBox);
        }
        configuration.getChildren().add(startButton);
        configuration.setSpacing(optionsSpace);
    }

    private void drawColors(HBox playerColorBox, int index) {
        for (String color : colors) {
            VBox colorBox = new VBox();
            colorBox.setPrefSize(20, 20);
            if (indexPlayers.containsValue(color))
                colorBox.setStyle("-fx-background-color: #454242;");
            else {
                colorBox.setStyle("-fx-background-color: #" + color + ";");
                colorBox.setOnMouseClicked((e) -> {
                    playersTextBoxes.get(index).setStyle("-fx-background-color: #" + color + ";");
                    indexPlayers.put(index, color);
                    for(int i = 0; i < colorsBoxes.size(); i++){
                        HBox box = colorsBoxes.get(i);
                        box.getChildren().clear();
                        drawColors(box, i);
                    }
                });
            }
            playerColorBox.getChildren().add(colorBox);
        }
    }

    public boolean checkStart() {
        for(int i = 0; i < playersNames.size(); i++){
            for(int j = i + 1; j < playersNames.size(); j++){
                if(Objects.equals(playersNames.get(i).getText(), playersNames.get(j).getText()))
                    return false;
            }
        }
        return indexPlayers.size() == numberOfPlayers;
    }

    public VBox getConfiguration() {
        return configuration;
    }

    public Button getStartButton() {
        return startButton;
    }

    public HashMap<String, String> getPlayers() {
        HashMap<String, String> players = new HashMap<>();
        for(int i = 0; i < numberOfPlayers; i++){
            players.put(playersNames.get(i).getText(), indexPlayers.get(i));
        }
        return players;
    }
}
