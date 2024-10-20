// created by justin 10/19/2024

import java.util.*;

class Card {
    String name;
    
    public Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class MurderDeck {
    // Cards for suspects, weapons, and rooms
    private static final Card[] SUSPECTS = {
        new Card("Miss Scarlet"), 
        new Card("Professor Plum"), 
        new Card("Colonel Mustard"), 
        new Card("Mrs. White"), 
        new Card("Mr. Green"), 
        new Card("Mrs. Peacock")
    };

    private static final Card[] WEAPONS = {
        new Card("Candlestick"), 
        new Card("Dagger"), 
        new Card("Lead Pipe"), 
        new Card("Revolver"), 
        new Card("Rope"), 
        new Card("Wrench")
    };

    private static final Card[] ROOMS = {
        new Card("Kitchen"), 
        new Card("Ballroom"), 
        new Card("Conservatory"), 
        new Card("Dining Room"), 
        new Card("Lounge"), 
        new Card("Hall"), 
        new Card("Study"), 
        new Card("Library"), 
        new Card("Billiard Room")
    };

    // Murder cards
    private final Card correctSuspect;
    private final Card correctWeapon;
    private final Card correctRoom;

    // Randomize for one suspect, weapon, and room
    public MurderDeck() {
        Random random = new Random();
        correctSuspect = SUSPECTS[random.nextInt(SUSPECTS.length)];
        correctWeapon = WEAPONS[random.nextInt(WEAPONS.length)];
        correctRoom = ROOMS[random.nextInt(ROOMS.length)];
    }

    // Check player accusation matches murder deck
    public boolean checkAccusation(Card suspect, Card weapon, Card room) {
        return correctSuspect == suspect && correctWeapon == weapon && correctRoom == room;
    }

    // TEST - murder solution (removed for final)
    public String gameSolution() {
        return correctSuspect.getName() + " in the " + correctRoom.getName() + " with the " + correctWeapon.getName();
    }

    // TEST (removed for final)
    public static void main(String[] args) {
        // Create the murder deck
        MurderDeck murderDeck = new MurderDeck();

        // Display the solution (for testing)
        System.out.println("Solution: " + murderDeck.gameSolution());

        // Example of checking an accusation
        Card accusedSuspect = new Card("Miss Scarlet");
        Card accusedWeapon = new Card("Revolver");
        Card accusedRoom = new Card("Kitchen");

        boolean isCorrect = murderDeck.checkAccusation(accusedSuspect, accusedWeapon, accusedRoom);
        System.out.println("Accusation correct? " + isCorrect);
    }
}
