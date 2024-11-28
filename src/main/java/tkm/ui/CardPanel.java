package tkm.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CardPanel extends JPanel {

    //private JButton playerHand;
    private JLabel playerHand;

    public CardPanel() {
        this.initComponents();
        this.setLayout(new GridLayout(0,1,5,5));
        this.add(playerHand);
    }

    private void initComponents() {
        playerHand = new JLabel("Player Hand");
        playerHand.setHorizontalAlignment(SwingConstants.CENTER);
        playerHand.setVerticalAlignment(SwingConstants.NORTH);
    }
/*
    private JButton getPlayerHand() {
        return playerHand;
    }
*/
}
