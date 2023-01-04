package Kasztany.Turtles.model;

import Kasztany.Turtles.model.cards.Card;

import java.util.ArrayList;

public class Player {
    private final String name;
    private final ArrayList<Card> cards = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
