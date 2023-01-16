package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;

public class SwapTurtlesInStackCard extends Card {
    public SwapTurtlesInStackCard(Board board) {
        super(board);
        super.setHeader("Swap turtles in stack");
        super.setNumberOfTurtlesRequired(2);
        super.setAdditionalInfo("2 turtles required");
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
        Turtle turtle1 = choosedTurtles.poll();
        Turtle turtle2 = choosedTurtles.poll();
        assert turtle1 != null;
        Field field = turtle1.getCurrentField();
        return field.swapTurtles(turtle1, turtle2);
    }

    public boolean validate(Field choosedField, ArrayDeque<Turtle> choosedTurtles) {
        if (choosedTurtles.size() == getNumberOfTurtlesRequired()) {
            Turtle turtle1 = choosedTurtles.poll();
            Turtle turtle2 = choosedTurtles.poll();
            assert turtle1 != null;
            assert turtle2 != null;
            boolean valid = turtle1.getCurrentField() == turtle2.getCurrentField();
            choosedTurtles.add(turtle1);
            choosedTurtles.add(turtle2);
            return valid;
        }
        return false;
    }
}
