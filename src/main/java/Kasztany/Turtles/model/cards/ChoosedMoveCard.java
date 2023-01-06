package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Board;
import Kasztany.Turtles.model.Field;
import Kasztany.Turtles.model.Turtle;
import Kasztany.Turtles.settings.GlobalSettings;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class ChoosedMoveCard extends Card {
    private final int steps;
    private final boolean moveForward;

    public ChoosedMoveCard(Board board) {
        super(board);
        this.steps = GlobalSettings.getRandomNumber(1, 3);
        this.moveForward = GlobalSettings.getRandomNumber(0, 2) == 0;
        super.setFieldRequired(true);
        super.setNumberOfTurtlesRequired(1);
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
    public HBox getInfo(){
        return new HBox(new Text("Steps " + steps));
    }

    @Override
    public boolean doTask(ArrayDeque<Turtle> choosedTurtles, Field choosedField) {
        System.out.println("Did sth");
        return true;
    }

    @Override
    public ArrayList<Field> getFieldsToHighlight(Turtle turtle){
        return super.getPossibleFieldsToMove(turtle,steps,moveForward);
    }

}
