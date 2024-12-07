package tkm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tkm.clientserver.Client;
import tkm.clientserver.Server;
import tkm.enums.CharacterType;
import tkm.enums.RoomType;
import tkm.enums.WeaponType;
import tkm.gamelogic.Card;
import tkm.gamelogic.Deck;
import tkm.gamelogic.GamePiece;
import tkm.gamelogic.MakeAccusation;
import tkm.gamelogic.Player;
import tkm.ui.CardPanel;
import tkm.ui.ChatPanel;
import tkm.ui.GamePanel;
import tkm.ui.MainMenu;
import tkm.ui.PlayerOptionsPanel;
import tkm.ui.StartGamePanel;
import tkm.ui.TitlePanel;
/**
 * App class serves as the main controller for the game by
 * initializing all necessary components, setting up the UI,
 * and handling key game actions through event listeners.
 */

/**
 * 11/8/2024 verison - justin
 * integration of making suggestions/accusations
 */

public class Main extends JFrame {

    private MainMenu mainMenu;
    private GamePanel gamePanel;
    private ChatPanel chatPanel;
    private PlayerOptionsPanel pOptionsPanel;
    private TitlePanel titlePanel;
    private JPanel contentPanel;
    private JPanel optionsPanel;
    private StartGamePanel startPanel;
    private CardPanel cardPanel;
    
    private String username;
    private Server gameServer;
    private Client gameClient;
    
    private Deck murderDeck;
    private Player currentPlayer;

    public Main() {
        mainMenu = new MainMenu();
        chatPanel = new ChatPanel();
        pOptionsPanel = new PlayerOptionsPanel();
        titlePanel = new TitlePanel();
        optionsPanel = new JPanel();
        contentPanel = new JPanel();
        this.initializeComponents();

        // Initialize game components
        murderDeck = new Deck(); // The solution deck for the game
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

        chatPanel.getSendButton().addActionListener((ActionEvent e) -> {
            this.send(e);
        });

    }

    // Initializes the App Window, and creates the UI.
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
        contentPanel.add(titlePanel, BorderLayout.CENTER);

        this.add(contentPanel);

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

        /*
        TO DO
        Add check for empty username
        Way for user to back out of hostgame without making a server
        */

        //if(username != null) {
            // Start the host's server on a new Thread
            gameServer = new Server(this);
            new Thread(gameServer).start();

            // Reference: https://stackoverflow.com/questions/55198388/using-public-ip-address-in-sockets
            // Start the host's client
            gameClient = new Client("0.0.0.0", Server.PORT, this, true);
            new Thread(gameClient).start();

            gamePanel = new GamePanel(gameServer.getGameBoard().getTileMap(), 
                    gameServer.getGameBoard().getGamePieces());
            
