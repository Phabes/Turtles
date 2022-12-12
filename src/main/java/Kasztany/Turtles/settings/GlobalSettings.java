package Kasztany.Turtles.settings;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GlobalSettings {
    private final int optionsSpace;
    private final int textFieldSize;
    private final double boardWidth;
    private final double boardHeight;
    private final double gridWidth;
    private final double gridHeight;
    private final double minTurtleSize;
    private final double headerTurtleSize;
    private final int fruitSize;

    public GlobalSettings(){
        optionsSpace = 20;
        textFieldSize = 100;
        boardWidth = 1000;
        boardHeight = 700;
        gridWidth = 800;
        gridHeight = 650;
        minTurtleSize = 40;
        headerTurtleSize = 50;
        fruitSize = 20;
    }
    public int getOptionsSpace() {
        return optionsSpace;
    }

    public int getTextFieldSize() {
        return textFieldSize;
    }

    public double getBoardWidth() {
        return boardWidth;
    }

    public double getBoardHeight() {
        return boardHeight;
    }

    public double getGridWidth() {
        return gridWidth;
    }

    public double getGridHeight() {
        return gridHeight;
    }

    public double getMinTurtleSize() {
        return minTurtleSize;
    }

    public double getHeaderTurtleSize() {
        return headerTurtleSize;
    }

    public int getFruitSize() {
        return fruitSize;
    }

    public void setScreenInTheMiddle(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
