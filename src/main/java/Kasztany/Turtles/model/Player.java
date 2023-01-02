package Kasztany.Turtles.model;

import Kasztany.Turtles.model.cards.Card;

import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<Card> cards;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
