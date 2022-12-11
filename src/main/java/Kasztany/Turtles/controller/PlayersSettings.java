package Kasztany.Turtles.controller;

import Kasztany.Turtles.gui.BoardPanel;
import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.parser.OptionsParser;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PlayersSettings {
    private int numberOfPlayers;
    private int boardSize;
    private final OptionsParser optionsParser = new OptionsParser();
    private final GlobalSettings globalSettings = new GlobalSettings();
    private final String[] colors = {"ff0000", "00ff00", "0000ff", "ffa500", "800080", "ffc0cb", "4cf03a", "3af0de", "5f41a6", "4f2c0f"};
    @FXML
    private VBox configuration;
    private final List<HBox> playersTextBoxes = new ArrayList<>();
    private final List<HBox> colorsBoxes = new ArrayList<>();
    private final List<TextField> playersNames = new ArrayList<>();
    private final HashMap<Integer, String> indexPlayers = new HashMap<>();

    @FXML
    public void receiveData(String numberOfPlayersStr, String boardSizeStr){
        numberOfPlayers = optionsParser.getInt(numberOfPlayersStr);
        boardSize = optionsParser.getInt(boardSizeStr);
        for (int i = 0; i < numberOfPlayers; i++) {
            Text playerText = new Text("Player " + i);
            HBox playerTextBox = new HBox(playerText);
            playerTextBox.setPrefWidth(globalSettings.getTextFieldSize());
            playersTextBoxes.add(playerTextBox);
            playerTextBox.setAlignment(Pos.CENTER);
            playerTextBox.setStyle("-fx-background-color: #454242;");
            TextField playerName = new TextField("Player " + i);
            playersNames.add(playerName);
            playerName.setPrefWidth(globalSettings.getTextFieldSize());
            HBox playerColorBox = new HBox();
            playerColorBox.setAlignment(Pos.CENTER);
            drawColors(playerColorBox, i);
            VBox colorsBox = new VBox(playerColorBox);
            colorsBox.setAlignment(Pos.CENTER);
            colorsBoxes.add(playerColorBox);
            HBox playerBox = new HBox(playerTextBox, playerName, colorsBox);
            playerBox.setAlignment(Pos.CENTER);
            playerBox.setSpacing(globalSettings.getOptionsSpace());
            configuration.getChildren().add(playerBox);
        }
    }

    private void drawColors(HBox playerColorBox, int index) {
        for (String color : colors) {
            VBox colorBox = new VBox();
            colorBox.setPrefSize(30, 30);
            if (indexPlayers.containsValue(color))
                colorBox.setStyle("-fx-background-color: #454242;");
            else {
                colorBox.setStyle("-fx-background-color: #" + color + ";");
                colorBox.setOnMouseClicked((e) -> {
                    playersTextBoxes.get(index).setStyle("-fx-background-color: #" + color + ";");
                    indexPlayers.put(index, color);
                    for (int i = 0; i < colorsBoxes.size(); i++) {
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
        for (int i = 0; i < playersNames.size(); i++) {
            for (int j = i + 1; j < playersNames.size(); j++) {
                if (Objects.equals(playersNames.get(i).getText(), playersNames.get(j).getText()))
                    return false;
            }
        }
        return indexPlayers.size() == numberOfPlayers;
    }

    public HashMap<Integer, List<String>> getPlayers() {
        HashMap<Integer, List<String>> players = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.put(i, List.of(playersNames.get(i).getText(), indexPlayers.get(i)));
        }
        return players;
    }

    @FXML
    public void handleStartClick(ActionEvent event) {
        if(checkStart()){
            Board board = new Board(getPlayers(), boardSize);
            BoardPanel boardPanel = new BoardPanel(board);
            Scene boardScene = new Scene(boardPanel.getBoard());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Board");
            stage.setScene(boardScene);
            globalSettings.setScreenInTheMiddle(stage);
        }
    }
}
