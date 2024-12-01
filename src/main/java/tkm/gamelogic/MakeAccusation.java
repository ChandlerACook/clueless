package tkm.gamelogic;

import javax.swing.JOptionPane;

/**
 * makeAccusation class holds logic behind making accusations.
 * created by justin - edit 11/8/2024 includes display
 */

public class MakeAccusation {

    private Player player;
    private Card suspect;
    private Card weapon;
    private Card room;

    public MakeAccusation(Player player, Card suspect, Card weapon, Card room) {
        this.player = player;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getSuspect() {
        return suspect;
    }

    public Card getWeapon() {
        return weapon;
    }

    public Card getRoom() {
        return room;
    }

    // Displays the result of the accusation.
    private void displayAccusation(boolean isCorrect) {
        String message;

        if (isCorrect) {
            message = player.getName() + " made a correct accusation! "
                    + suspect.getName() + " used the " + weapon.getName()
                    + " in the " + room.getName() + ".";
        } else {
            message = player.getName() + " made an incorrect accusation. They are eliminated!";
        }

        JOptionPane.showMessageDialog(null, message, "Accusation Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Performs the accusation logic and displays the result.
    public boolean performAccusation(Deck deck) {
        // Check if the accusation matches the solution in the murder deck
        boolean isCorrect = deck.checkAccusation(suspect, weapon, room);

        // Display the result of the accusation
        displayAccusation(isCorrect);

        return isCorrect;
    }
}
