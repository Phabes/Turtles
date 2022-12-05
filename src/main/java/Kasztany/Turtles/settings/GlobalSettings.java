package Kasztany.Turtles.settings;

import org.springframework.stereotype.Service;

@Service
public class GlobalSettings {
    private final int optionsSpace;
    private final int textFieldSize;
    private final int endPanelWidth;
    private final double boardWidth;
    private final double boardHeight;
    private final double gridWidth;
    private final double gridHeight;
    private final double minTurtleSize;

    public GlobalSettings(){
        optionsSpace = 20;
        textFieldSize = 100;
        endPanelWidth = 300;
        boardWidth = 1000;
        boardHeight = 700;
        gridWidth = 800;
        gridHeight = 650;
        minTurtleSize = 40;
    }
    public int getOptionsSpace() {
        return optionsSpace;
    }

    public int getTextFieldSize() {
        return textFieldSize;
    }

    public int getEndPanelWidth() {
        return endPanelWidth;
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
}
