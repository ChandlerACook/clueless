package tkm;

import tkm.clientserver.Client;
import tkm.clientserver.Server;
import tkm.gamelogic.GameBoard;
import tkm.ui.ChatPanel;
import tkm.ui.GamePanel;
import tkm.ui.PlayerOptionsPanel;
import tkm.ui.MainMenu;
import tkm.ui.CardPanel;
import tkm.enums.CharacterType;
import tkm.enums.WeaponType;
import tkm.enums.RoomType;
import tkm.gamelogic.Deck;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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
    private GameBoard gameBoard;
    private CardPanel cardPanel;
    private Deck deck;
    private List<Player> playersList;
    private Player player;

    public Main() {
        // Initialize UI components
        mainMenu = new MainMenu();
        chatPanel = new ChatPanel();
        pOptionsPanel = new PlayerOptionsPanel();
        optionsPanel = new JPanel();
        contentPanel = new JPanel();

        // Initialize players and Deck
        playersList = new ArrayList<>(); // Initialize the players list
        player = new Player("Player 1", "Miss Scarlet"); // Example player
        playersList.add(player);

        // Initialize the Deck with players list
        deck = new Deck(playersList);
        deck.dealCards();
        gameBoard = new GameBoard(playersList); // Initialize GameBoard with players list
        gamePanel = new GamePanel(playersList);

        // Initialize player hand (Make sure player is initialized before accessing hand)
        List<Card> playerHandCards = player.getPlayerHand();
        if (playerHandCards != null) {
            cardPanel = new CardPanel(playerHandCards); // Pass List<Card> to CardPanel constructor
        } else {
            cardPanel = new CardPanel(new ArrayList<>()); // Default to empty list if no hand
        }

        this.initializeComponents();

        // Add action listeners
        mainMenu.getExitGameButton().addActionListener(this::exit);
        mainMenu.getHostGameButton().addActionListener(this::hostGame);
        mainMenu.getJoinGameButton().addActionListener(this::joinGame);

        chatPanel.getSendButton().addActionListener(this::send);

        setupEventListeners();
    }

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

    private void switchToPOPanel() {
        optionsPanel.remove(mainMenu);

        // Get List<Card> from the player's hand
        List<Card> playerHandCards = player.getPlayerHand(); // This returns List<Card>
        if (playerHandCards != null) {
            cardPanel = new CardPanel(playerHandCards); // Pass List<Card> to CardPanel constructor
        } else {
            cardPanel = new CardPanel(new ArrayList<>()); // Default to empty list if no hand
        }

        optionsPanel.add(pOptionsPanel, 0);
        optionsPanel.add(cardPanel);
        cardPanel.setBackground(Color.CYAN);

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void exit(ActionEvent e) {
        System.exit(0);
    }

    private void hostGame(ActionEvent e) {
        username = JOptionPane.showInputDialog(this, "Enter your username:");

        if(username != null) {
            gameServer = new Server();
            new Thread(gameServer).start();

            gameClient = new Client("localhost", Server.PORT, username, this);
            new Thread(gameClient).start();

            chatPanel.setVisible(true);
            this.switchToPOPanel();
        }
    }

    private void joinGame(ActionEvent e) {
        JTextField serverAddressField = new JTextField();
        JTextField portField = new JTextField(Integer.toString(Server.PORT));
        Object[] message = {
                "Server IP Address: ", serverAddressField,
                "Server Port: ", portField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Join Game",
                JOptionPane.OK_CANCEL_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            username = JOptionPane.showInputDialog(this, "Enter your username:");

            gameClient = new Client("localhost", Server.PORT, username, this);
            new Thread(gameClient).start();

            chatPanel.setVisible(true);
            this.switchToPOPanel();
        }
    }

    private void send(ActionEvent e) {
        String message = chatPanel.getChatInput().getText();
        if(!message.isEmpty()) {
            gameClient.sendMessage("CHAT: " + username + ": " + message);
            chatPanel.getChatInput().setText("");
        }
    }

    public void updateChat(String message) {
        SwingUtilities.invokeLater(() -> chatPanel.getChatArea().append(message + "\n"));
    }

    private void setupEventListeners() {
        pOptionsPanel.getMoveButton().addActionListener(new MoveActionListener());
        pOptionsPanel.getSuggestButton().addActionListener(new SuggestActionListener());
        pOptionsPanel.getAccusationButton().addActionListener(new AccusationActionListener());
    }

    private class MoveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Move button clicked");
            // Logic for player movement
        }
    }

    private class SuggestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Select suspect:"));
            panel.add(suspectList);
            panel.add(new JLabel("Select weapon:"));
            panel.add(weaponList);
            panel.add(new JLabel("Select room:"));
            panel.add(roomList);

            int result = JOptionPane.showConfirmDialog(null, panel, "Make a Suggestion", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // No need to convert the selected item to String, as it's already a String
                String suspectName = (String) suspectList.getSelectedItem();
                String weaponName = (String) weaponList.getSelectedItem();
                String roomName = (String) roomList.getSelectedItem();

                // Create Card objects directly from selected values
                Card suspect = new Card(suspectName);
                Card weapon = new Card(weaponName);
                Card room = new Card(roomName);

                // Perform suggestion logic
                gameBoard.performSuggestion(player, suspect, weapon, room); // GameBoard handles suggestion
            }
        }
    }

    private class AccusationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
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

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Select suspect:"));
            panel.add(suspectList);
            panel.add(new JLabel("Select weapon:"));
            panel.add(weaponList);
            panel.add(new JLabel("Select room:"));
            panel.add(roomList);

            int result = JOptionPane.showConfirmDialog(null, panel, "Make an Accusation", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // No need to convert the selected item to String, as it's already a String
                String suspectName = (String) suspectList.getSelectedItem();
                String weaponName = (String) weaponList.getSelectedItem();
                String roomName = (String) roomList.getSelectedItem();

                // Create Card objects directly from selected values
                Deck deck = new Deck(playersList);  // Pass players to Deck constructor

                // Get the suspect, weapon, and room from user input
                Card suspect = new Card(suspectName);
                Card weapon = new Card(weaponName);
                Card room = new Card(roomName);

                // Perform accusation logic by checking against the Deck
                gameBoard.performAccusation(player, suspect, weapon, room, deck); // GameBoard handles accusation
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
