package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;

import java.io.FileNotFoundException;

public class MoveTurtleInStackCard extends Card {
    public MoveTurtleInStackCard(Board board, boolean toTop) {
        super(board);
        super.setHeader("Move turtle in stack");

        try {
            if (toTop) {
                super.setAdditionalInfo("On top ");
                super.setIcon("arrow-u-up-left-bold.png");
            } else {
                super.setAdditionalInfo("On bottom ");
                super.setIcon("arrow-u-down-left-bold.png");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doTask() {

    }
}
