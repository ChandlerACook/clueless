package tkm;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
    private int turnNum;
    //private String name;
    //private String character;  // The character the player is playing as
    private List<String> playerHand;
    
    // initialize a player with a name and character
    public Player(int turnNum, List<String> playerHand) {
        this.turnNum = turnNum;
        //this.name = name;
        //this.character = character;
        this.playerHand = playerHand;
    }

    /*
    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }
*/
    public int getTurnNum() {
        return turnNum;
    }

    public List<String> getPlayerHand() {
        return playerHand;
    }

    /*
    public void setPlayerHand(int turnNum, List<String> playerHand) {
        if (turnNum == this.turnNum) {
            this.playerHand = playerHand;
        }
    }
        */
}
