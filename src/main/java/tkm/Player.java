package tkm;

public class Player {
    private String name;
    private String character;  // The character the player is playing as
    private List<Card> hand = new ArrayList<>(); // Player's hand of cards
    
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
    public void setCharacter(String character) {
        this.character = character;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }
}
