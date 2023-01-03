package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;

import java.io.FileNotFoundException;

public class LastTurtleMoveCard extends Card {
    public LastTurtleMoveCard(Board board, int steps) {
        super(board);
        super.setHeader("Move last turtle");
        super.setAdditionalInfo("Steps " + steps);
        try {
            super.setIcon("arrow-right-bold.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doTask() {

    }
}
