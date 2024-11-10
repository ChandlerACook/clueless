// created by justin 10/19/2024
package tkm.gamelogic;

// game cards
public class Card {
    private int type;
    private String name;

    // initialize the card name
    public Card(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    
    public int getType() {
        return type;
    }
}

