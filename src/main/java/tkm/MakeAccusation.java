package tkm;

public class MakeAccusation {

    private Player player;
    private Card suspect;
    private Card weapon;
    private Card room;

    public MakeAccusation(Player player, Card suspect, Card weapon, Card room) {
        // assigns the accusing player and their accusations
        this.player = player;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    // accusation logic
    public boolean performAccusation(MurderDeck deck) {
        // basically check if the accusation matches the murder deck solution
        boolean result = deck.checkAccusation(suspect, weapon, room);
        if (result) {
            System.out.println(player.getName() + " made a correct accusation!");
        } else {
            System.out.println(player.getName() + " made an incorrect accusation.");
        }
        return result;
    }
}
