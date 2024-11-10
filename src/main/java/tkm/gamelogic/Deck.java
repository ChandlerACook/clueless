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
    }

    
    public List<String> getCaseFile(Deck deck) {
        return caseFile;
    }

    public List<String> deal(Deck deck, Player player) {

        return playerHand;
    }
    
    
}


// 1 = suspect
// 2 = weapon
// 3 = room