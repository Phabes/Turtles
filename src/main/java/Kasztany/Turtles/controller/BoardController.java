package Kasztany.Turtles.controller;

import Kasztany.Turtles.gui.ImageBoxElement;
import Kasztany.Turtles.model.*;
import Kasztany.Turtles.model.cards.Card;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;


@Controller
public class BoardController {
    private Board board;
    private final ImageBoxElement fruitBoxElement = new ImageBoxElement("peach.png");
    private final ImageBoxElement finishBoxElement = new ImageBoxElement("finish.png");
    @FXML
    private VBox pane;
    @FXML
    private HBox playersBox;
    @FXML
    private HBox cardsBox;
    @FXML
    private GridPane boardGrid;
    @FXML
    private Button moveButton;
    @FXML
    private Text informationText;

    private final ArrayList<Field> possibleFields = new ArrayList<>();
    private final ArrayDeque<Turtle> choosedTurtles = new ArrayDeque<>();
    private Field choosedField = null;
    private Card choosedCard = null;
    private Field endField = null;

    public BoardController() throws FileNotFoundException {
    }

    @FXML
    public void initialize() {
        pane.setPrefSize(GlobalSettings.BOARD_WIDTH, GlobalSettings.BOARD_HEIGHT);
        playersBox.setPrefSize(GlobalSettings.GRID_WIDTH, GlobalSettings.BOARD_HEIGHT - GlobalSettings.GRID_HEIGHT - GlobalSettings.CARD_HEIGHT);
        playersBox.setAlignment(Pos.CENTER);
        cardsBox.setPrefSize(GlobalSettings.GRID_WIDTH, GlobalSettings.CARD_HEIGHT);
        cardsBox.setAlignment(Pos.CENTER);
        boardGrid.setPrefSize(GlobalSettings.GRID_WIDTH, GlobalSettings.GRID_HEIGHT);
        informationText.setText("Choose Card");
    }

    @FXML
    public void receiveData(Board receivedBoard) {
        board = receivedBoard;
        endField = board.getLastField();
        playersBox.setSpacing(GlobalSettings.GRID_WIDTH / board.getTurtles().size() - (GlobalSettings.BOARD_HEIGHT - GlobalSettings.GRID_HEIGHT));
        board.getTurtles().forEach(turtle -> {
            VBox turtleBox = new VBox(drawTurtle(GlobalSettings.HEADER_TURTLE_SIZE, turtle.getColor()));
            turtleBox.setOnMouseClicked((e) -> turtleClick(turtle));
            turtleBox.setOnMouseEntered((e) -> turtleBox.setCursor(Cursor.HAND));
            turtleBox.setId(turtle.getColor());
            turtleBox.setAlignment(Pos.CENTER);
            playersBox.getChildren().add(turtleBox);
        });
        highlightTurtles();

        Vector2d maxVector = board.getMaxVector();
        double prefSize = GlobalSettings.GRID_WIDTH / (maxVector.x() + 1);
        int fruitSize = (int) prefSize / 2;
        fruitBoxElement.setSize(fruitSize);

        for (int x = 0; x <= maxVector.x(); x++) {
            boardGrid.getColumnConstraints().add(new ColumnConstraints(50, prefSize, 200));
        }
        for (int y = 0; y <= maxVector.y(); y++) {
            boardGrid.getRowConstraints().add(new RowConstraints(50, prefSize, 200));
        }
        drawBoard();
        drawCards(board.getCurrentPlayer().getCards());
    }

    private void drawCards(ArrayList<Card> cards) {
        cardsBox.getChildren().clear();
        for (Card card : cards) {
            Text header = new Text(card.getHeader());
            HBox info = card.getInfo();
            info.getStyleClass().add("turtleInfo");
            card.getIcon().setSize(GlobalSettings.CARD_WIDTH / 2);
            VBox cardBox = new VBox(header, info, card.getIcon().getImage());
            cardBox.getStyleClass().add("card");
            cardBox.setMinSize(GlobalSettings.CARD_WIDTH, GlobalSettings.CARD_HEIGHT);
            cardBox.setOnMouseClicked((e) -> cardClick(card, cardBox));
            cardsBox.getChildren().add(cardBox);
        }
    }

