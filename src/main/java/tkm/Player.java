package tkm;

public class Player {
    private String name;
    private String character;  // The character the player is playing as
    
    // initialize a player with a name and character
    public Player(String name, String character) {
        this.name = name;
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }
}
