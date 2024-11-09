
package tkm.gamelogic;

import tkm.enums.HallwayType;
import java.awt.Color;

/**
 * @file Hallway.java
 * @date 10/25/2024
 * @author Mike
 * 
 * This class represents a 1x3 Tile hallway, that allows a character to occupy
 * the middle Tile. It also connects to two rooms.
 */

public class Hallway extends Location{
    
    private Tile[] tiles;
    private boolean isVertical;
    
    //Constructor for standard hallway
    public Hallway(HallwayType hallway) {
        super(hallway.getName());
        this.tiles = new Tile[3];
        this.isVertical = hallway.isVertical();
        //initialize Tiles
        for(int i = 0; i < 3; i++) {
            tiles[i] = new Tile();
        }
    }
    
    // Hallways can only be occupied on their middle Tile, if its occupied its
    // blocked to other players.
    public boolean isBlocked() {
        return tiles[1].isOccupied();
    }
    
    public boolean isVertical() {
        return isVertical;
    }
    
    public Tile[] getTiles() {
        return this.tiles;
    }
}