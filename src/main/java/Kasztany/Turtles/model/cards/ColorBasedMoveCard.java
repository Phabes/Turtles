package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Turtle;

import java.io.FileNotFoundException;

public class ColorBasedMoveCard extends Card{
    private final int steps;
    private final boolean moveForward;
    public ColorBasedMoveCard(Board board,int steps,boolean moveForward, String color) {
        super(board);
        this.steps=steps;
        this.moveForward=moveForward;
        super.setHeader(color);
        super.setAdditionalInfo("Steps "+steps);
        try {
            if(moveForward)
                super.setIcon("arrow-right-bold.png");
            else
                super.setIcon("arrow-left-bold.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doTask() {

    }
}
