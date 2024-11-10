package tkm.ui;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class WaitingLobby extends JPanel {


    private JLabel waiting;

    // Constructor
    public WaitingLobby() {
        this.initComponents();

        this.setLayout(new GridLayout(0,1,5,5));

        JPanel lobbyJPanel = new JPanel(new BorderLayout());

        this.add(lobbyJPanel);
        this.add(waiting);
        
    }

    private void initComponents() {
        waiting = new JLabel("Waiting for Players...");


    }
}