
package tkm.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import tkm.Main;
import tkm.enums.CharacterType;
import tkm.gamelogic.GamePiece;

/**
 * @file Client.java
 * @date 10/16/2024
 * @author Mike
 * 
 * This class creates a client using Java Sockets, and connects to our Server
 * Class to send and receive messages from the server.
 */

public class Client implements Runnable{
    
    private int port;
    private String serverAddress;
    private Socket socket;
    private BufferedReader incoming;    // getting updates from the server
    private PrintWriter outgoing;       // writing messages to the server
    private boolean host;
    // Possibly bad design to pass in the whole app class, maybe just chatArea?
    private Main main;                    // Reference to game so client can update
    private int[][] tileMap;
    private ArrayList<GamePiece> pieces;
    
    // Constructor, creates a socket, and connects to the server
    public Client(String serverAddress, int port, Main main, boolean host) {
        this.host = host;
        this.serverAddress = serverAddress;
        this.port = port;
        this.main = main;
        this.pieces = new ArrayList<>();
        
        // Try to connect to the server, and create input/output streams
        try {
            socket = new Socket(this.serverAddress, this.port);
            incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outgoing = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to server at: " + serverAddress + ":" 
                    + port);
        } catch(IOException e) {
            System.out.println("Could not connect to server: " 
                    + e.getMessage());
        }
    }
    
    // This controls what happens while the client is connected.
    @Override
    public void run() {
        //Updating a clients chat window, if there are any changes from the server
        try {
        
            StringBuilder messageBuffer = new StringBuilder();
            String line;
        
            while ((line = incoming.readLine()) != null) {
                messageBuffer.append(line).append("\n");

                // If we receive the end delimiter, process the full message
                if (line.contains("|END|")) {
                    String fullMessage = messageBuffer.toString();
                    messageBuffer.setLength(0); // Clear buffer after processing
                    handleServerMessage(fullMessage);
                }
            }
        } catch(IOException e) {
            System.out.println("Error communicating with server: " 
                    + e.getMessage());
        } finally {
            /*
            Ensures all sockets and streams close
            TO DO
            Add a check to see if they exist != null
            */
            try {
                socket.close();
                incoming.close();
                outgoing.close();
            } catch(IOException e) {
                System.out.println("Error closing client socket: " 
                        + e.getMessage());
            }
        }
    }
    
    // For sending a message to the Server
    public void sendMessage(String message) {
        outgoing.println(message);
    }
    
    /*
    This method is important for anything you want to change on the client's
    system based on messages recieved from the server. This would be when a different
    client moves for example. It receives a message in the form 
    "MESSAGE_TYPE: [DATA] | [DATA] |END|"
    or
    "MESSAGE_TYPE"
    */
    private void handleServerMessage(String fullMessage) {
        // Removing the "|END|" from the message
        fullMessage = fullMessage.replace("|END|", "").trim();
        //System.out.println(fullMessage);

        // Update chat if a chat message comes from the server
        if (fullMessage.contains("CHAT: ")) {
            main.updateChat(fullMessage.replace("CHAT: ", ""));
        } 
        
        // Updates the amount of players that have joined in the lobby
        else if (fullMessage.startsWith("PLAYERJOINED: ")) {
            main.updatePlayerCount(Integer.parseInt(fullMessage.substring(14)));
        }
        
        // Starts the game for non-host players, proceeds to the gamepanel
        else if (fullMessage.equals("START")) {
            main.startGameForJoinedPlayers();
        } 
        
        // This server message is used to create the starting gameboard based on the
        // server data
        else if (fullMessage.startsWith("GAMEBOARD: ")) {
            this.tileMap = this.parseTileMap(fullMessage.substring(11));
        }
        
        // This server message is used to both create the starting game piece locations
        // and when updating their locations.
        else if (fullMessage.startsWith("PIECES: ")) {
            this.pieces = (this.parseGamePieces(fullMessage.substring(8)));
        } 
        
        // This server message is used to create the client's game panel when the game
        // starts
        else if (fullMessage.equals("INITIALIZE")) {
            main.initializeGamePanel(tileMap, pieces);
        } 
        
        // This server message is used to create the client's card Panel when the
        // game starts
        else if(fullMessage.startsWith("PLAYER_CARDS:")) {
            
        }
        
        // This server message is used to present the player with possible moves
        // when they click on the move button
        else if(fullMessage.startsWith("VALID_MOVES:")) {
            // removes the message type, and the data separators |.
            String[] moves = fullMessage.substring(12).split("\\|");
            List<String> moveOptions = new ArrayList<>();
            for (String move : moves) {
                if (!move.isEmpty()) {
                    moveOptions.add(move);
                }
            }

            String selectedMove = (String) JOptionPane.showInputDialog(
                    null,
                    "Select your move:",
                    "Move Options",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    moveOptions.toArray(),
                    moveOptions.get(0)
            );

            if (selectedMove != null && !selectedMove.isEmpty()) {
                sendMessage("MOVE: " + selectedMove + "|END|");
            }
        } 
        // This server message tells the client to redraw their gamepanel to
        // represent any changes.
        else if(fullMessage.equals("REDRAW")) {
            main.redrawGamePanel(pieces);
        }
    }
    
    // Used to put the string tileMap data received from the server back
    // together again.
    private int[][] parseTileMap(String serializedTileMap) {
        String[] rows = serializedTileMap.split("\n");
        int[][] newTileMap = new int[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            String[] values = rows[i].split(",");
            newTileMap[i] = new int[values.length];
            for (int j = 0; j < values.length; j++) {
                newTileMap[i][j] = Integer.parseInt(values[j]);
            }
        }

        return newTileMap;
    }
    
    // Used to put the string representation of the gamepieces and their locations
    // back into usable data.
    private ArrayList<GamePiece> parseGamePieces(String serializedPieces) {
        ArrayList<GamePiece> jpieces = new ArrayList<>();
        String[] lines = serializedPieces.split("\n");

        for (String line : lines) {
            //System.out.println(line);
            String[] parts = line.split(",");
            
            if (parts.length == 3) {
                String characterName = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);

                 // Find the matching CharacterType
                CharacterType character = null;
                for (CharacterType type : CharacterType.values()) {
                    if (type.getName().equalsIgnoreCase(characterName)) {
                        character = type;
                        break;
                    }
                }

                if (character != null) {
                    jpieces.add(new GamePiece(x, y, character));
                } else {
                    System.err.println("Character not found for name: " + characterName);
                }
            }
        }
        return jpieces;
    }

    public boolean getHost() {
        return host;
    }
    
}