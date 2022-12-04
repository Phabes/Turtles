package Kasztany.Turtles.gui;

import Kasztany.Turtles.settings.GlobalSettings;
import javafx.geometry.Pos;
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
    private GlobalSettings globalSettings = new GlobalSettings();

    public SettingsPanel() {
        numberOfPlayers.setPrefWidth(globalSettings.getTextFieldSize());
        boardSize.setPrefWidth(globalSettings.getTextFieldSize());
        numberOfPlayers.setPrefWidth(globalSettings.getTextFieldSize());
        Text numberOfPlayersText = new Text("Number Of Players");
        HBox numberOfPlayersBox = new HBox(numberOfPlayersText, numberOfPlayers);
        numberOfPlayersBox.setAlignment(Pos.CENTER);
        Text boardSizeText = new Text("Board Size");
        HBox boardSizeBox = new HBox(boardSizeText, boardSize);
        boardSizeBox.setAlignment(Pos.CENTER);
        HBox startBox = new HBox(startButton);
        startBox.setAlignment(Pos.CENTER);
        settings.getChildren().addAll(numberOfPlayersBox, boardSizeBox, startBox);
        settings.setSpacing(globalSettings.getOptionsSpace());
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
