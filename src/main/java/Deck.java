package tkm.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @file Deck.java
 * @date 11/30/2024
 * @author justin
 *
 * Deck deals with dealing cards, shuffling cards,
 * and storing the correct solution cards (renamed from MurderDeck)
 */

public class Deck {
    private ArrayList<Card> deck;       // The deck of cards
    private List<Player> players;  // List of players in the game

    // Correct solution cards
    private Card correctSuspect;
    private Card correctWeapon;
    private Card correctRoom;

    // Constructor accepts the list of players to deal cards to
    public Deck() {
        this.players = players;
        this.deck = new ArrayList<>();
        initializeDeck();
    }
    public Card getCardByName(String cardName) {
        for (Card card : deck) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;  // Return null if no matching card is found
    }

    // Initialize the deck with cards (suspects, weapons, rooms)
    private void initializeDeck() {
        // Add all cards to the deck
        String[] suspects = {"Miss Scarlet", "Professor Plum", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Kernel Mustard"};
        String[] weapons = {"Candlestick", "Knife", "Lead Pipe", "Revolver", "Rope", "Wrench"};
        String[] rooms = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Library", "Billiard Room", "Lounge", "Hall", "Study"};

        // Add suspect cards
        for (String suspect : suspects) {
            deck.add(new Card(suspect, 1));
        }

        // Add weapon cards
        for (String weapon : weapons) {
            deck.add(new Card(weapon, 2));
        }

        // Add room cards
        for (String room : rooms) {
            deck.add(new Card(room, 3));
        }

        // Shuffle the deck
        Collections.shuffle(deck);

        // Loop through the deck to select one of each card type, ensures that cards are correct type
        for (Card card : deck) {
            if (card.getType() == 1 && correctSuspect == null) {
                correctSuspect = card;
            } else if (card.getType() == 2 && correctWeapon == null) {
                correctWeapon = card;
            } else if (card.getType() == 3 && correctRoom == null) {
                correctRoom = card;
            }
        }

        // Remove the selected cards from the deck
        deck.remove(correctSuspect);
        deck.remove(correctWeapon);
        deck.remove(correctRoom);
    }

    // Deals cards to the players
    public void dealCards() {
        int totalCards = deck.size();
        int numPlayers = players.size();

        // Check that there are players and cards in the deck before dealing, else null
        if (numPlayers == 0 || totalCards == 0) {
            System.out.println("Error: No players or cards to deal.");
            return;
        }

        // Calculate how many cards each player should get
        int cardsPerPlayer = totalCards / numPlayers;
        int leftoverCards = totalCards % numPlayers;

        for (int i = 0; i < numPlayers; i++) {
            Player player = players.get(i);
            ArrayList<Card> hand = new ArrayList<>();

            // Deal the standard number of cards
            for (int j = 0; j < cardsPerPlayer; j++) {
                if (!deck.isEmpty()) {
                    hand.add(deck.remove(0));
                }
            }

            // Distribute leftover cards
            if (leftoverCards > 0 && !deck.isEmpty()) {
                hand.add(deck.remove(0));
                leftoverCards--;
            }

            // Set the player's hand
            player.setPlayerHand(hand);
        }
    }

    // Check if the accusation is correct
    public boolean checkAccusation(Card suspect, Card weapon, Card room) {
        return suspect.getName().equals(correctSuspect.getName()) &&
                weapon.getName().equals(correctWeapon.getName()) &&
                room.getName().equals(correctRoom.getName());
    }

    // Check if the suggestion is correct 
    public boolean checkSuggestion(Card suspect, Card weapon, Card room) {
        return suspect.getName().equals(correctSuspect.getName()) ||
                weapon.getName().equals(correctWeapon.getName()) ||
                room.getName().equals(correctRoom.getName());
    }

    // Getter for the deck (useful for testing or other purposes)
    public ArrayList<Card> getDeck() {
        return deck;
    }

    public Card correctSuspect() {
        return correctSuspect;
    }

    public Card correctWeapon() {
        return correctWeapon;
    }

    public Card correctRoom() {
        return correctRoom;
    }
}
