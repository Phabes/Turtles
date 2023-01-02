package Kasztany.Turtles.model.cards;

import Kasztany.Turtles.model.Turtle;

public class ColorBasedMoveCard implements Card{
    private final Turtle turtle;
    private final int steps;

    public ColorBasedMoveCard(Turtle turtle, int steps) {
        this.turtle = turtle;
        this.steps = steps;
    }

    @Override
    public void doTask() {
        System.out.println("Move "+turtle.getColor()+" "+ steps+ " steps");
    }
}
