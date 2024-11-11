
package tkm.gamelogic;

import java.awt.Graphics;
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
    //private final ImageIcon icon;
    private final CharacterType character;
    
    public GamePiece(int x, int y, CharacterType character) {
        this.x = x;
        this.y = y;
        //this.icon = icon;
        this.character = character;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
//    public ImageIcon getIcon() {
//        return icon;
//    }
    
    public void draw(Graphics g) {
        g.setColor(character.getColor());
        g.fillOval(x * 30, y * 30, 30, 30); // Draw player as a circle with a size of 30x30 pixels
    }
    
    public CharacterType getCharacter() {
        return character;
    }
    
    public String toString() {
        return character.getName() + "," + x + "," + y;
    }
    
}
