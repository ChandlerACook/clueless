
package tkm.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    
    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        /**
         * TO DO
         * add a variable to keep track of the current gamestate
         */
        this.server = server;
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
            
            /*
            This reads any messages that are incoming from the client
            */
            String message;
            while ((message = incoming.readLine()) != null) {               
                System.out.println("Received from client: " + message);
                
                // Check for correct messages, currently only "CHAT: " can come
                // in
                if (message.startsWith("CHAT: ")) {
                    // trims CHAT: from message
                    server.broadcast(message.substring(6)); // Broadcast chat messages to all clients
                } else {
                    outgoing.println("Unknown command: " + message);
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
}