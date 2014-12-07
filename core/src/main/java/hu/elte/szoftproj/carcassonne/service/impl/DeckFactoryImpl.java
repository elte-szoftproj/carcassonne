package hu.elte.szoftproj.carcassonne.service.impl;

import hu.elte.szoftproj.carcassonne.service.Deck;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.impl.deck.BasicDeck;

public class DeckFactoryImpl implements DeckFactory {
    @Override
    public Deck getDeck(String name) {
        switch(name) {
            case "basic": return new BasicDeck();
        }

        throw new IllegalArgumentException("Unknown deck type: " + name);
    }
}
