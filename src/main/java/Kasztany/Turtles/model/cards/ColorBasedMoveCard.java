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
import java.util.Optional;

public class ColorBasedMoveCard extends Card {
    private final int steps;
    private final boolean moveForward;
    private final String color;

    public ColorBasedMoveCard(Board board, ArrayList<String> availableColors) {
        super(board);
        this.steps = GlobalSettings.getRandomNumber(1, 3);
        super.setFieldRequired(true);
        this.moveForward = GlobalSettings.getRandomNumber(0, 2) == 0;
        this.color = availableColors.get(GlobalSettings.getRandomNumber(0, availableColors.size()));
        super.setHeader("Move specific turtle");
        super.setAdditionalInfo("Steps " + steps + ", color " + color);
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
    public HBox getInfo() {
        HBox colorBox = new HBox();
        colorBox.setMinSize(20, 20);
        colorBox.setStyle("-fx-background-color: #" + color);
        return new HBox(new Text("Steps " + steps), colorBox);
    }

    public boolean doTask(ArrayDeque<Turtle> choosedTurtles, Field choosedField) {
        if(choosedTurtles.size()!=1)
            return false;
        Turtle choosedTurtle=choosedTurtles.poll();
        if (choosedTurtle != null && choosedField != null) {
            choosedTurtle.move(choosedField);
            if (choosedField.getFruit().isPresent()) {
                choosedTurtle.eat(choosedField.getFruit().get());
                choosedField.deleteFruit();
            }
        }
        return true;
    }
    @Override
    public  ArrayList<Turtle> getTurtles(){
        ArrayList<Turtle> turtles=new ArrayList<>();
        board.getTurtles().forEach(turtle -> {
            if(turtle.getColor().equals(color))
                turtles.add(turtle);
        });
        return turtles;
    }
    @Override
    public ArrayList<Field> getFieldsToHighlight(Turtle turtle){
        return super.getPossibleFieldsToMove(turtle,steps,moveForward);
    }
}
