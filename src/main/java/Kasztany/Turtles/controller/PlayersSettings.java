package Kasztany.Turtles.controller;


import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.parser.OptionsParser;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class PlayersSettings {
    private final OptionsParser optionsParser;
    private final Board board;
    private int numberOfPlayers;
    private File map;
    private final String[] colors = {"ff0000", "00ff00", "0000ff", "ffa500", "ffc0cb", "800080", "f2dc46", "3af0de", "5f41a6", "4f2c0f"};
    @FXML
    private VBox configuration;
    @FXML
    private Button nextStep;
    private final List<HBox> playersTextBoxes = new ArrayList<>();
    private final List<HBox> colorsBoxes = new ArrayList<>();
    private final List<TextField> playersNames = new ArrayList<>();
    private final HashMap<Integer, String> indexPlayers = new HashMap<>();
    private int index = 0;


    public PlayersSettings(OptionsParser optionsParser, Board board) {
        this.optionsParser = optionsParser;
        this.board = board;
    }

    @FXML
    public void receiveData(String numberOfPlayersStr, File map) {
        numberOfPlayers = optionsParser.getInt(numberOfPlayersStr);
        this.map = map;
        if (numberOfPlayers == 1)
            nextStep.setText("START");
        shuffleColors();
        drawPlayerSettings();
    }

    private void shuffleColors() {
        List<String> colorsList = Arrays.asList(colors);
        Collections.shuffle(colorsList);
        colorsList.toArray(colors);
    }

    private void drawPlayerSettings() {
        configuration.getChildren().clear();
        Text playerText = new Text("Player " + index);
        HBox playerTextBox = new HBox(playerText);
        playerTextBox.setPrefWidth(GlobalSettings.TEXT_FIELD_SIZE);
        playersTextBoxes.add(playerTextBox);
        playerTextBox.setAlignment(Pos.CENTER);
        playerTextBox.setStyle("-fx-background-color: #454242;");
        TextField playerName = new TextField("Player " + index);
        playersNames.add(playerName);
        playerName.setPrefWidth(GlobalSettings.TEXT_FIELD_SIZE);
        HBox playerColorBox = new HBox();
        playerColorBox.setAlignment(Pos.CENTER);
        drawColors(playerColorBox);
        VBox colorsBox = new VBox(playerColorBox);
        colorsBox.setAlignment(Pos.CENTER);
        colorsBoxes.add(playerColorBox);
        HBox playerBox = new HBox(playerTextBox, playerName, colorsBox);
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setSpacing(GlobalSettings.OPTIONS_SPACE);
        configuration.getChildren().add(playerBox);
    }

    private void drawColors(HBox playerColorBox) {
        for (String color : colors) {
            VBox colorBox = new VBox();
            colorBox.setPrefSize(GlobalSettings.COLOR_SIZE, GlobalSettings.COLOR_SIZE);
            if (indexPlayers.containsValue(color))
                colorBox.setStyle("-fx-background-color: #454242;");
            else {
                colorBox.setStyle("-fx-background-color: #" + color + ";");
                colorBox.setOnMouseClicked((e) -> {
                    playersTextBoxes.get(index).setStyle("-fx-background-color: #" + color + ";");
                    indexPlayers.put(index, color);
                    nextStep.setDisable(false);
                    for (HBox box : colorsBoxes) {
                        box.getChildren().clear();
                        drawColors(box);
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
    public void handleStartClick(ActionEvent event) throws IOException {
        if (checkStart()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BoardPane.fxml"));
            Parent root = loader.load();

            BoardController boardController = loader.getController();
            board.addFields(map);
            board.addTurtlesFromHashMap(getPlayers());
            boardController.receiveData(board);

            Scene boardScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Board");
            stage.setScene(boardScene);
            GlobalSettings.setScreenInTheMiddle(stage);
        } else {
            index++;
            if (index == numberOfPlayers - 1)
                nextStep.setText("START");
            shuffleColors();
            drawPlayerSettings();
            nextStep.setDisable(true);
        }
    }
}
