package tkm.ui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tkm.gamelogic.Player;
public class CardPanel extends JPanel {

    //private JButton playerHand;
    private JLabel panelLabel;
    private JComboBox<String> playerHandDropdown;
    private JLabel username;
    private Player player;

    public CardPanel(Player player) {
        player = this.player;
        this.initComponents(player);
        this.setLayout(new GridLayout(0,1,5,5));
        this.add(panelLabel);
        //this.add(playerHandDropdown);
        this.add(username);
    }

    private void initComponents(Player player) {
        panelLabel = new JLabel("Player Hand");
        panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelLabel.setVerticalAlignment(SwingConstants.NORTH);
        /*
        String[] cardNames = new String[player.getHand().size()];
        for (int i = 0; i < player.getHand().size(); i++) {
            cardNames[i] = player.getHand().get(i).getName(); // Get the card name from each Card object
        }

        playerHandDropdown = new JComboBox<>(cardNames); // Populate the dropdown with card names
        playerHandDropdown.setAlignmentX(CENTER_ALIGNMENT);
*/
        username = new JLabel(player.getName());
    }
/*
    private JButton getPlayerHand() {
        return playerHand;
    }
*/
}
