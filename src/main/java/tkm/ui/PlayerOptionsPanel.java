
package tkm.ui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @file PlayerOptionsPanel.java
 * @date 10/20/2024
 * @author Mike
 * 
 * This is a UI panel that has buttons for player options during the game. They are
 * "Move", "Suggest", "Accusation".
 * 
 * TO DO
 * Add Exit Game, and View Rules
 */
public class PlayerOptionsPanel extends JPanel {
    
    private JButton move;
    private JButton suggest;
    private JButton accuse;
    private JButton endTurn;
    
    public PlayerOptionsPanel() {
        this.initComponents();
        
        this.setLayout(new GridLayout(0, 1, 5, 5));
        
        this.add(move);
        this.add(suggest);
        this.add(accuse);
        this.add(endTurn);
    }
    
    private void initComponents() {
        move = new JButton("Move Character");
        suggest = new JButton("Make a Suggestion");
        accuse = new JButton("Make Accusation");
        endTurn = new JButton("End Turn");
        this.enableSwitch(false);
    }
    
    public JButton getMoveButton() {
        return move;
    }
    
    public JButton getSuggestButton() {
        return suggest;
    }
    
    public JButton getAccusationButton() {
        return accuse;
    }
    
    public JButton getEndTurnButton() {
        return endTurn;
    }
    
    // This method is used to enable or disable the components. Used with player
    // turns to enable or disable the components. Should only enable when its 
    // the players turn.
    public void enableSwitch(boolean activate) {
            move.setEnabled(activate);
            suggest.setEnabled(activate);
            accuse.setEnabled(activate);
            endTurn.setEnabled(activate);
    }
}