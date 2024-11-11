package tkm;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String character;  // The character the player is playing as
    private List<Card> cards;

    // initialize a player with a name and character
    public Player(String name, String character) {
        this.name = name;
        this.character = character;
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getMatchingCards(Card suspect, Card weapon, Card room) {
        List<Card> matchingCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.equals(suspect) || card.equals(weapon) || card.equals(room)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

    public List<Card> getPlayerHand() {
        return cards;
    }

    // Set the player's hand of cards
    public void setPlayerHand(List<Card> hand) {
        this.cards = hand;
    }
}
