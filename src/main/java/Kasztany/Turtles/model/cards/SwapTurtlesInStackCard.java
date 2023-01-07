package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;

public class SwapTurtlesInStackCard extends Card {
    public SwapTurtlesInStackCard(Board board) {
        super(board);
        super.setHeader("Swap turtles in stack");
        super.setNumberOfTurtlesRequired(2);
        super.setAdditionalInfo("");
        try {
            super.setIcon("atom-variant.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HBox getInfo() {
        return new HBox();
    }

    @Override
    public boolean doTask(ArrayDeque<Turtle> choosedTurtles, Field choosedField) {
        //System.out.println("Did sth");

        if(choosedTurtles.size()!=2)
            return false;

        Turtle turtle1 = choosedTurtles.poll();
        Turtle turtle2 = choosedTurtles.poll();
        return choosedField.swapTurtles(turtle1, turtle2);
    }
}
