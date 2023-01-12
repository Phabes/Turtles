package Kasztany.Turtles.controller;

import Kasztany.Turtles.model.Turtle;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class EndGame {
    @FXML
    private VBox rankingBox;

    public void reveiceData(ArrayList<Turtle> ranking) {
        Text playerName = new Text("Name");
        HBox playerNameBox = new HBox(playerName);
        playerNameBox.getStyleClass().add("rankingComponent");
        Text playerPoints = new Text("Points");
        HBox playerPointsBox = new HBox(playerPoints);
        playerPointsBox.getStyleClass().add("rankingComponent");
        Text playerColor = new Text("Points");
        HBox playerColorBox = new HBox(playerColor);
        playerColorBox.getStyleClass().add("rankingComponent");
        HBox row = new HBox(playerNameBox, playerPointsBox, playerColorBox);
        rankingBox.getChildren().add(row);
        for(Turtle turtle: ranking){
            playerName = new Text(turtle.getName());
            playerNameBox = new HBox(playerName);
            playerNameBox.getStyleClass().add("rankingComponent");
            playerPoints = new Text(String.valueOf(turtle.getPoints()));
            playerPointsBox = new HBox(playerPoints);
            playerPointsBox.getStyleClass().add("rankingComponent");
            playerColorBox = new HBox();
            playerColorBox.getStyleClass().add("rankingComponent");
            playerColorBox.setStyle("-fx-background-color: #" + turtle.getColor() + ";");
            row = new HBox(playerNameBox, playerPointsBox, playerColorBox);
            rankingBox.getChildren().add(row);
        }
    }
}
