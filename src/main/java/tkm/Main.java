package tkm;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout()); // Main Menu Start
        window.setResizable(false);
        window.setTitle("Clue-Less");
        window.add(new JLabel("Main Menu"));
        window.add(new JButton("Start Game"));
        window.add(new JButton("Join Game"));
        window.add(new JButton("Exit Game")); // Main Menu End

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }

}
