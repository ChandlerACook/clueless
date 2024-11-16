package tkm.gamelogic;

import tkm.enums.RoomType;
import tkm.enums.HallwayType;
import tkm.Player;
import tkm.enums.CharacterType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tkm.Card;

/**
 * @file GameBoard.
 * @date 10/26/2024
 * @author Mike
 * 
 * This class represents the game board, and its current state. Keeps track of
 * all player positions, room/hallway/startingSquare states, and updates when
 * changes are made.
 */
public class GameBoard {
    
    //private List<Player> players;
    //private List<Card> caseFile;
    private Map<String, Location> locations;
    private List<Player> players = new ArrayList<>();
    private List<String> availableCharacters = Arrays.asList(
        "Miss Scarlet", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum"
    );
    private List<Card> roomCards;   // Initialize with room cards
    private List<Card> weaponCards; // Initialize with weapon cards
    private List<Card> characterCards; // Initialize with character cards
    
    
   public GameBoard() {
       locations = new HashMap<>();
       this.createLocations();
       this.createConnections();
   }
   
   // This creates all the locations on the gameboard, such as rooms, hallways and starting squares.
   private void createLocations() {
       // Create all the rooms
        for(RoomType room : RoomType.values()) {
            locations.put(room.getName(), new Room(room));
        }
        
        // Create each starting character's location
        for(CharacterType character : CharacterType.values()) {
           locations.put(character.getName() + " Starting Square" , new StartingSquare(character));
        }
       
        // Create all the hallways
        for(HallwayType hallway: HallwayType.values()) {
            locations.put(hallway.getName(), new Hallway(hallway));
        }
        
   }
   
   // Creates the location connections
   // TO DO change bidirectional connections with starting squares and hallways
   // to one directional
    private void createConnections() {
        
        // Creates bidirectional connections with all hallways and rooms.
        connectLocations("Hall-Study", RoomType.HALL.getName(), RoomType.STUDY.getName());
        connectLocations("Hall-Billiard", RoomType.HALL.getName(), RoomType.BILLIARD_ROOM.getName());
        connectLocations("Hall-Lounge", RoomType.HALL.getName(), RoomType.LOUNGE.getName(), "Miss Scarlet Starting Square");
        
        connectLocations("Library-Study", RoomType.LIBRARY.getName(), RoomType.STUDY.getName(), "Professor Plum Starting Square");
        connectLocations("Library-Billiard", RoomType.LIBRARY.getName(), RoomType.BILLIARD_ROOM.getName());
        connectLocations("Library-Conservatory", RoomType.LIBRARY.getName(), RoomType.CONSERVATORY.getName(), "Mrs. Peacock Starting Square");
       
        connectLocations("Dining-Lounge", RoomType.DINING_ROOM.getName(), RoomType.LOUNGE.getName(), "Kernel Mustard Starting Square");
        connectLocations("Dining-Billiard", RoomType.DINING_ROOM.getName(), RoomType.BILLIARD_ROOM.getName());
        connectLocations("Dining-Kitchen", RoomType.DINING_ROOM.getName(), RoomType.KITCHEN.getName());
        
        connectLocations("Ballroom-Conservatory", RoomType.BALLROOM.getName(), RoomType.CONSERVATORY.getName(), "Mr. Green Starting Square");
        connectLocations("Ballroom-Billiard", RoomType.BALLROOM.getName(), RoomType.BILLIARD_ROOM.getName());
        connectLocations("Ballroom-Kitchen", RoomType.BALLROOM.getName(), RoomType.KITCHEN.getName(), "Mrs. White Starting Square");
        
        // Create secretPassageway connections, Study-Kitchen, Lounge-Conservatory
        connectLocations(RoomType.STUDY.getName(), RoomType.KITCHEN.getName());
        connectLocations(RoomType.LOUNGE.getName(), RoomType.CONSERVATORY.getName());
        
   } // end createConnections
   
    // Helper method to connect multiple locations
    private void connectLocations(String locationName, String... connectedLocationNames) {
        Location location = locations.get(locationName);

        if (location != null) {
            for (String connectedLocationName : connectedLocationNames) {
                Location connectedLocation = locations.get(connectedLocationName);
                if (connectedLocation != null) {
                    location.connect(connectedLocation);
                }
            }
        } else {
            System.out.println("Location not found for name: " + locationName);
        }
    }
    
    public Map<String, Location> getLocations() {
        return locations;
    }

    public void startGame() {
        assignCharacters();
        assignHands();
    }

    private void assignCharacters() {
        Collections.shuffle(availableCharacters);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setCharacter(availableCharacters.get(i));
        }
    }

    private void assignHands() {
        List<Card> deck = createDeck();
        Collections.shuffle(deck);

        int cardsPerPlayer = deck.size() / players.size();
        for (int i = 0; i < players.size(); i++) {
            List<Card> hand = deck.subList(i * cardsPerPlayer, (i + 1) * cardsPerPlayer);
            players.get(i).setHand(new ArrayList<>(hand));
        }
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        deck.addAll(roomCards);
        deck.addAll(weaponCards);
        deck.addAll(characterCards);
        return deck;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
} // end class GameBoard
