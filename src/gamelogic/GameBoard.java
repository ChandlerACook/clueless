package tkm.gamelogic;

import tkm.Player;
import tkm.Card;
import tkm.enums.RoomType;
import tkm.enums.HallwayType;
import tkm.enums.CharacterType;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @file GameBoard.
 * @date 10/26/2024, updated with MakeSuggestion and MakeAccusation consolidation on 11/10/2024.
 * @authored by Mike, edited by Justin
 *
 * This class represents the game board and its current state. Keeps track of
 * all player positions, room/hallway/startingSquare states, and updates when
 * changes are made.
 */
public class GameBoard {

    private List<Player> players;
    private List<Card> caseFile;
    private Map<String, Location> locations;
    private Deck deck;  // Add Deck as an instance variable

    // Constructor now accepts players list
    public GameBoard(List<Player> players) {
        this.players = players;
        locations = new HashMap<>();
        this.createLocations();
        this.createConnections();
        this.deck = new Deck(new ArrayList<>(players));  // Pass players list to Deck constructor
    }

    // This creates all the locations on the gameboard, such as rooms, hallways, and starting squares.
    private void createLocations() {
        // Create all the rooms
        for (RoomType room : RoomType.values()) {
            locations.put(room.getName(), new Room(room));
        }

        // Create each starting character's location
        for (CharacterType character : CharacterType.values()) {
            locations.put(character.getName() + " Starting Square", new StartingSquare(character));
        }

        // Create all the hallways
        for (HallwayType hallway : HallwayType.values()) {
            locations.put(hallway.getName(), new Hallway(hallway));
        }
    }

    // Creates the location connections
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

    public Deck getDeck() {
        return deck;
    }

    public Card performDisproval(Player suggestingPlayer, Card suspect, Card weapon, Card room) {
        int startingIndex = (players.indexOf(suggestingPlayer) + 1) % players.size();

        // Go through each player in turn, starting from the player to the left
        for (int i = 0; i < players.size() - 1; i++) {
            Player playerToCheck = players.get((startingIndex + i) % players.size());

            // Check if player has any of the suggested cards
            List<Card> matchingCards = playerToCheck.getMatchingCards(suspect, weapon, room);
            if (!matchingCards.isEmpty()) {
                // If the player has more than one matching card, they select one to show
                Card cardToShow = matchingCards.get(0); // or add logic to let player choose

                // Show the card to the suggesting player (not visible to others)
                JOptionPane.showMessageDialog(null,
                        playerToCheck.getName() + " has shown " + suggestingPlayer.getName() + " a card.",
                        "Disproval", JOptionPane.INFORMATION_MESSAGE);

                // This card disproves the suggestion; return it
                return cardToShow;
            }
        }

        // No player could disprove the suggestion
        JOptionPane.showMessageDialog(null,
                "No one could disprove the suggestion. " + suggestingPlayer.getName() +
                        " may make an accusation or end their turn.",
                "Disproval Result", JOptionPane.INFORMATION_MESSAGE);

        return null; // Indicating no disproval was found
    }

    // Helper method to get matching cards from a player
    private List<Card> getMatchingCards(Player player, Card suspect, Card weapon, Card room) {
        return player.getCards().stream()
                .filter(card -> card.equals(suspect) || card.equals(weapon) || card.equals(room))
                .collect(Collectors.toList());
    }

    // Method to handle making a suggestion
    public void performSuggestion(Player player, Card suspect, Card weapon, Card room) {
        System.out.println(player.getName() + " suggests that it was " +
                suspect.getName() + " with the " + weapon.getName() +
                " in the " + room.getName());

        displaySuggestion(player, suspect, weapon, room);

        // Attempt disproval
        Card disprovingCard = performDisproval(player, suspect, weapon, room);

        // If disproving card is found, record it
        if (disprovingCard != null) {
            System.out.println("Disproving card shown: " + disprovingCard.getName());
            // Optionally, mark the card in the player's notebook or record system
        }
    }

    // Display the suggestion in a dialog box
    private void displaySuggestion(Player player, Card suspect, Card weapon, Card room) {
        String message = player.getName() + " suggested that " +
                suspect.getName() + " used the " + weapon.getName() +
                " in the " + room.getName() + ".";

        JOptionPane.showMessageDialog(null, message, "Suggestion Result", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to handle making an accusation
    public void performAccusation(Player player, Card suspect, Card weapon, Card room, Deck deck) {
        if (deck.checkAccusation(suspect, weapon, room)) {
            // Accusation is correct
            System.out.println(player.getName() + " made a correct accusation!");
            // Handle the correct accusation (game ends or continues)
        } else {
            // Accusation is incorrect
            System.out.println(player.getName() + " made an incorrect accusation.");
            // Handle the incorrect accusation (penalties or turn progression)
        }
    }

    // Display the accusation result in a dialog box
    private void displayAccusation(Player player, Card suspect, Card weapon, Card room, boolean isCorrect) {
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

    public Map<String, Location> getLocations() {
        return locations;
    }
} // end class GameBoard
