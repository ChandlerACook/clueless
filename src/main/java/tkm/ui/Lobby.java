// this file is responsible for the the transition panel after host presses host game, and allows the host to press start game
// if player presses join game, they have a message that says waiting for players...
package tkm.ui;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Lobby extends JPanel {

    private JButton startGame;
    private JLabel waiting;

    // Constructor
    public Lobby() {
        this.initComponents();

        this.setLayout(new GridLayout(0,1,5,5));

        JPanel lobbyJPanel = new JPanel(new BorderLayout());

        this.add(lobbyJPanel);
        this.add(startGame);
        this.add(waiting);
        
    }

    private void initComponents() {
        waiting = new JLabel("Waiting for Players...");
        startGame = new JButton("Start Game");


    }

    public JButton getStartGame() {
        return startGame;
    }
}