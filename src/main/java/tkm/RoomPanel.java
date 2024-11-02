
package tkm.ui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import tkm.enums.CharacterType;
import tkm.gamelogic.Hallway;
import tkm.gamelogic.Location;
import tkm.gamelogic.Room;
import tkm.gamelogic.StartingSquare;
import tkm.gamelogic.Tile;

/**
 * @file RoomPanel
 * @date 10/31/2024
 * @author Mike
 * 
 * This class is a JPanel that is a visual representation of a Room.java class.
 * It will show the state of the room, such as present characters and weapons, and
 * can update when changes are made.
 */

public class RoomPanel extends JPanel {
    
    // Which room does this panel represent
    private final Location location;
    
    // Constructor for standard rooms
    public RoomPanel(Room room) {
        this.location = room;
        this.setLayout(new GridLayout(4, 4));
        this.setBorder(BorderFactory.createTitledBorder(room.getName()));
        
        for (Tile[] row : room.getTiles()) {
            for (Tile tile : row) {
                TilePanel tilePanel = new TilePanel(tile);
                this.add(tilePanel);
            }
        }
    }
    
    public RoomPanel(Hallway hallway) {
        this(hallway, null);
    }
    
    // Constructor for hallways
    public RoomPanel(Hallway hallway, StartingSquare character) {
        this.location = hallway;
        this.setLayout(new GridLayout(3, 3));
        this.setBackground(Color.BLACK);
        
        Tile tiles[] = hallway.getTiles();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                /* Checks if the Hallway is vertical or horizontal
                [][x][]
                [][x][]     for vertical
                [][x][]
                
                [ ][ ][ ]
                [x][x][x]   for horizontal
                [ ][ ][ ]
                */
                if(hallway.isVertical()) {
                    if(j == 1) {
                        TilePanel tilePanel = new TilePanel(tiles[i]);
                        this.add(tilePanel);
                    } else if(character != null && character.getCharacterType() == CharacterType.KERNEL_MUSTARD 
                                && i == 1 && j == 2) {
                        this.createStartingSquare(character);
                    } else if(character != null && (character.getCharacterType() == CharacterType.PROFESSOR_PLUM 
                            || character.getCharacterType() == CharacterType.MRS_PEACOCK) 
                            && i == 1 && j == 0) {
                        this.createStartingSquare(character);
                    } else {
                        this.add(this.createBlackPanel());
                    }
                } else {
                    if(i == 1) {
                        TilePanel tilePanel = new TilePanel(tiles[i]);
                        this.add(tilePanel);
                    } else if(character != null && character.getCharacterType() == CharacterType.MISS_SCARLET 
                                && i == 0 && j == 1) {
                        this.createStartingSquare(character);
                    } else if(character != null && (character.getCharacterType() == CharacterType.MRS_WHITE 
                            || character.getCharacterType() == CharacterType.MR_GREEN) 
                            && i == 2 && j == 1) {
                        this.createStartingSquare(character);
                    } else {
                        this.add(this.createBlackPanel());
                    }
                }
            }
        }
        // Add the starting square to the room panel
//        if(character != null)
//            this.createStartingSquare(character);
    }
    
    // Helper method to create a blank black panel
    private JPanel createBlackPanel() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(Color.BLACK); // Set background to black for non-hallway tiles
        return emptyPanel;
    }
    
    private void createStartingSquare(StartingSquare square) {
        TilePanel panel = new TilePanel(square.getTile());
        this.add(panel);
    }
    
    // Will be used to update. TO DO
    public void update() {
        
    }
    
    public Location getRoom() {
        return location;
    }
    
}
