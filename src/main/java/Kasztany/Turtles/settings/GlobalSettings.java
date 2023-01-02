package Kasztany.Turtles.settings;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GlobalSettings {
    public static final int OPTIONS_SPACE = 20;
    public static final int TEXT_FIELD_SIZE = 100;
    public static final double BOARD_WIDTH = 1000;
    public static final double BOARD_HEIGHT = 750;
    public static final double CARD_HEIGHT = 150;
    public static final int CARD_WIDTH = 100;
    public static final int GRID_WIDTH = 800;
    public static final double GRID_HEIGHT = 650;
    public static final double MIN_TURTLE_SIZE = 40;
    public static final double HEADER_TURTLE_SIZE = 50;
    public static final int FRUIT_SIZE = 20;

    private GlobalSettings() {
    }

    public static void setScreenInTheMiddle(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
}