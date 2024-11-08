package tkm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App extends JFrame {
    
    private MainMenu main;
    private GamePanel gamePanel;
    private ChatPanel chatPanel;
    private String username;
    private Server gameServer;
    private Client gameClient;
    
    public App() {
        main = new MainMenu();
        gamePanel = new GamePanel();
        chatPanel = new ChatPanel();
        this.initializeComponents();

        /*
        *TO DO
        * Bring listener creation into their respective classes, maybe make
        * the UI stuff protected instead of private to enable access.
        */
        
        main.getExitGameButton().addActionListener((ActionEvent e) -> {
            this.exit(e);
        });
        
        main.getHostGameButton().addActionListener((ActionEvent e) -> {
            this.hostGame(e);
        });
        
        main.getJoinGameButton().addActionListener((ActionEvent e) -> {
            this.joinGame(e);
        });
        
        chatPanel.getSendButton().addActionListener((ActionEvent e) -> {
            this.send(e);
        });
    }
    
    // Initializes the App Window, and creates the UI.
    private void initializeComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*
        TO DO
        Try borderlayout as a quick fix to make prettier?
        */
        this.setLayout(new FlowLayout()); // Main Menu Start
        this.setResizable(false);
        this.setTitle("Clue-Less");
        
        this.add(main);
        /*
        TO DO
        Add the Game Panel back in
        */
        //this.add(gamePanel);
        this.add(chatPanel);
        
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    // Exit Button Action, exits from application
    private void exit(ActionEvent e) {
        System.exit(0);
    }
    
    // Host Game Button Action, sets up a server and a client for the host, and
    // should proceed to the game lobby or panel.
    private void hostGame(ActionEvent e) {
        // Asks for the host's username
        username = JOptionPane.showInputDialog(this, "Enter your username:");
        
        /*
        TO DO
        Add check for empty username
        */
        
        // Start the host's server on a new Thread
        gameServer = new Server();
        new Thread(gameServer).start();
        
        // Start the host's client
        gameClient = new Client("localhost", Server.PORT, username, this);
        new Thread(gameClient).start();
    }
    
    // Join Game Button Action, allows a user to join a host's server, proceed
    // to lobby/gameboard
    private void joinGame(ActionEvent e) {
        // For creating a custom JOptionPane confirm dialog
        JTextField serverAddressField = new JTextField();
        JTextField portField = new JTextField(Integer.toString(Server.PORT));
        Object[] message = {
            "Server IP Address: ", serverAddressField,
            "Server Port: ", portField
        };
        
        // Whether the user accepts or cancels joining the server.
        int option = JOptionPane.showConfirmDialog(this, message, "Join Game", 
                JOptionPane.OK_CANCEL_OPTION);
        
        // Player accepts joining the server
        if(option == JOptionPane.OK_OPTION) {
            // Asks for the player's username
            username = JOptionPane.showInputDialog(this, "Enter your username:");

            /* TO DO
            * Add way for user to specify serverAddress and port from dialog box
            * Change client initialization below to use inputted fields
            * Add check for empty username
            */
            
            // Start player's client
            gameClient = new Client("localhost", Server.PORT, username, this);
            new Thread(gameClient).start(); 
        }
        
        
    }
    
    // Send Button Action, allows a user to send a message to the chat window
    private void send(ActionEvent e) {
        
        // Get input from chatInput textfield in chatPanel
        String message = chatPanel.getChatInput().getText();
        // Only send a message if its not empty
        if(message.isEmpty() == false) {
            gameClient.sendMessage("CHAT: " + username + ": " + message);
            chatPanel.getChatInput().setText("");
        }
    }
    
    // Adds the players message to the chat area. SwingUtilities was used to ensure
    // Thread safety, but I do not know if it is necessary in this case
    public void updateChat(String message) {
        SwingUtilities.invokeLater(() -> chatPanel.getChatArea().append(message + "\n"));
    }
    
    // Starts up the Clue-Less Application
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
