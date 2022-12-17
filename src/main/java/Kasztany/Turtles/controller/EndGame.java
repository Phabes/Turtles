package Kasztany.Turtles.controller;

import Kasztany.Turtles.model.Turtle;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndGame {
    @FXML
    private Text winnerName;
    @FXML
    private Text winnerPoints;

    public void reveiceData(Turtle winner) {
        winnerName.setText("Winner: " + winner.getName());
        winnerPoints.setText("Points: " + winner.getPoints());
    }
}
