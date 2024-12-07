package tkm.gamelogic;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @file SolutionPanel.java
 * @date 11/30/2024
 * @author justin
 *
 * This panel displays the correct solution of the game for
 * presentation purposes
 */

public class SolutionPanel extends JPanel {

    // Constructor that accepts the solution details
    public SolutionPanel(Card correctSuspect, Card correctWeapon, Card correctRoom) {
        // Set layout with minimal spacing
        this.setLayout(new GridLayout(0, 1, 5, 1));

        // Create and add labels with solution details
        this.add(new JLabel("Suspect: " + correctSuspect.getName())); // Assuming Card has a getName() method
        this.add(new JLabel("Weapon: " + correctWeapon.getName()));
        this.add(new JLabel("Room: " + correctRoom.getName()));
    }
}
