
package tkm.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import tkm.gamelogic.Tile;

/**
 *
 * @author Mike
 */

public class TilePanel extends JPanel{
    
    private final Tile tile;
    
    public TilePanel(Tile tile) {
        this.tile = tile;
        this.setPreferredSize(new Dimension(50, 50));
        this.setBackground(tile.getColor());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // Update the tile color and repaint
    public void updateTile() {
        this.setBackground(tile.getColor());
        repaint();
    }
    
}