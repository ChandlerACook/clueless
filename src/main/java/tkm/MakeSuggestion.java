package tkm;

import javax.swing.JOptionPane;

/**
 * makeSuggestion class holds logic behind making suggestions.
 * created by justin - edit 11/8/2024 includes display
 */

public class MakeSuggestion {
    private Player player;
    private Card suspect;
    private Card weapon;
    private Card room;

    public MakeSuggestion(Player player, Card suspect, Card weapon, Card room) {
        // assigns the suggesting player and their suggestions
        this.player = player;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    // suggestion logic handling
    public void performSuggestion() {
        // for now just prints out the suggestion, add disprove later
        System.out.println(player.getName() + " suggests that it was " + 
                           suspect.getName() + " with the " + weapon.getName() + 
                           " in the " + room.getName());
        displaySuggestion();
    }
    // display the suggestion in a dialog box
    public void displaySuggestion() {
        String message = player.getName() + " suggested that "
                + suspect.getName() + " used the "
                + weapon.getName() + " in the "
                + room.getName() + ".";

        JOptionPane.showMessageDialog(null, message, "Suggestion Result", JOptionPane.INFORMATION_MESSAGE);
    }
}
