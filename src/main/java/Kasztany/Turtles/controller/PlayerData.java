package Kasztany.Turtles.controller;

import Kasztany.Turtles.model.Player;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PlayerData {
    @FXML
    private Text playerName;

    public void reveiceData(Player player) {
        playerName.setText("Current player: " + player.getName());
    }
}
