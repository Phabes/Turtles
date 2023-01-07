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

public class MoveTurtleInStackCard extends Card {
    private final String color;
    private final boolean toTop;

    public MoveTurtleInStackCard(Board board, ArrayList<String> availableColors) {
        super(board);
        this.color = availableColors.get(GlobalSettings.getRandomNumber(0, availableColors.size()));
        this.toTop = GlobalSettings.getRandomNumber(0, 2) == 0;
        super.setNumberOfTurtlesRequired(1);
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
    public HBox getInfo() {
        HBox colorBox = new HBox();
        colorBox.setMinSize(20, 20);
        colorBox.setStyle("-fx-background-color: #" + color);
        return new HBox(new Text("Top " + toTop), colorBox);
    }

    @Override
    public boolean doTask(ArrayDeque<Turtle> choosedTurtles, Field choosedField) {
        //System.out.println("Did sth");
        if(choosedTurtles.size()!=1)
            return false;

        Optional<Turtle> optionalTurtle = super.board.getTurtleWithColor(color);
        Turtle turtle = optionalTurtle.orElse(null);
        if(turtle == null){
            return false;
        }
        Field field = turtle.getCurrentField();

        if(this.toTop){
            return field.moveTurtleTop(turtle);
        }else{
            return field.moveTurtleDown(turtle);
        }
    }
}
