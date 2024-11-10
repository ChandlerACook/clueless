package tkm.gamelogic;

import java.util.ArrayList;
import tkm.enums.CharacterType;

public class Player {
    private final String name;
    private final CharacterType character;  // The character the player is playing as
    private final GamePiece gamePiece;
    private final ArrayList<Card> hand;
    
    // initialize a player with a name and character
    public Player(String name, GamePiece gamePiece, ArrayList<Card> hand) {
        this.name = name;
        this.gamePiece = gamePiece;
        this.character = gamePiece.getCharacter();
        this.hand = hand; 
    }

    public String getName() {
        return name;
    }

    public CharacterType getCharacter() {
        return character;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public GamePiece getGamePiece() {
        return gamePiece;
    }
}
