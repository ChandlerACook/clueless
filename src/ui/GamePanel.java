package tkm.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import tkm.gamelogic.GameBoard;
import tkm.gamelogic.Hallway;
import tkm.gamelogic.Location;
import tkm.gamelogic.Room;
import tkm.gamelogic.StartingSquare;
import tkm.Player;

/**
 * @file GamePanel.java
 * @date 10/31/2024
 * @author Chandler
 * @edited Mike Snelling - 10/31/2024
 *
 * This class is a JPanel that visually represents the current state of the board
 * for a Clue-Less game.
 */

public class GamePanel extends JPanel{

    // SCREEN SETTINGS
    final private int originalTileSize = 16; // 16x16 tile
    final private int scale = 3;  // scale up * 3

    final private int tileSize = originalTileSize * scale; // 48x48 tile
    final private int maxScreenCol = 16;
    final private int maxScreenRow = 12;
    final private int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final private int screenHeight = tileSize * maxScreenRow; // 576 pixels

    private List<Player> players; // This should match your player list from the main game controller
    private final GameBoard gameBoard; // Game board reference

    /**
     * Constructor
     * Initializes the game board and sets up the UI for the game state.
     */
    public GamePanel(List<Player> players) {
        // Ensure we are passing the correct list of players to the GameBoard
        this.players = players;  // Assign the passed players list to the class variable
        this.gameBoard = new GameBoard(players); // Pass players list to GameBoard constructor

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontal and vertical space
        gbc.weightx = 1.0; // Give equal weight for horizontal resizing
        gbc.weighty = 1.0; // Give equal weight for vertical resizing
        gbc.insets = new Insets(5, 5, 5, 5); // Optional padding between components

        // Access the locations from GameBoard to add to the UI
        Map<String, Location> locations = gameBoard.getLocations();

        // Add rooms to the panel
        gbc.gridx = 1; gbc.gridy = 1;
        this.add(new RoomPanel((Room) locations.get("Study")), gbc);
        gbc.gridx = 3; gbc.gridy = 1;
        this.add(new RoomPanel((Room) locations.get("Hall")), gbc);
        gbc.gridx = 5; gbc.gridy = 1;
        this.add(new RoomPanel((Room) locations.get("Lounge")), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        this.add(new RoomPanel((Room) locations.get("Library")), gbc);
        gbc.gridx = 3; gbc.gridy = 3;
        this.add(new RoomPanel((Room) locations.get("Billiard Room")), gbc);
        gbc.gridx = 5; gbc.gridy = 3;
        this.add(new RoomPanel((Room) locations.get("Dining Room")), gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        this.add(new RoomPanel((Room) locations.get("Conservatory")), gbc);
        gbc.gridx = 3; gbc.gridy = 5;
        this.add(new RoomPanel((Room) locations.get("Ballroom")), gbc);
        gbc.gridx = 5; gbc.gridy = 5;
        this.add(new RoomPanel((Room) locations.get("Kitchen")), gbc);

        // Add hallways to the panel
        gbc.gridx = 2; gbc.gridy = 1;
        this.add(new RoomPanel((Hallway) locations.get("Hall-Study")), gbc);
        gbc.gridx = 3; gbc.gridy = 2;
        this.add(new RoomPanel((Hallway) locations.get("Hall-Billiard")), gbc);
        gbc.gridx = 4; gbc.gridy = 1;
        this.add(new RoomPanel((Hallway) locations.get("Hall-Lounge"),
                (StartingSquare)locations.get("Miss Scarlet Starting Square")), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        this.add(new RoomPanel((Hallway) locations.get("Library-Study"),
                (StartingSquare)locations.get("Professor Plum Starting Square")), gbc);
        gbc.gridx = 2; gbc.gridy = 3;
        this.add(new RoomPanel((Hallway) locations.get("Library-Billiard")), gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        this.add(new RoomPanel((Hallway) locations.get("Library-Conservatory"),
                (StartingSquare)locations.get("Mrs. Peacock Starting Square")), gbc);

        gbc.gridx = 5; gbc.gridy = 2;
        this.add(new RoomPanel((Hallway) locations.get("Dining-Lounge"),
                (StartingSquare)locations.get("Kernel Mustard Starting Square")), gbc);
        gbc.gridx = 4; gbc.gridy = 3;
        this.add(new RoomPanel((Hallway) locations.get("Dining-Billiard")), gbc);
        gbc.gridx = 5; gbc.gridy = 4;
        this.add(new RoomPanel((Hallway) locations.get("Dining-Kitchen")), gbc);

        gbc.gridx = 2; gbc.gridy = 5;
        this.add(new RoomPanel((Hallway) locations.get("Ballroom-Conservatory"),
                (StartingSquare)locations.get("Mr. Green Starting Square")), gbc);
        gbc.gridx = 3; gbc.gridy = 4;
        this.add(new RoomPanel((Hallway) locations.get("Ballroom-Billiard")), gbc);
        gbc.gridx = 4; gbc.gridy = 5;
        this.add(new RoomPanel((Hallway) locations.get("Ballroom-Kitchen"),
                (StartingSquare)locations.get("Mrs. White Starting Square")), gbc);
    } // end of constructor
}
