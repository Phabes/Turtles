package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;

import java.io.FileNotFoundException;

public class ChoosedMoveCard extends Card {
    private final int steps;
    private final boolean moveForward;

    public ChoosedMoveCard(Board board, int steps, boolean moveForward) {
        super(board);
        this.steps = steps;
        this.moveForward = moveForward;
        super.setHeader("Choose any turtle");
        super.setAdditionalInfo("Steps " + steps);
        try {
            if (moveForward)
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
