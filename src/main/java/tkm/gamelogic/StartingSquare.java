
package tkm.gamelogic;

import tkm.enums.CharacterType;
import java.awt.Color;

/**
 * @file StartingSquare.java
 * @date 10/26/2024
 * @author Mike
 * 
 * This class represents a characters starting square.
 */

public class StartingSquare extends Location{
    
    private Tile tile;
    private CharacterType character;
    private boolean gameStart;
    
    // Constructor for starting squares
    public StartingSquare(CharacterType character) {
        super(character.getName());
        this.character = character;
        
        tile = new Tile(this.character.getColor());
        this.gameStart = true;
    }
    
    // For moving off of Starting Square, and making it invalid to move back
    public void startGame() {
        gameStart = false;
        this.tile.setColor(Color.BLACK);
    }
    
    // Used to connect to the nearest hallway, making a one way connection.
    // TO DO, maybe not needed
    @Override
    public void connect(Location location) {
        this.getConnections().add(location);
    }
    
    public Tile getTile() {
        return tile;
    }
    
    public CharacterType getCharacterType() {
        return character;
    }
    
}