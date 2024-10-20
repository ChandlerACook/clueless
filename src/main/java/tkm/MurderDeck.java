// Created by justin 10/19/2024
package tkm; 

import java.util.Random;

// MurderDeck class to manage suspects, weapons, and rooms cards
public class MurderDeck {
    // arrays for suspects, weapons, and rooms
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

    // final solution cards
    private final Card correctSuspect;
    private final Card correctWeapon;
    private final Card correctRoom;

    // randomized solution cards
    public MurderDeck() {
        Random random = new Random();
        correctSuspect = SUSPECTS[random.nextInt(SUSPECTS.length)];
        correctWeapon = WEAPONS[random.nextInt(WEAPONS.length)];
        correctRoom = ROOMS[random.nextInt(ROOMS.length)];
    }

    // check if the player accusation is correct
    public boolean checkAccusation(Card suspect, Card weapon, Card room) {
        return correctSuspect == suspect && correctWeapon == weapon && correctRoom == room;
    }

    // TESTING
    public String gameSolution() {
        return correctSuspect.getName() + " in the " + correctRoom.getName() + " with the " + correctWeapon.getName();
    }

    // TESTING
    public static void main(String[] args) {
        // create new murderdeck
        MurderDeck murderDeck = new MurderDeck();

        
        System.out.println("Solution: " + murderDeck.gameSolution());

        // TESTING (check accusation)
        Card accusedSuspect = new Card("Miss Scarlet");
        Card accusedWeapon = new Card("Revolver");
        Card accusedRoom = new Card("Kitchen");

        boolean isCorrect = murderDeck.checkAccusation(accusedSuspect, accusedWeapon, accusedRoom);
        System.out.println("Accusation correct? " + isCorrect);
    }
}
