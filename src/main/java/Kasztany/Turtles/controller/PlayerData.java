package Kasztany.Turtles.controller;

import Kasztany.Turtles.model.Turn;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PlayerData {
    @FXML
    private Text playerName;
    @FXML
    private HBox playerColor;

    public void bind(Turn turn) {
        playerName.textProperty().bind(turn.playerNameProperty());
        playerColor.setStyle("-fx-background-color: #" + turn.getPlayerColor() + ";");
        turn.playerColorProperty().addListener(change -> {
            playerColor.setStyle("-fx-background-color: #" + turn.getPlayerColor() + ";");
        });
    }
}
