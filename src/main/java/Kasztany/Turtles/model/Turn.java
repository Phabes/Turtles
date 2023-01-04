package Kasztany.Turtles.model;

import java.util.ArrayList;

public class Turn {
    private final ArrayList<Turtle> turtles;
    private int current;

    public Turn(ArrayList<Turtle> turtles) {
        this.turtles = turtles;
        this.current = 0;
    }

    public void next() {
        current++;
        current %= turtles.size();
    }

    public Player getCurrentPlayer(){
        return turtles.get(current).getPlayer();
    }
}
