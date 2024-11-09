
package tkm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Mike
 */
public class TitlePanel extends JPanel {
    
    // SCREEN SETTINGS
    final private int originalTileSize = 10; // 16x16 tile
    final private int scale = 3;  // scale up * 3

    final private int tileSize = originalTileSize * scale; 
    final private int maxScreenCol = 20;
    final private int maxScreenRow = 20;
    final private int screenWidth = tileSize * maxScreenCol; 
    final private int screenHeight = tileSize * maxScreenRow;
    
    private final ImageIcon image;
    private final JLabel title;
    private final JLabel authors;
    
    public TitlePanel() {
        image = new ImageIcon();
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        title = new JLabel("Clue-Less");
        title.setFont(new Font("SansSerif", Font.BOLD, 60));
        title.setForeground(Color.RED);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title);
        
    }
    
}
