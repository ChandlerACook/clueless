
package tkm.gamelogic;

import java.awt.Color;
import java.awt.Image;

/**
 * @file Tile
 * @date 10/24/2024
 * @author Mike
 * 
 * The purpose of this class is to represent a single Tile. It can be occupied
 * by a character, or weapon.
 */

public class Tile {
    
    private boolean occupied;
    private Color color;
    private Image image;
    
    // Constructor for default tile
    public Tile() {
        this.occupied = false;
        this.color = Color.WHITE;
    }
    
    public Tile(Color color) {
        this.occupied = true;
        this.color = color;
    }
    
    public boolean isOccupied() {
        return this.occupied;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public Image getImage() {
        return this.image;
    }
    
    // Updates the tile's color and occupied status.
    public void setColor(Color color) {
        this.color = color;
        occupied = !color.equals(Color.WHITE);
    }
    
} // end class Tile
