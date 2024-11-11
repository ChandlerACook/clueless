package tkm.ui;

import javax.swing.*;
import java.awt.*;

public class CharacterPanel extends JPanel {
    private JLabel characterLabel;

    public CharacterPanel() {
        setLayout(new BorderLayout());
        characterLabel = new JLabel("Assigned Character: None", JLabel.CENTER);
        add(characterLabel, BorderLayout.CENTER);
    }

    // Method to update the displayed character
    public void updateCharacter(String characterName) {
        characterLabel.setText("Assigned Character: " + characterName);
    }
}

