
package tkm.gamelogic;

import javax.swing.ImageIcon;
import tkm.enums.CharacterType;

/**
 * @file GamePiece
 * @date 11/9/12024
 * @author Mike
 * 
 * This class represents a gamepiece on the board. A player will have a gamepiece
 * associated with it. 
 * 
 * A Game Piece has a location, and an Icon.
 */

public class GamePiece {

    private int x;
    private int y;
    private final ImageIcon icon;
    private final CharacterType character;
    
    public GamePiece(int x, int y, ImageIcon icon, CharacterType character) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.character = character;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public ImageIcon getIcon() {
        return icon;
    }
    
    public CharacterType getCharacter() {
        return character;
    }
    
}
