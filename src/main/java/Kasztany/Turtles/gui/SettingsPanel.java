package Kasztany.Turtles.gui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SettingsPanel {
    private final VBox settings = new VBox();
    private final TextField numberOfPlayers = new TextField("4");
    private final TextField boardSize = new TextField("10");
    private final Button startButton = new Button("Start");

    public SettingsPanel() {
        int optionsSpace = 20;
        int textFieldSize = 100;
        numberOfPlayers.setPrefWidth(textFieldSize);
        boardSize.setPrefWidth(textFieldSize);
        numberOfPlayers.setPrefWidth(textFieldSize);
        Text numberOfPlayersText = new Text("Number Of Players");
        HBox numberOfPlayersBox = new HBox(numberOfPlayersText, numberOfPlayers);
        Text boardSizeText = new Text("Board Size");
        HBox boardSizeBox = new HBox(boardSizeText, boardSize);
        HBox startBox = new HBox(startButton);
        settings.getChildren().addAll(numberOfPlayersBox, boardSizeBox, startBox);
        settings.setSpacing(optionsSpace);
    }

    public VBox getSettings() {
        return settings;
    }

    public Button getStartButton() {
        return startButton;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers.getText();
    }

    public String getBoardSize() {
        return boardSize.getText();
    }
}
