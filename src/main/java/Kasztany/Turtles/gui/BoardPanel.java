package Kasztany.Turtles.gui;


import Kasztany.Turtles.settings.GlobalSettings;
import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.model.Vector;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardPanel {
    private final Board board;
    private final VBox boardBox = new VBox();
    private final GridPane gridPane = new GridPane();
    private final HBox playersBox = new HBox();
    private final Button moveButton = new Button("Move");
    private Turtle choosedTurtle = null;
    private GlobalSettings globalSettings = new GlobalSettings();

    public BoardPanel(Board board) {
        this.board = board;
        boardBox.getChildren().addAll(playersBox, gridPane);
        boardBox.setPrefSize(globalSettings.getBoardWidth(), globalSettings.getBoardHeight());
        String boardLayout = """
                -fx-padding: 10;
                """;
        boardBox.setStyle(boardLayout);
        moveButton.setDisable(true);
        moveButton.setOnMouseClicked((e) -> moveButtonClick());
        setMoveButtonColor("454242");
        gridPane.setPrefSize(globalSettings.getGridWidth(), globalSettings.getGridHeight());
        gridPane.setStyle("""
                -fx-border-color: red;
                -fx-border-width: 1;
                -fx-border-style: solid;
                """);


        Vector maxVector = board.getMaxVector();
        double prefSize = globalSettings.getGridWidth() / (maxVector.getX() + 1);

        for (int x = 0; x <= maxVector.getX(); x++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(50, prefSize, 200));
        }
        for (int y = 0; y <= maxVector.getY(); y++) {
            gridPane.getRowConstraints().add(new RowConstraints(50, prefSize, 200));
        }
        drawHeader();
        drawBoard();
    }

    private void drawHeader() {
        double headerHeight = globalSettings.getBoardHeight() - globalSettings.getGridHeight();
        playersBox.setPrefSize(globalSettings.getGridWidth(), headerHeight);
        for (Turtle turtle : board.getTurtles()) {
            Text turtleText = new Text(turtle.getName());

            HBox turtleDrawing = drawTurtle(globalSettings.getHeaderTurtleSize(), turtle.getColor());
            turtleDrawing.setOnMouseClicked((e) -> turtleClick(turtle));
            turtleDrawing.setOnMouseEntered((e) -> turtleDrawing.setCursor(Cursor.HAND));
            VBox turtleBox = new VBox(turtleText, turtleDrawing);

            playersBox.setSpacing(globalSettings.getGridWidth() / board.getTurtles().size() - headerHeight);
            playersBox.getChildren().add(turtleBox);
            playersBox.setAlignment(Pos.CENTER);
            playersBox.setStyle("""
                    -fx-border-color: blue;
                    -fx-border-width: 1;
                    -fx-border-style: solid;
                    """);
        }
        playersBox.getChildren().add(moveButton);
    }

    private void drawBoard() {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            Vector maxVector = board.getMaxVector();
            double size = Math.max(globalSettings.getGridWidth() / (maxVector.getX() + 1), globalSettings.getMinTurtleSize());
            for (Field field: board.getNeighbourhood().getFields()) {
                GridPane fieldBox = new GridPane();
                fieldBox.setMinSize(globalSettings.getMinTurtleSize(), globalSettings.getMinTurtleSize());
                fieldBox.setStyle("""
                        -fx-border-color: #AAAAAA;
                        -fx-border-width: 1;
                        -fx-border-style: solid;
                        -fx-border-insets: 1;
                        """);
                if (field.getBottomTurtle().isPresent()) {
                    Turtle turtle = field.getBottomTurtle().get();
                    ArrayList<Turtle> turtlesOnField = new ArrayList<>();
                    turtlesOnField.add(turtle);
                    while (turtle.getTurtleOnBack().isPresent()) {
                        turtle = turtle.getTurtleOnBack().get();
                        turtlesOnField.add(turtle);
                    }
                    drawTurtlesInField(size / (turtlesOnField.size() + 1), fieldBox, turtlesOnField);
                }
                gridPane.add(fieldBox, field.getPosition().getX(), maxVector.getY() - field.getPosition().getY());
            }
            gridPane.setAlignment(Pos.CENTER);
        });
    }

    private void drawTurtlesInField(double size, GridPane field, ArrayList<Turtle> turtlesOnField) {
        for (int i = 0; i < turtlesOnField.size(); i++) {
            field.add(drawTurtle(size, turtlesOnField.get(i).getColor()), 0, turtlesOnField.size() - i - 1);
        }
        field.setAlignment(Pos.CENTER);
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
        head.setStyle(
                "-fx-border-color: black;\n" +
                        "-fx-border-width: 1;\n" +
                        "-fx-border-style: solid;\n" +
                        "-fx-padding:" + size / 20 + ";\n"
        );
        head.setAlignment(Pos.TOP_RIGHT);
        headBox.getChildren().add(head);
        HBox eye = new HBox();
        eye.setMaxSize(4, 4);
        eye.setMinSize(4, 4);
        eye.setStyle("""
                -fx-background-color: black;
                """);

        head.getChildren().add(eye);

        HBox turtle = new HBox(shell, headBox);
        turtle.setPrefSize(size, size / 2);
        return turtle;
    }

    private void turtleClick(Turtle turtle) {
        if (!board.isGameEnd()) {
            System.out.println("Choose " + turtle.getName() + " " + turtle.getColor());
            choosedTurtle = turtle;
            moveButton.setDisable(false);
            setMoveButtonColor(turtle.getColor());
        }
    }

    private void moveButtonClick() {
        if (choosedTurtle != null) {
            System.out.println("Move " + choosedTurtle.getName());
            choosedTurtle.move();
            if (board.isGameEnd()) {
                Turtle winner = board.findWinner();
                System.out.println("Winner is " + winner.getName() + " " + winner.getColor());
                EndPanel endPanel = new EndPanel(winner);
                endPanel.showPanel();
            }
            drawBoard();
            moveButton.setDisable(true);
            choosedTurtle = null;
            setMoveButtonColor("454242");
        }
    }

    private void setMoveButtonColor(String color) {
        moveButton.setStyle(
                "-fx-border-color: #" + color + ";\n" +
                        "-fx-border-width: 3;\n" +
                        " -fx-border-style: solid;\n"
        );
    }

    public VBox getBoard() {
        return boardBox;
    }
}