    private void highlightTurtles() {
        playersBox.getChildren().forEach(turtleBox -> {
            if (turtleBox instanceof VBox){
                turtleBox.getStyleClass().clear();
                turtleBox.getStyleClass().add("simpleTurtle");
            }
            choosedTurtles.forEach(choosedTurtle -> {
                if (choosedTurtle.getColor().equals(turtleBox.getId())) {
                    turtleBox.getStyleClass().clear();
                    turtleBox.getStyleClass().add("choosedTurtle");
                }
            });
        });
    }

    private void clearBoard() {
        choosedField = null;
        for (Field possibleField : possibleFields) {
            Node boardField = boardGrid.lookup("#" + possibleField.getId());
            boardField.getStyleClass().clear();
            boardField.getStyleClass().add("regularField");
        }
        possibleFields.clear();
        highlightTurtles();
//        moveButton.setDisable(true);
    }

    private void highlightPossibleFields() {
        for (Field possibleField : possibleFields) {
            Node boardField = boardGrid.lookup("#" + possibleField.getId());
            boardField.getStyleClass().clear();
            boardField.getStyleClass().add("possibleField");

            boardField.setOnMouseClicked((e) -> fieldClick(possibleField, boardField));
        }
    }


    private HBox drawTurtle(double size, String color) {
        HBox shell = new HBox();
        shell.setMinSize(size / 2, size / 2);
        shell.setStyle("-fx-background-color: #" + color);
        HBox headBox = new HBox();
        headBox.setMinSize(size / 2, size / 2);
        headBox.setAlignment(Pos.BOTTOM_LEFT);

        HBox head = new HBox();
        head.setMaxSize(size / 2, size / 3);
        head.setMinSize(size / 2, size / 3);
        head.getStyleClass().add("turtleHead");
        head.setStyle(
                "-fx-padding:" + size / 20 + ";\n"
        );
        head.setAlignment(Pos.TOP_RIGHT);
        headBox.getChildren().add(head);
        HBox eye = new HBox();
        eye.setMaxSize(4, 4);
        eye.setMinSize(4, 4);
        eye.getStyleClass().add("turtleEye");
        head.getChildren().add(eye);

        HBox turtle = new HBox(shell, headBox);
        turtle.setPrefSize(size, size / 2);
        return turtle;
    }

    private void drawBoard() {
//        Platform.runLater(() -> {
        boardGrid.getChildren().clear();
        Vector2d maxVector = board.getMaxVector();
        double size = Math.max(GlobalSettings.GRID_WIDTH / (maxVector.x() + 1), GlobalSettings.MIN_TURTLE_SIZE);
        for (Field field : board.getNeighbourhood().getFields()) {
            GridPane fieldBox = new GridPane();
            fieldBox.setId(field.getId());
            fieldBox.setMinSize(GlobalSettings.MIN_TURTLE_SIZE, GlobalSettings.MIN_TURTLE_SIZE);
            fieldBox.getStyleClass().add("field");
            if (field.getBottomTurtle().isPresent()) {
                Turtle turtle = field.getBottomTurtle().get();
                ArrayList<Turtle> turtlesOnField = new ArrayList<>();
                turtlesOnField.add(turtle);
                while (turtle.getTurtleOnBack().isPresent()) {
                    turtle = turtle.getTurtleOnBack().get();
                    turtlesOnField.add(turtle);
                }
                drawTurtlesInField(size / (turtlesOnField.size() + 1), fieldBox, turtlesOnField);
            } else if (field.getFruit().isPresent()) {
                fieldBox.add(fruitBoxElement.getImage(), 0, 0);
            }
            if (field == endField) {
                fieldBox.getStyleClass().add("endField");
                finishBoxElement.setSize((int) fieldBox.getMinWidth());
                fieldBox.add(finishBoxElement.getImage(), 0, 0);
            }
            fieldBox.setAlignment(Pos.CENTER);
            boardGrid.add(fieldBox, field.getPosition().x(), maxVector.y() - field.getPosition().y());
        }
        boardGrid.setAlignment(Pos.CENTER);
//        });
    }

