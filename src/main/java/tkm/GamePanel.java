package tkm;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel{

    // SCREEN SETTINGS
    final private int originalTileSize = 16; // 16x16 tile
    final private int scale = 3;  // scale up * 3

    final private int tileSize = originalTileSize * scale; // 48x48 tile
    final private int maxScreenCol = 16;
    final private int maxScreenRow = 12;
    final private int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final private int screenHeight = tileSize * maxScreenRow; // 576 pixels


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground (Color.black);
        this.setDoubleBuffered(true);
    }
}
