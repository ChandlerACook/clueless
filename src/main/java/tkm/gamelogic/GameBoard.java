
package tkm.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import tkm.enums.CharacterType;
import tkm.enums.RoomType;
import tkm.enums.WeaponType;

/**
 * @file GameBoard.
 * @date 10/26/2024
 * @author Mike
 * 
 * This class represents the game board, and its current state. Keeps track of
 * all player positions, room/hallway/startingSquare states, and updates when
 * changes are made.
 */
public class GameBoard {
    
    private ArrayList<Player> players;
    private ArrayList<Card> caseFile;
    private ArrayList<Card> deck;
    private ArrayList<GamePiece> pieces;
    private Map<Player, List<Card>> playerHands;
    private Map<Player, GamePiece> playerPieces;
    //private ArrayList<BufferedImage> images;
    private int currentTurn;
    private static final int TILEMAP[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0, 0,  0,  0,  0,  0, 0},
            {0, 3, 3, 3, 3, 0, 0, 0,  4,  4,  4,  4, 0, 0, 0,  5,  5,  5,  5, 0},
            {0, 3, 3, 3, 3, 0, 0, 0,  4,  4,  4,  4, 0, 2, 0,  5,  5,  5,  5, 0},
            {0, 3, 3, 3, 3, 1, 1, 1,  4,  4,  4,  4, 1, 1, 1,  5,  5,  5,  5, 0},
            {0, 3, 3, 3, 3, 0, 0, 0,  4,  4,  4,  4, 0, 0, 0,  5,  5,  5,  5, 0},
            {0, 0, 0, 1, 0, 0, 0, 0,  0,  0,  1,  0, 0, 0, 0,  0,  0,  1,  0, 0}, 
            {0, 0, 2, 1, 0, 0, 0, 0,  0,  0,  1,  0, 0, 0, 0,  0,  0,  1,  2, 0},
            {0, 0, 0, 1, 0, 0, 0, 0,  0,  0,  1,  0, 0, 0, 0,  0,  0,  1,  0, 0},
            {0, 6, 6, 6, 6, 0, 0, 0,  7,  7,  7,  7, 0, 0, 0,  8,  8,  8,  8, 0},
            {0, 6, 6, 6, 6, 0, 0, 0,  7,  7,  7,  7, 0, 0, 0,  8,  8,  8,  8, 0},
            {0, 6, 6, 6, 6, 1, 1, 1,  7,  7,  7,  7, 1, 1, 1,  8,  8,  8,  8, 0},
            {0, 6, 6, 6, 6, 0, 0, 0,  7,  7,  7,  7, 0, 0, 0,  8,  8,  8,  8, 0},
            {0, 0, 0, 1, 0, 0, 0, 0,  0,  0,  1,  0, 0, 0, 0,  0,  0,  1,  0, 0}, 
            {0, 0, 2, 1, 0, 0, 0, 0,  0,  0,  1,  0, 0, 0, 0,  0,  0,  1,  0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0,  0,  0,  1,  0, 0, 0, 0,  0,  0,  1,  0, 0},
            {0, 9, 9, 9, 9, 0, 0, 0, 10, 10, 10, 10, 0, 0, 0, 11, 11, 11, 11, 0},
            {0, 9, 9, 9, 9, 0, 0, 0, 10, 10, 10, 10, 0, 0, 0, 11, 11, 11, 11, 0},
            {0, 9, 9, 9, 9, 1, 1, 1, 10, 10, 10, 10, 1, 1, 1, 11, 11, 11, 11, 0},
            {0, 9, 9, 9, 9, 0, 2, 0, 10, 10, 10, 10, 0, 2, 0, 11, 11, 11, 11, 0},
            {0, 0, 0, 0, 0, 0, 0, 0,  0,  0,  0,  0, 0, 0, 0,  0,  0,  0,  0, 0}
        };
    
    
    public GameBoard() {
       players = new ArrayList<>();
       deck = new ArrayList<>();
       caseFile = new ArrayList<>();
       pieces = new ArrayList<>();
       
       playerHands = new HashMap<>();
       playerPieces = new HashMap<>();
       
       
       this.createGamePieces();
       this.createDeck();
    }
    
    public void startGame() {
        //Shuffle the deck
        Collections.shuffle(deck);
        this.createCaseFile();
        // Shuffle the players, in effect creating a turn order.
        Collections.shuffle(players);
        this.assignPlayerToGamePiece();
        this.dealCards();
        

        

        // for debugging
        for(Player player : players)
            System.out.println(player.toString());
    }
    
    // Adds a player/client to the GameState
    public void addPlayer(Player player) {
        players.add(player);
    }
    
    // assign players to game pieces.
    // TO DO 
    // add a randomization instead of 1-1 mapping.
    private void assignPlayerToGamePiece() {
        for(int i = 0; i < players.size(); i++) {
            playerPieces.put(players.get(i), pieces.get(i));
            players.get(i).setGamePiece(pieces.get(i));
        }
    }
    
    private void dealCards() {
        int playerIndex = players.size() - 1;
        while(!deck.isEmpty()) {
            players.get(playerIndex).addCard(deck.getFirst());
            deck.removeFirst();
            playerIndex--;
            
            if(playerIndex < 0) {
                playerIndex = players.size() - 1;
            }
        }
    }

    // Helper method that creates all the cards from enum types
    private void createDeck() {
        for(CharacterType character : CharacterType.values())
            deck.add(new Card(character.getName(), 1));
        for(WeaponType weapon : WeaponType.values())
            deck.add(new Card(weapon.getName(), 2));
        for(RoomType room : RoomType.values())
            deck.add(new Card(room.getName(), 3));
    }
    
    private void createGamePieces() {
        for(CharacterType character : CharacterType.values()) {
            pieces.add(new GamePiece(character.getX(), character.getY(), character));
        }
    }
    
    // Creates the case file by picking one of each type of card, removing that
    // card from the deck.
    private void createCaseFile() {
        caseFile.add(this.findFirstCard(1));
        caseFile.add(this.findFirstCard(2));
        caseFile.add(this.findFirstCard(3));
    }
    
    // For debug purposes, and checking correct accusations
    private String caseFileToString() {
        StringBuilder b = new StringBuilder();
        
        b.append(caseFile.get(0).getName());
        b.append(" with the "); 
        b.append(caseFile.get(1).getName());
        b.append(" in the "); 
        b.append(caseFile.get(2).getName()); 
        b.append(".");
        
        return b.toString();
    }
    
    // Helper method to find the first card of a passed in type(character, weapon, room)
    private Card findFirstCard(int type) {
        for(Card card : deck) {
            if(card.getType() == type) {
                deck.remove(card);
                return card;
            }    
        }
        return null; // something wrong happened here!
    }
    
    // Method to generate valid moves for the current player's turn
    public Set<int[]> generateValidMoves(GamePiece piece) {
        Set<int[]> validMoves = new HashSet<>();
        int x = piece.getX();
        int y = piece.getY();

        if (TILEMAP[y][x] == 1) { // Player is in a hallway
            // Check adjacent rooms to move into
            addAdjacentRooms(validMoves, x, y);
        } else if (TILEMAP[y][x] >= 3 && TILEMAP[y][x] <= 11) { // Player is in a room
            // Move to adjacent hallway if unoccupied
            addAdjacentHallways(validMoves, x, y);
            // Secret passages
            addSecretPassages(validMoves, x, y);
        } else {
           // If player is on a starting square, move to adjacent hallway
            addAdjacentHallways(validMoves, x, y);
        }

        return validMoves;
    }
    
    private void addAdjacentRooms(Set<int[]> validMoves, int x, int y) {
        // Assuming you are in a hallway, check the adjacent rooms.

        // Check above (North)
        if (y > 0 && TILEMAP[y - 2][x] >= 3 && TILEMAP[y - 2][x] <= 11 && !isOccupied(x, y - 2)) {
            validMoves.add(findFirstUnoccupiedTile(TILEMAP[y - 2][x])); // Find the first available spot in the room above.
        }

        // Check below (South)
        if (y < TILEMAP.length - 1 && TILEMAP[y + 2][x] >= 3 && TILEMAP[y + 2][x] <= 11 && !isOccupied(x, y + 2)) {
            validMoves.add(findFirstUnoccupiedTile(TILEMAP[y + 2][x])); // Find the first available spot in the room below.
        }

        // Check left (West)
        if (x > 0 && TILEMAP[y][x - 2] >= 3 && TILEMAP[y][x - 2] <= 11 && !isOccupied(x - 2, y)) {
            validMoves.add(findFirstUnoccupiedTile(TILEMAP[y][x - 2])); // Find the first available spot in the room to the left.
        }

        // Check right (East)
        if (x < TILEMAP[y].length - 1 && TILEMAP[y][x + 2] >= 3 && TILEMAP[y][x + 2] <= 11 && !isOccupied(x + 2, y)) {
            validMoves.add(findFirstUnoccupiedTile(TILEMAP[y][x + 2])); // Find the first available spot in the room to the right.
        }
}
    
    // Use BFS in order to find the nearest hallways. Add one to whatever it finds
    // to use the middle hallway.
    private void addAdjacentHallways(Set<int[]> validMoves, int x, int y) {
        int roomNumber = TILEMAP[y][x];
        boolean[][] visited = new boolean[TILEMAP.length][TILEMAP[0].length];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currX = current[0];
            int currY = current[1];

            for (int[] direction : DIRECTIONS) {
                int adjX = currX + direction[0];
                int adjY = currY + direction[1];

                if (isWithinBounds(adjX, adjY) && !visited[adjY][adjX]) {
                    int tileType = TILEMAP[adjY][adjX];

                    if (tileType == roomNumber) {
                        queue.offer(new int[]{adjX, adjY});
                    } else if (tileType == 1) {
                        // Found a hallway tile adjacent to the room
                        // Now find the next hallway tile (e.g., middle of the hallway)
                        int[] hallwayTile = findNextHallwayTile(adjX, adjY, direction);
                        if (hallwayTile != null && !isOccupied(hallwayTile[0], hallwayTile[1])) {
                            validMoves.add(hallwayTile);
                        }
                    }
                    visited[adjY][adjX] = true;
                }
            }
        }
    }

    private static final int[][] DIRECTIONS = {
        {0, -1}, // Up
        {1, 0},  // Right
        {0, 1},  // Down
        {-1, 0}  // Left
    };

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < TILEMAP[0].length && y >= 0 && y < TILEMAP.length;
    }

    // Helper method to find the next hallway tile
    private int[] findNextHallwayTile(int x, int y, int[] direction) {
        List<int[]> hallwayTiles = new ArrayList<>();
        int currX = x;
        int currY = y;

        // Collect hallway tiles along the direction
        while (isWithinBounds(currX, currY) && TILEMAP[currY][currX] == 1) {
            hallwayTiles.add(new int[]{currX, currY});
            currX += direction[0];
            currY += direction[1];
        }

        if (!hallwayTiles.isEmpty()) {
            // Choose the middle tile of the hallway
            int middleIndex = hallwayTiles.size() / 2;
            return hallwayTiles.get(middleIndex);
        }

        return null; // No valid hallway tile found
    }

    // Helper to add secret passage moves to valid moves
    private void addSecretPassages(Set<int[]> validMoves, int x, int y) {
        switch (TILEMAP[y][x]) {
            case 3 -> // Study to Kitchen
                validMoves.add(findFirstUnoccupiedTile(11));
            case 11 -> // Kitchen to Study
                validMoves.add(findFirstUnoccupiedTile(3));
            case 5 -> // Lounge to Conservatory
                validMoves.add(findFirstUnoccupiedTile(9));
            case 9 -> // Conservatory to Lounge
                validMoves.add(findFirstUnoccupiedTile(5));
            default -> {
            }
        }
    }

    // Helper to check if a specific tile is occupied
    private boolean isOccupied(int x, int y) {
        for (GamePiece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // Helper to find the first unoccupied tile in a room
    private int[] findFirstUnoccupiedTile(int roomNumber) {
        for (int y = 0; y < TILEMAP.length; y++) {
            for (int x = 0; x < TILEMAP[y].length; x++) {
                if (TILEMAP[y][x] == roomNumber && !isOccupied(x, y)) {
                    return new int[]{x, y};
                }
            }
        }
        return null; // In case no unoccupied tile is found
    }

    // Method to move a player to a new location
    public void movePlayer(Player player, int newX, int newY) {
        GamePiece piece = playerPieces.get(player);
        if (piece != null) {
            piece.setPosition(newX, newY);
        }
    }

    // Method to validate a move before it's made
    public boolean validateMove(GamePiece piece, int newX, int newY) {
        Set<int[]> validMoves = generateValidMoves(piece);
        for (int[] move : validMoves) {
            if (move[0] == newX && move[1] == newY) {
                return true;
            }
        }
        return false;
    }
    
    public int[][] getTileMap() {
        return TILEMAP;
    }
    
    public ArrayList<GamePiece> getGamePieces() {
        return pieces;
    }
    
    public GamePiece getPlayerGamePiece(Player player) {
        return playerPieces.get(player);
    }
    
    // For debugging
    private void printDeck() {
        for(Card card : deck) {
            System.out.print(card);
        }
        System.out.println();
    }
    
    public String stringTileMap() {
        StringBuilder b = new StringBuilder();
        
        for(int[] row : TILEMAP) {
            for(int col : row) {
                b.append(col);
                b.append(",");
            }
            b.append("\n");
        }
        return b.toString();
    }
    
    public String stringPieces() {
        StringBuilder b = new StringBuilder();
        
        for(GamePiece piece : pieces) {
            b.append(piece.toString());
            b.append("\n");
        }
        
        return b.toString();
    }
    
} // end class GameBoard