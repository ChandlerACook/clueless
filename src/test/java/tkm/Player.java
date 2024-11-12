package tkm;
import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private String character;  // The character the player is playing as
    private List<Card> cards;
    private List<Player> playersList;
    //private Room currentRoom;  // The room the player is currently in

    // Initialize a player with a name and character
    public Player(String name, String character) {
        this.name = name;
        this.character = character;
        this.cards = new ArrayList<>();
        this.playersList = new ArrayList<>();
        //this.currentRoom = null; // Player starts outside any room
    }

    // Getter for the player's name
    public String getName() {
        return name;
    }

    // Getter for the player's character
    public String getCharacter() {
        return character;
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        cards.add(card);
    }

    // Get all cards in the player's hand
    public List<Card> getCards() {
        return cards;
    }

    // Get a list of cards that match the given suspect, weapon, or room
    public List<Card> getMatchingCards(Card suspect, Card weapon, Card room) {
        List<Card> matchingCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.equals(suspect) || card.equals(weapon) || card.equals(room)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

    // Get the player's hand of cards
    public List<Card> getPlayerHand() {
        return cards;
    }

    // Set the player's hand of cards
    public void setPlayerHand(List<Card> hand) {
        this.cards = hand;
    }

    

}