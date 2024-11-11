package tkm.ui;

import tkm.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CardPanel extends JPanel {

    private JComboBox<String> playerHandDropdown;

    // Constructor that accepts List<Card> as parameter
    public CardPanel(List<Card> playerHand) {
        this.initComponents(playerHand); // Initialize components using the player's hand
        this.setLayout(new GridLayout(0, 1, 5, 5));
        this.add(new JLabel("Player Hand", SwingConstants.CENTER));
        this.add(playerHandDropdown);
    }

    // Initializes the components (Dropdown list for player hand cards)
    private void initComponents(List<Card> playerHand) {
        String[] cardNames = new String[playerHand.size()];
        for (int i = 0; i < playerHand.size(); i++) {
            cardNames[i] = playerHand.get(i).getName(); // Get the card name from each Card object
        }

        playerHandDropdown = new JComboBox<>(cardNames); // Populate the dropdown with card names
        playerHandDropdown.setAlignmentX(CENTER_ALIGNMENT);
    }
}
