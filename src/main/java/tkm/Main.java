package tkm;
//import tkm.clientserver.*;
import tkm.clientserver.Client;
import tkm.clientserver.Server;
//import tkm.ui.*;
import tkm.ui.ChatPanel;
import tkm.ui.GamePanel;
import tkm.ui.Lobby;
import tkm.ui.PlayerOptionsPanel;
import tkm.ui.MainMenu;
import tkm.ui.CardPanel;
import tkm.ui.Lobby;
import tkm.ui.WaitingLobby;
//import tkm.enums.*;
import tkm.enums.CharacterType;
import tkm.enums.WeaponType;
import tkm.enums.RoomType;

import tkm.gamelogic.Deck;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.WatchEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main extends JFrame {

    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private ChatPanel chatPanel;
    private PlayerOptionsPanel pOptionsPanel;
    private JPanel contentPanel;
    private JPanel optionsPanel;
    private String username;
    private Server gameServer;
    private Client gameClient;
    private MurderDeck murderDeck;
    private Player currentPlayer;
    private Lobby lobby;
    private WaitingLobby waitingLobby;
    private CardPanel cardPanel;
    private Deck deck;
    private int numPlayers;
  
    //Constructor
    public Main() {
        
        mainMenu = new MainMenu();
        gamePanel = new GamePanel();
        chatPanel = new ChatPanel();
        lobby = new Lobby();
        waitingLobby = new WaitingLobby();
        pOptionsPanel = new PlayerOptionsPanel();
        optionsPanel = new JPanel();
        contentPanel = new JPanel();
        //cardPanel = new CardPanel();
        this.initializeComponents();

        // Initialize game components
        murderDeck = new MurderDeck(); // The solution deck for the game
        //currentPlayer = new Player("Player 1"); // Example current player

        /*
         *TO DO
         * Bring listener creation into their respective classes, maybe make
         * the UI stuff protected instead of private to enable access.
         */

        mainMenu.getExitGameButton().addActionListener((ActionEvent e) -> {
            this.exit(e);
        });

        mainMenu.getHostGameButton().addActionListener((ActionEvent e) -> {
            this.hostGame(e);
        });

        mainMenu.getJoinGameButton().addActionListener((ActionEvent e) -> {
            this.joinGame(e);
        });

        lobby.getStartGame().addActionListener((ActionEvent e) -> {
            this.startGame(e);
        });

        chatPanel.getSendButton().addActionListener((ActionEvent e) -> {
            this.send(e);
        });

        // Set up event listeners for player options
        setupEventListeners();
    }

    //initialize
    private void initializeComponents() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Clue-Less");

        // Setup the options panel
        optionsPanel.setLayout(new GridLayout(0, 1, 5, 20));
        optionsPanel.add(mainMenu);
        optionsPanel.add(chatPanel);
        
        chatPanel.setVisible(false);
        

        // Setup the content panel
        contentPanel.setLayout(new BorderLayout(5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 , 10));

        contentPanel.add(optionsPanel, BorderLayout.WEST);
        contentPanel.add(gamePanel, BorderLayout.CENTER);

        this.add(contentPanel);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    //Action Events for Buttons
    private void exit(ActionEvent e) {
        System.exit(0);
    }

    private void startGame(ActionEvent e) {
        //int numPlayers = Server.getPlayerCount()
        deck.deal(deck, numPlayers);
        this.switchToPOPanel();
    }
    
    private void hostGame(ActionEvent e) {
        numPlayers = 0;
        // Asks for the host's username
        username = JOptionPane.showInputDialog(this, "Enter your username:");

        /*
        TO DO
        Add check for empty username
        Way for user to back out of hostgame without making a server
        */

        if(username != null) {
            // Start the host's server on a new Thread
            gameServer = new Server();
            new Thread(gameServer).start();
            


            // Start the host's client
            gameClient = new Client("localhost", Server.PORT, username, this);
            numPlayers ++;
            new Thread(gameClient).start();

            chatPanel.setVisible(true);
            this.switchToLobbyPanel();
        }
    }

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
            numPlayers ++;


            chatPanel.setVisible(true);
            this.swithToWaitingPanel();
        }


    }

    private void send(ActionEvent e) {

        // Get input from chatInput textfield in chatPanel
        String message = chatPanel.getChatInput().getText();
        // Only send a message if its not empty
        if(message.isEmpty() == false) {
            gameClient.sendMessage("CHAT: " + username + ": " + message);
            chatPanel.getChatInput().setText("");
        }
    }

    public void updateChat(String message) {
        SwingUtilities.invokeLater(() -> chatPanel.getChatArea().append(message + "\n"));
    }

    //UI Transitions
    private void switchToLobbyPanel() {
        optionsPanel.remove(mainMenu);
        optionsPanel.add(lobby, 0);
        lobby.setVisible(true);
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void swithToWaitingPanel() {
        optionsPanel.remove(mainMenu);
        optionsPanel.add(waitingLobby, 0);
        waitingLobby.setVisible(true);
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private synchronized void switchToPOPanel() {
        optionsPanel.remove(lobby);
        optionsPanel.add(pOptionsPanel, 0);
        optionsPanel.add(cardPanel);
        cardPanel.setBackground(Color.CYAN);

        optionsPanel.revalidate();
        optionsPanel.repaint();

    }

    //Action Listeners
    private void setupEventListeners() {
        // Setting up event listeners for each player option button
        pOptionsPanel.getMoveButton().addActionListener(new MoveActionListener());
        pOptionsPanel.getSuggestButton().addActionListener(new SuggestActionListener());
        pOptionsPanel.getAccusationButton().addActionListener(new AccusationActionListener());
    }

    private class MoveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TO DO, add in player moves
            System.out.println("Move button clicked");
        }
    }

    private class SuggestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Dropdowns for suggestion selections
            JComboBox<String> suspectList = new JComboBox<>();
            for (CharacterType character : CharacterType.values()) {
                suspectList.addItem(character.getName());
            }

            JComboBox<String> weaponList = new JComboBox<>();
            for (WeaponType weapon : WeaponType.values()) {
                weaponList.addItem(weapon.getName());
            }

            JComboBox<String> roomList = new JComboBox<>();
            for (RoomType room : RoomType.values()) {
                roomList.addItem(room.getName());
            }

            // Panel layout for suggestion inputs
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Select suspect:"));
            panel.add(suspectList);
            panel.add(new JLabel("Select weapon:"));
            panel.add(weaponList);
            panel.add(new JLabel("Select room:"));
            panel.add(roomList);

            int result = JOptionPane.showConfirmDialog(null, panel, "Make a Suggestion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                // Get selected values
                String suspectName = (String) suspectList.getSelectedItem();
                String weaponName = (String) weaponList.getSelectedItem();
                String roomName = (String) roomList.getSelectedItem();

                // Create suggestion cards
                Card suspect = new Card(suspectName);
                Card weapon = new Card(weaponName);
                Card room = new Card(roomName);

                // Make the suggestion and display the result
                MakeSuggestion suggestion = new MakeSuggestion(currentPlayer, suspect, weapon, room);
                suggestion.performSuggestion();
            }
        }
    }

    private class AccusationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create dropdown lists for accusation selection
            JComboBox<String> suspectList = new JComboBox<>();
            for (CharacterType character : CharacterType.values()) {
                suspectList.addItem(character.getName());
            }

            JComboBox<String> weaponList = new JComboBox<>();
            for (WeaponType weapon : WeaponType.values()) {
                weaponList.addItem(weapon.getName());
            }

            JComboBox<String> roomList = new JComboBox<>();
            for (RoomType room : RoomType.values()) {
                roomList.addItem(room.getName());
            }

            // Panel layout for accusation inputs
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Select suspect:"));
            panel.add(suspectList);
            panel.add(new JLabel("Select weapon:"));
            panel.add(weaponList);
            panel.add(new JLabel("Select room:"));
            panel.add(roomList);

            int result = JOptionPane.showConfirmDialog(null, panel, "Make an Accusation", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve the selected values
                String suspectName = (String) suspectList.getSelectedItem();
                String weaponName = (String) weaponList.getSelectedItem();
                String roomName = (String) roomList.getSelectedItem();

                // Create accusation cards
                Card suspect = new Card(suspectName);
                Card weapon = new Card(weaponName);
                Card room = new Card(roomName);

                // Make the accusation and display the result
                MakeAccusation accusation = new MakeAccusation(currentPlayer, suspect, weapon, room);
                accusation.performAccusation(murderDeck);
            }
        }
    }

    //Main Method
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
