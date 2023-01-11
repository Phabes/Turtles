package Kasztany.Turtles.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

public class Turn {
    private final ArrayList<Turtle> turtles;
    private int current;
    private final StringProperty playerName;
    private final StringProperty playerColor;

    public Turn(ArrayList<Turtle> turtles) {
        this.turtles = turtles;
        this.current = -1;
        this.playerName = new SimpleStringProperty("");
        this.playerColor = new SimpleStringProperty("");
    }

    public void next() {
        current++;
        current %= turtles.size();
        playerName.set(turtles.get(current).getPlayer().getName());
        playerColor.set(turtles.get(current).getColor());
    }

    public Player getCurrentPlayer() {
        return turtles.get(current).getPlayer();
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public String getPlayerColor() {
        return playerColor.get();
    }

    public StringProperty playerColorProperty() {
        return playerColor;
    }
}
