
package tkm.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

import javax.swing.JOptionPane;

import tkm.gamelogic.GamePiece;
import tkm.gamelogic.Player;

/**
 * @file ClientHandler.java
 * @date 10/19/2024
 * @author Mike
 * 
 * This class helps the server, by handling the logic of communicating with
 * connected clients.
 */

public class ClientHandler implements Runnable{
    
    private Socket clientSocket;        // socket of the client
    private BufferedReader incoming;    // Handles incoming information from client
    private PrintWriter outgoing;       // Handles sending info to client
    private Server server;              // server that uses this Handler
    private Player player;
    private String username;
    private boolean host;
    
    public ClientHandler(Socket clientSocket, Server server, boolean host) {
        this.host = host;
        this.clientSocket = clientSocket;
        /**
         * TO DO
         * add a variable to keep track of the current gamestate
         */
        this.server = server;
        //this.username = username;
    }
    
    @Override
    public void run() {
        try {
            // Reads incoming data from the client
            incoming = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Outputs data from the server to the client
            outgoing = new PrintWriter(clientSocket.getOutputStream(), true);
            
            /** TO DO
            *
            * Logic to handle clients gameState changes
            * More Message types for the client handler to manage, (MOVE, SUGGEST)
            * 
            */
            
            // Asks for the host's username
            username = JOptionPane.showInputDialog(null, "Enter your username:");
            //added an int value to players added.  server stores the running int, but can be adapted.
            player = new Player(server.addPlayer(), username);
            
            server.getGameBoard().addPlayer(player);
            
            // Updates the player count when a player joins and sends them the starting
            // state
            server.broadcast("PLAYERJOINED: " + server.getClientListSize() + "|END|");
            server.broadcast("GAMEBOARD: " + server.getGameBoard().stringTileMap() + "|END|", this);
            server.broadcast("PIECES: " + server.getGameBoard().stringPieces() + "|END|", this);
            server.broadcast("INITIALIZE" + "|END|", this);
            /*
            This reads any messages that are incoming from the client
            */

            StringBuilder messageBuffer = new StringBuilder();
            String line;
        
            while ((line = incoming.readLine()) != null) {
                messageBuffer.append(line).append("\n");

                // If we receive the end delimiter, process the full message
                if (line.contains("|END|")) {
                    String fullMessage = messageBuffer.toString();
                    messageBuffer.setLength(0); // Clear buffer after processing
                    handleClientMessage(fullMessage);
                }
            }
            
        } catch(IOException e) {
            System.out.println("Client handler encounterd an issue: " 
                    + e.getMessage());
        } finally {
            /*
            Ensure that sockets and streams close
            */
            try {
                clientSocket.close();
                incoming.close();
                outgoing.close();
            } catch(IOException e) {
                System.out.println("Could not close client socket: " 
                        + e.getMessage());
            }
        }
    }
    
    // For sending a message from the server to this client being handled
    public void sendMessage(String message) {
        outgoing.println(message);
    }
    
    private void handleClientMessage(String fullMessage) {
        // Removing the "|END|" from the message
        //fullMessage = fullMessage.replace("|END|", "").trim();
        System.out.println(fullMessage);

        // Update chat if a chat message comes from the server
        if (fullMessage.startsWith("CHAT: ")) {
            // trims CHAT: from message
            server.broadcast(username + ": " + fullMessage); // Broadcast chat messages to all clients
        } else if (fullMessage.contains("START")) {
            if(this.host)
                server.broadcast(fullMessage);
        } else if(fullMessage.contains("REQUEST_MOVES")) {
            GamePiece piece = server.getGameBoard().getPlayerGamePiece(player);
            Set<int[]> validMoves = server.getGameBoard().generateValidMoves(piece);
            StringBuilder movesMessage = new StringBuilder("VALID_MOVES:");

            for (int[] move : validMoves) {
                movesMessage.append(move[0]).append(",").append(move[1]).append("|");
            }
            movesMessage.append("|END|");
            sendMessage(movesMessage.toString());
            
        } else if(fullMessage.startsWith("MOVE: ")) {
            this.handleMove(fullMessage);
        } else {
            outgoing.println("Unknown command: " + fullMessage);
        }
    }
    
    private void handleMove(String message) {
        //System.out.println(message);
        message = message.replace("MOVE: ", "").replace("|END|", "").trim();
        String[] coordinates = message.split(",");
        if (coordinates.length == 2) {
            try {
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                // Move the player on the game board
                server.getGameBoard().movePlayer(player, x, y);
                server.broadcast("PIECES: " + server.getGameBoard().stringPieces() + "|END|");
                server.broadcast("REDRAW|END|");
            } catch (NumberFormatException e) {
                System.out.println("Invalid move coordinates from client: " + message);
            }
        }
    }
}