
package tkm.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import tkm.gamelogic.Card;

/**
 *
 * @author Mike
 */
public class CardPanel extends JPanel {
    
    private final ArrayList<Card> hand;
    private final ArrayList<JButton> cardButtons;
    
    public CardPanel(List<Card> playerHand) {
        this.hand = (ArrayList<Card>) playerHand;
        cardButtons = new ArrayList();
        this.initComponents();
    }
    
    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Your Cards"));
        
        for(Card card : hand) {
            JButton button = new JButton(card.getName());
            cardButtons.add(button);
            this.add(button);
        }
    }
}
