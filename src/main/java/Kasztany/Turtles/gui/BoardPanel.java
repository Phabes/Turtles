package Kasztany.Turtles.gui;


import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.model.Vector;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class BoardPanel {
    private final Board board;
    private final VBox boardBox = new VBox();
    private final GridPane gridPane = new GridPane();
    private final HBox playersBox = new HBox();
    private final Button moveButton = new Button("Move");
    private Turtle choosedTurtle = null;

    public BoardPanel(Board board) {
        this.board = board;
        boardBox.getChildren().addAll(playersBox, gridPane);
        boardBox.setPrefSize(1000, 700);
        String boardLayout = """
                -fx-padding: 10;
                """;
        boardBox.setStyle(boardLayout);
        moveButton.setDisable(true);
        gridPane.setPrefSize(800, 600);
        gridPane.setStyle("""
                    -fx-border-color: red;
                    -fx-border-width: 1;
                    -fx-border-style: solid;
                    """);

        Vector maxVector = board.getMaxVector();
        System.out.println(maxVector.toString());
        double prefSize = 800.0 / (maxVector.getX() + 1);
        System.out.println(prefSize);


        for (int x = 0; x <= maxVector.getX(); x++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(50, prefSize, 200));
//            gridPane.getColumnConstraints().add(new ColumnConstraints(50, 50, 200));
        }
        for (int y = 0; y <= maxVector.getY(); y++) {
            gridPane.getRowConstraints().add(new RowConstraints(50, prefSize, 200));
//            gridPane.getRowConstraints().add(new RowConstraints(50, 50, 200));
        }
        drawHeader();
        drawBoard();
    }

    private void drawHeader() {
        playersBox.setPrefSize(800, 50);
        for (Turtle turtle : board.getTurtles()) {
            Text turtleText = new Text(turtle.getName());

            HBox turtleDrawing = drawTurtle(50, turtle.getColor());
            turtleDrawing.setOnMouseClicked((e) -> turtleClick(turtle));
            VBox turtleBox = new VBox(turtleText, turtleDrawing);

            playersBox.setSpacing(800.0 / board.getTurtles().size() - 50);
            playersBox.getChildren().add(turtleBox);
            playersBox.setAlignment(Pos.CENTER);
            playersBox.setStyle("""
                    -fx-border-color: blue;
                    -fx-border-width: 1;
                    -fx-border-style: solid;
                    """);
        }
        moveButton.setOnMouseClicked((e) -> moveButtonClick());
        playersBox.getChildren().add(moveButton);
    }

    private void drawBoard() {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            Vector maxVector = board.getMaxVector();

            for (Field field : board.getFields()) {
                GridPane fieldBox = new GridPane();
                fieldBox.setStyle("""
                        -fx-border-color: #AAAAAA;
                        -fx-border-width: 1;
                        -fx-border-style: solid;
                        -fx-border-insets: 1;
                        """);
                if (field.getBottomTurtle().isPresent()) {
                    Turtle turtle = field.getBottomTurtle().get();
                    System.out.println(turtle.getName() + " " + turtle.getColor() + " " + field.getPosition().toString());
                    ArrayList<Turtle> turtlesOnField = new ArrayList<>();
                    turtlesOnField.add(turtle);
                    while (turtle.getTurtleOnBack().isPresent()) {
                        turtle = turtle.getTurtleOnBack().get();
                        turtlesOnField.add(turtle);
                    }
                    double size = Math.max(800 / (maxVector.getX() + 1), 50);
//                System.out.println(800 / (maxVector.getX() + 1));
                    drawTurtlesInField(size / (turtlesOnField.size() - 1), fieldBox, turtlesOnField);
                }

                gridPane.add(fieldBox, field.getPosition().getX(), maxVector.getY() - field.getPosition().getY());
            }
            gridPane.setAlignment(Pos.CENTER);
        });
    }

    private void drawTurtlesInField(double size, GridPane field, ArrayList<Turtle> turtlesOnField) {
        System.out.println("SIZE " + turtlesOnField.size());
//        if(turtlesOnField.size() == 1)
//            size/=2;
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
                        "-fx-border-style: solid;\n"
//                        "-fx-padding:" + size / 20 + ";\n"
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
        System.out.println("DRAW " + color);
        return turtle;
    }

    private void turtleClick(Turtle turtle) {
        System.out.println("Choose " + turtle.getColor());
        choosedTurtle = turtle;
        moveButton.setDisable(false);
    }

    private void moveButtonClick() {
        if (choosedTurtle != null) {
            System.out.println("Move " + choosedTurtle.getName());
            choosedTurtle.move();
            drawBoard();
            moveButton.setDisable(true);
            choosedTurtle = null;
        }
    }

    public VBox getBoard() {
        return boardBox;
    }
}
