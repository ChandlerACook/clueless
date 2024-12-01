
package tkm.ui;

import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mike
 */
public class CardPanel extends JPanel {
    
    private final String[] hand;
    private final ArrayList<JButton> cardButtons;
    
    public CardPanel(String[] playerHand) {
        for(String string : playerHand) {
            System.out.println(string);
        }
        this.hand = playerHand;
        cardButtons = new ArrayList();
        this.initComponents();
    }
    
    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(BorderFactory.createTitledBorder("Your Cards"));
        
        for(String card : hand) {
            JButton button = new JButton(card);
            cardButtons.add(button);
            this.add(button);
        }
    }
}