    private void setMoveButtonColor(String color) {
        moveButton.setStyle(
                "-fx-border-color: #" + color + ";"
        );
    }

    private void drawTurtlesInField(double size, GridPane field, ArrayList<Turtle> turtlesOnField) {
        for (int i = 0; i < turtlesOnField.size(); i++) {
            field.add(drawTurtle(size, turtlesOnField.get(i).getColor()), 0, turtlesOnField.size() - i - 1);
        }
    }

    private void showEndView(ActionEvent event) throws IOException {
        Turtle winner = board.findWinner();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EndGame.fxml"));
        Parent root = loader.load();
        EndGame endGame = loader.getController();
        endGame.reveiceData(winner);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("End Game");
        stage.setScene(scene);
        GlobalSettings.setScreenInTheMiddle(stage);
    }

    @FXML
    public void handleMoveClick(ActionEvent event) throws IOException {
        if (choosedCard.doTask(choosedTurtles, choosedField)) {
            board.burnCard(choosedCard);
            board.changeTurn();
            clearBoard();
            choosedCard = null;
            choosedTurtles.clear();
            drawCards(board.getCurrentPlayer().getCards());
            drawBoard();
            moveButton.setDisable(true);
            setMoveButtonColor("454242");
        } else {
            System.out.println("ERROR ");
        }
        if(board.isGameEnd())
            showEndView(event);

        informationText.setText("Choose Card");
    }

    @FXML
    public void handleShowClick(ActionEvent event) throws IOException {
        Player currentPlayer = board.getCurrentPlayer();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PlayerData.fxml"));
        Parent root = loader.load();
        PlayerData playerData = loader.getController();
        playerData.reveiceData(currentPlayer);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Player Data");
        stage.setScene(scene);
        stage.show();
        GlobalSettings.setScreenInTheMiddle(stage);
    }

    private void cardClick(Card card, VBox cardBox) {
        cardsBox.getChildren().forEach(child -> {
            child.setStyle("-fx-border-color: #db43e7");
        });
        cardBox.setStyle("-fx-border-color: red");

        choosedCard = card;
        choosedTurtles.clear();

        clearBoard();
        drawBoard();
        choosedTurtles.addAll(choosedCard.getTurtles());
        highlightTurtles();
        if (choosedCard.isFieldRequired()) {
            choosedTurtles.forEach(turtle -> {
                possibleFields.addAll(choosedCard.getFieldsToHighlight(turtle));
            });
            highlightPossibleFields();
        }
        moveButton.setDisable(!choosedCard.validate(choosedField, choosedTurtles));
    }

    private void turtleClick(Turtle turtle) {
        if (board.isGameEnd() || choosedCard == null || choosedCard.getNumberOfTurtlesRequired() == 0 || choosedCard.changeTurtleDisabled())
            return;

        if (choosedTurtles.contains(turtle))
            return;
        if (choosedTurtles.size() < choosedCard.getNumberOfTurtlesRequired())
            choosedTurtles.add(turtle);
        else if (choosedTurtles.size() == choosedCard.getNumberOfTurtlesRequired()) {
            choosedTurtles.poll();
            choosedTurtles.add(turtle);
        }
        clearBoard();
        highlightTurtles();
        if (choosedCard.isFieldRequired()) {
            choosedTurtles.forEach(choosedTurtle -> {
                possibleFields.addAll(choosedCard.getFieldsToHighlight(choosedTurtle));
            });
            highlightPossibleFields();
        }
        moveButton.setDisable(!choosedCard.validate(choosedField, choosedTurtles));
    }

    private void fieldClick(Field possibleField, Node boardField) {
        if (choosedField != null) {
            Node lastChoosenField = boardGrid.lookup("#" + choosedField.getId());
            lastChoosenField.getStyleClass().clear();
            lastChoosenField.getStyleClass().add("possibleField");
        }
        boardField.getStyleClass().clear();
        boardField.getStyleClass().add("choosedField");
        choosedField = possibleField;

        moveButton.setDisable(!choosedCard.validate(choosedField, choosedTurtles));
//        moveButton.setDisable(false);
    }
}
