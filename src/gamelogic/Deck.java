package tkm.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tkm.Player;
import tkm.Card;

public class Deck {
    private ArrayList<Card> deck;       // The deck of cards
    private List<Player> players;  // List of players in the game

    // Case file variables (solution of the game)
    private Card correctSuspect;
    private Card correctWeapon;
    private Card correctRoom;

    // Constructor accepts the list of players to deal cards to
    public Deck(List<Player> players) {
        this.players = players;
        this.deck = new ArrayList<>();
        initializeDeck();
    }

    // Get the case file (solution)
    public List<String> getCaseFile() {
        List<String> caseFile = new ArrayList<>();
        caseFile.add(correctSuspect.getName());
        caseFile.add(correctWeapon.getName());
        caseFile.add(correctRoom.getName());
        return caseFile;
    }

    // Initialize the deck with cards (suspects, weapons, rooms)
    private void initializeDeck() {
        // Add all cards to the deck
        String[] suspects = {"Miss Scarlet", "Professor Plum", "Mrs. White", "Reverend Green", "Mrs. Peacock", "Colonel Mustard"};
        String[] weapons = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Wrench"};
        String[] rooms = {"Kitchen", "Ballroom", "Conservatory", "Dining Room", "Library", "Billiard Room", "Lounge", "Hall", "Study"};

        // Add suspect cards
        for (String suspect : suspects) {
            deck.add(new Card(suspect));
        }

        // Add weapon cards
        for (String weapon : weapons) {
            deck.add(new Card(weapon));
        }

        // Add room cards
        for (String room : rooms) {
            deck.add(new Card(room));
        }

        // Shuffle the deck
        Collections.shuffle(deck);

        // Select the case file (solution) - remove the last 3 cards as the solution
        correctSuspect = deck.remove(deck.size() - 1); // Last card is the suspect
        correctWeapon = deck.remove(deck.size() - 1);  // Last card is the weapon
        correctRoom = deck.remove(deck.size() - 1);    // Last card is the room
    }

    // Deals cards to the players
    public void dealCards() {
        int totalCards = deck.size();
        int numPlayers = players.size();

        // Ensure there are players and cards in the deck before dealing
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

            // Distribute leftover cards (if any)
            if (leftoverCards > 0 && !deck.isEmpty()) {
                hand.add(deck.remove(0));
                leftoverCards--;
            }

            // Set the player's hand
            player.setPlayerHand(hand);
        }
    }

    // Check if the accusation is correct (matching the solution)
    public boolean checkAccusation(Card suspect, Card weapon, Card room) {
        return suspect.getName().equals(correctSuspect.getName()) &&
                weapon.getName().equals(correctWeapon.getName()) &&
                room.getName().equals(correctRoom.getName());
    }

    // Check if the suggestion is correct (matching any part of the solution)
    public boolean checkSuggestion(Card suspect, Card weapon, Card room) {
        return suspect.getName().equals(correctSuspect.getName()) ||
                weapon.getName().equals(correctWeapon.getName()) ||
                room.getName().equals(correctRoom.getName());
    }

    // Getter for the deck (useful for testing or other purposes)
    public ArrayList<Card> getDeck() {
        return deck;
    }
}
