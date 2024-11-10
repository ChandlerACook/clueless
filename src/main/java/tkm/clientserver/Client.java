
package tkm.clientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import tkm.Main;

/**
 * @file Client.java
 * @date 10/16/2024
 * @author Mike
 * 
 * This class creates a client using Java Sockets, and connects to our Server
 * Class to send and receive messages from the server.
 */

public class Client implements Runnable{
    
    //private String username;
    public static final int PORT = 5555;
    //private int port;
    private String serverAddress;
    private Socket socket;
    private BufferedReader incoming;    // getting updates from the server
    private PrintWriter outgoing;       // writing messages to the server
    // Possibly bad design to pass in the whole app class, maybe just chatArea?
    private Main main;                    // Reference to game so client can update
    
    // Constructor, creates a socket, and connects to the server
    public Client(String serverAddress, String username, Main main) {
        this.serverAddress = serverAddress;
        //this.port = port;
        //this.username = username;

        this.main = main;
        
        // Try to connect to the server, and create input/output streams
        try {
            //socket = new Socket(this.serverAddress, this.port);
            socket = new Socket(this.serverAddress, PORT);
            incoming = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outgoing = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to server at: " + serverAddress + ":" 
                    + PORT);
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
            
            // Read updates from the server
            String response;
            while((response = incoming.readLine()) != null) {
                // Update the chatArea if an update comes from the server
                main.updateChat(response);           
            }
        } catch(IOException e) {
            System.out.println("Error communicating with server: " 
                    + e.getMessage());
        } finally {
            /*
            Ensures all sockets and streans close
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
    
}