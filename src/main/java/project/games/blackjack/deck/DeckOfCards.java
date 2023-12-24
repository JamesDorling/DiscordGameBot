package project.games.blackjack.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Cards repository
public class DeckOfCards {
    private List<Card> cards;

    public DeckOfCards() {
        cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("No more cards in the deck.");
            return null;
        }
        return cards.remove(0);
    }

    public int size() {
        return cards.size();
    }
}
