// https://stackoverflow.com/questions/33886441/remove-random-item-from-arraylist
package tkm.gamelogic;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import tkm.Player;
import tkm.Main;

public class Deck {

    private Random rand;
    private String correctSuspect;
    private String correctWeapon;
    private String correctRoom;
    private List<String> caseFile;
    private List<String> playerHand;
    private List<String> availableDeck;
    private int randomIndex;

    public Deck(){
        List<String> suspects = new ArrayList<>();

        suspects.add("Miss Scarlet");
        suspects.add("Kernel Mustard");
        suspects.add("Mrs. White");
        suspects.add("Mr. Green");
        suspects.add("Mrs. Peacock");
        suspects.add("Professor Plum");

        List<String> weapons = new ArrayList<>();

        weapons.add("Knife");
        weapons.add("Candlestick");
        weapons.add("Revolver");
        weapons.add("Rope");
        weapons.add("Lead Pipe");
        weapons.add("Wrench");

        List<String> rooms = new ArrayList<>();

        rooms.add("Study");
        rooms.add("Hall");
        rooms.add("Lounge");
        rooms.add("Library");
        rooms.add("Billiard Rooms");
        rooms.add("Dining Room");
        rooms.add("Conservatory");
        rooms.add("Ballroom");
        rooms.add("Kitchen");

        // get case file and remove values from deck
        randomIndex = (int) ((Math.random() * suspects.size()));
        correctSuspect = suspects.get(randomIndex);
        suspects.remove(randomIndex);

        randomIndex = (int) ((Math.random() * weapons.size()));
        correctWeapon = weapons.get(randomIndex);
        weapons.remove(randomIndex);

        randomIndex = (int) ((Math.random() * rooms.size()));
        correctRoom = rooms.get(randomIndex);
        rooms.remove(randomIndex);

        caseFile = new ArrayList<>();
        caseFile.add(correctSuspect);
        caseFile.add(correctWeapon);
        caseFile.add(correctRoom);

        // available cards to be dealt
        availableDeck = new ArrayList<>();
        availableDeck.addAll(suspects);
        availableDeck.addAll(weapons);
        availableDeck.addAll(rooms);

        // shuffle deck
        Collections.shuffle(availableDeck);

    }

    public List<String> getCaseFile(Deck deck) {
        return caseFile;
    }

    public List<String> getAvailableDeck(Deck deck) {
        return availableDeck;
    }

    public void deal(Deck deck, int playerCount) {
        List<String> currentDeck = getAvailableDeck(deck);

        if (playerCount == 3) {
            List<String> playerHand1 = new ArrayList<>();
            List<String> playerHand2 = new ArrayList<>();
            List<String> playerHand3 = new ArrayList<>();

            for(int i = 0; i < 7; i++) {
                playerHand1.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand2.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand3.add(currentDeck.get(i));
                currentDeck.remove(i);
            }

            Player player1 = new Player(1, playerHand1);
            Player player2 = new Player(2, playerHand2);
            Player player3 = new Player(3, playerHand3);
            /*
            Player.setPlayerHand(1, playerHand1);
            Player.setPlayerHand(2, playerHand2);
            Player.setPlayerHand(3, playerHand3);
            */

            // 7 cards each
        }

        if (playerCount == 4) {
            List<String> playerHand1 = new ArrayList<>();
            List<String> playerHand2 = new ArrayList<>();
            List<String> playerHand3 = new ArrayList<>();
            List<String> playerHand4 = new ArrayList<>();

            for(int i = 0; i < 5; i++) {
                playerHand1.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand2.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand3.add(currentDeck.get(i));
                currentDeck.remove(i);
            }

            playerHand4.addAll(currentDeck);     
            
            Player player1 = new Player(1, playerHand1);
            Player player2 = new Player(2, playerHand2);
            Player player3 = new Player(3, playerHand3);
            Player player4 = new Player(4, playerHand4);
            // 3 players -- 5 cards
            // 1 player -- 6 cards
        }

        if (playerCount == 5) {
            List<String> playerHand1 = new ArrayList<>();
            List<String> playerHand2 = new ArrayList<>();
            List<String> playerHand3 = new ArrayList<>();
            List<String> playerHand4 = new ArrayList<>();
            List<String> playerHand5 = new ArrayList<>();

            for(int i = 0; i < 4; i++) {
                playerHand1.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand2.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand3.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand4.add(currentDeck.get(i));
                currentDeck.remove(i);
            }

            playerHand5.addAll(currentDeck);

            Player player1 = new Player(1, playerHand1);
            Player player2 = new Player(2, playerHand2);
            Player player3 = new Player(3, playerHand3);
            Player player4 = new Player(4, playerHand4);
            Player player5 = new Player(5, playerHand5);
            // 4 players -- 4 cards
            // 1 player -- 5 cards
        }

        if (playerCount == 6) {
            List<String> playerHand1 = new ArrayList<>();
            List<String> playerHand2 = new ArrayList<>();
            List<String> playerHand3 = new ArrayList<>();
            List<String> playerHand4 = new ArrayList<>();
            List<String> playerHand5 = new ArrayList<>();
            List<String> playerHand6 = new ArrayList<>();

            for(int i = 0; i < 3; i++) {
                playerHand1.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand2.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand3.add(currentDeck.get(i));
                currentDeck.remove(i);
            }

            for(int i = 0; i < 4; i++) {
                playerHand4.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand5.add(currentDeck.get(i));
                currentDeck.remove(i);
                playerHand6.add(currentDeck.get(i));
                currentDeck.remove(i);
            }

            Player player1 = new Player(1, playerHand1);
            Player player2 = new Player(2, playerHand2);
            Player player3 = new Player(3, playerHand3);
            Player player4 = new Player(4, playerHand4);
            Player player5 = new Player(5, playerHand5);
            Player player6 = new Player(6, playerHand6);
            // 3 players -- 3 cards
            // 3 players -- 4 cards
        }
    }
    
    
}
