package tkm;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JPanel{
    
    private JLabel menu;
    private JButton hostGame;
    private JButton joinGame;
    private JButton exitGame;
    
    // Constructor
    public Main() {
        this.initComponents();
        
        this.add(menu);
        this.add(hostGame);
        this.add(joinGame);
        this.add(exitGame);
    }
    
    private void initComponents() {
        menu = new JLabel("Main Menu");
        hostGame = new JButton("Host Game");
        joinGame = new JButton("Join Game");
        exitGame = new JButton("ExitGame");
    }
    
    public JButton getHostGameButton() {
        return hostGame;
    }
    
    public JButton getJoinGameButton() {
        return joinGame;
    }
    
    public JButton getExitGameButton() {
        return exitGame;
    }

}
