
package tkm.gamelogic;

import tkm.enums.RoomType;

/**
 * @file Room.java
 * @date 10/25/2024
 * @author Mike
 * 
 * This class represents a room on the Game Board. It is made up of 3x3 Tiles, and
 * connects to two hallways. It might also directly connect to a room if it has
 * a secret passageway.
 */

public class Room extends Location{
    
    private Tile[][] tiles;
    private boolean secretPassageway;
    private RoomType type;
    
    //Constructor
    public Room(RoomType type) {
        super(type.getName());
        this.type = type;
        this.tiles = new Tile[4][4];
        
        //initialize Tiles
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile();
            }
        }
        
        this.secretPassageway = type.hasSecretPassageWay();
    }
    
    // For getting the array of all tiles
    public Tile[][] getTiles() {
        return this.tiles;
    }
    
    // for accessing a specific tile
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }
    
}