package tkm.ui;
import tkm.Player;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class CardPanel extends JPanel {

    //private JButton playerHand;
    private JLabel playerHand;
    private JLabel label;

    public CardPanel(Player player) {
        List<String> cardList = player.getPlayerHand();
        this.initComponents();
        this.setLayout(new GridLayout(0,1,5,5));
        this.add(playerHand);

        for (int i=0; i < cardList.size(); i++) {
            String indexVal = cardList.get(i);
            label = new JLabel(indexVal);
            this.add(label);
        }
        
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
