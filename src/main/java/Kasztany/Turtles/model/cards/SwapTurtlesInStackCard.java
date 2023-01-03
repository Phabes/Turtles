package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;

import java.io.FileNotFoundException;

public class SwapTurtlesInStackCard extends Card {
    public SwapTurtlesInStackCard(Board board) {
        super(board);
        super.setHeader("Swap turtles in stack");
        super.setAdditionalInfo("");
        try {
            super.setIcon("atom-variant.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doTask() {

    }
}