            this.createStartPanel(true);
        //}
    }

    // Join Game Button Action, allows a user to join a host's server, proceed
    // to lobby/gameboard
    private void joinGame(ActionEvent e) {
        // For creating a custom JOptionPane confirm dialog
        JTextField serverAddressField = new JTextField();
        JTextField portField = new JTextField(Integer.toString(Server.PORT));
        Object[] message = {
                "Server Public IP Address: ", serverAddressField,
                "Server Port: ", portField
        };

        // Whether the user accepts or cancels joining the server.
        int option = JOptionPane.showConfirmDialog(this, message, "Join Game",
                JOptionPane.OK_CANCEL_OPTION);

        // Player accepts joining the server
        if(option == JOptionPane.OK_OPTION) {

            /* TO DO
             * Add way for user to specify serverAddress and port from dialog box
             * Change client initialization below to use inputted fields
             * Add check for empty username
             */

            // Start player's client
            gameClient = new Client(serverAddressField.getText(), 25565, this, false);
            new Thread(gameClient).start();
            
            this.createStartPanel(false);
        }
    }
    
    // This creates the lobby area based on whether the user is the host or not
    // If they are the host, they have the capability to start the game.
    private void createStartPanel(boolean host) {
        startPanel = new StartGamePanel(host);
            
            startPanel.getStartGameButton().addActionListener((ActionEvent ev) -> {
                this.start(ev);
            });
            
        this.switchToStartGamePanel();
    }
    
    // Start Game Action, allows a host user to start the game when they feel 
    // ready to start.
    private void start(ActionEvent e) {
        //Let joined players know the game has started
        gameClient.sendMessage("START " + "|END|");
        chatPanel.setVisible(true);
        this.switchToPOPanel();
        // Set up event listeners for player options
        setupEventListeners();
        this.switchToGamePanel();
        this.gameServer.getGameBoard().startGame();
        gameClient.sendMessage("REQUEST_HAND|END|");
    }

    // Send Button Action, allows a user to send a message to the chat window
    private void send(ActionEvent e) {

        // Get input from chatInput textfield in chatPanel
        String message = chatPanel.getChatInput().getText();
        // Only send a message if its not empty
        if(message.isEmpty() == false) {
            gameClient.sendMessage("CHAT: " + message + "|END|");
            chatPanel.getChatInput().setText("");
        }
    }
    
    // This method is used to start the game for non-Host players, as they do not
    // have the option to start the game and therefore need to be triggered to 
    // move to the start of the game when the host starts. This method is called
    // when the server sends out the message.
    public void startGameForJoinedPlayers() {
        if(gameClient.getHost() == false) {
            SwingUtilities.invokeLater(() -> {
                chatPanel.setVisible(true);
                this.switchToPOPanel();
                // Set up event listeners for player options
                setupEventListeners();
                this.switchToGamePanel();
                gamePanel.repaint();
                gameClient.sendMessage("REQUEST_HAND|END|");
            });
        }
    }

    // Adds the players message to the chat area. SwingUtilities was used to ensure
    // Thread safety, and is required when using swing and multiple threads
    public void updateChat(String message) {
        SwingUtilities.invokeLater(() -> chatPanel.getChatArea().append(message + "\n"));
    }
    
    
    // This method updates the lobby panel to show the amount of connected players
    // It receives this information from the server each time someone joins.
    public void updatePlayerCount(int count) {
        SwingUtilities.invokeLater(() -> startPanel.updatePlayerCount(count));
    }
    
    // This method is used to create the starting gamepanel for clients, based
    // on the servers gameBoard data. It will always be the same in this case
    // however.
    public void initializeGamePanel(int[][] tileMap, ArrayList<GamePiece> pieces) {
        gamePanel = new GamePanel(tileMap, pieces);
    }
    
    // This method is called on the the user's client to create their Card Panel
    // which represents their held hand.
    public void createCardPanel(String[] cards) {
        cardPanel = new CardPanel(cards);
        SwingUtilities.invokeLater(() -> {
            this.add(cardPanel, BorderLayout.SOUTH);
            this.revalidate();
            this.repaint();
        });
    }

    // This method is used to update the UI, in this
    // case proceeding to the lobby.
    private void switchToStartGamePanel() {
        optionsPanel.remove(mainMenu);
        optionsPanel.add(startPanel, 0);
        
        optionsPanel.revalidate();
        optionsPanel.repaint();
    }
    
    // Switches from the main menu panel to the player options panel
    private void switchToPOPanel() {
        optionsPanel.remove(startPanel);
        optionsPanel.add(pOptionsPanel, 0);

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }
    
    // Switches from the title screen to the gameboard screen
    private void switchToGamePanel() {
        contentPanel.remove(titlePanel);
        contentPanel.add(gamePanel, BorderLayout.CENTER);
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    // This method is used to redraw the gamePanel anytime there is a change in
    // player/weapon location. TO-DO add weapon locations
    public void redrawGamePanel(ArrayList<GamePiece> pieces) {
        SwingUtilities.invokeLater(() -> {
            this.gamePanel.setGamePieces(pieces);
            gamePanel.revalidate();
            gamePanel.repaint();
        });
    }
    
    public PlayerOptionsPanel getOptionsPanel() {
        return this.pOptionsPanel;
    }

    private void setupEventListeners() {
        // Setting up event listeners for each player option button
        pOptionsPanel.getMoveButton().addActionListener(new MoveActionListener());
        pOptionsPanel.getSuggestButton().addActionListener(new SuggestActionListener());
        pOptionsPanel.getAccusationButton().addActionListener(new AccusationActionListener());
        pOptionsPanel.getEndTurnButton().addActionListener(new EndTurnActionListener());
    }
    
    // Action Listener for the Move button
    private class MoveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TO DO, add in player moves
            System.out.println("Move button clicked");
            if (gameClient != null) {
                gameClient.sendMessage("REQUEST_MOVES|END|");
            }
        }
    }
    
    /**
     * Justin's Code for Suggestions and Accusations
     * 
     * need to me implemented with server/client messaging.
     */

    // Action Listener for the Suggest button
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
                
                StringBuilder suggestion = new StringBuilder("SUGGESTION: ");
                
                // Get selected values
                suggestion.append((String) suspectList.getSelectedItem()).append("|");
                suggestion.append((String) weaponList.getSelectedItem()).append("|");
                suggestion.append((String) roomList.getSelectedItem()).append("|");

                suggestion.append("|END|");
                
                gameClient.sendMessage(suggestion.toString());
            }
        }
    }

    // Action Listener for the Accusation button
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
                Card suspect = new Card(suspectName, 1);
                Card weapon = new Card(weaponName, 2);
                Card room = new Card(roomName, 3);

                // Make the accusation and display the result
                MakeAccusation accusation = new MakeAccusation(currentPlayer, suspect, weapon, room);
                accusation.performAccusation(murderDeck);
            }
        }
    }
    
    // Action Listener for the End Turn button
    private class EndTurnActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pOptionsPanel.enableSwitch(false);
            gameClient.sendMessage("END_TURN|END|");
        }
    }
    
    // Starts up the Clue-Less Application
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}