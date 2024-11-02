
package tkm.gamelogic;

import java.util.HashSet;
import java.util.Set;

/**
 * @file Location.java
 * @date 10/25/2024
 * @author Mike
 * 
 * This abstract class represents a generic location on the game board, which will be either
 * a room or a hallway. It uses a HashSet to store the connections between the 
 * locations.
 */

public abstract class Location {
    
    private String name;
    private Set<Location> connections;
    
    // Constructor
    public Location(String name) {
        this.name = name;
        this.connections = new HashSet<>();
    }
    
    // Returns the location name
    public String getName() {
        return this.name;
    }
    
    public Set<Location> getConnections() {
        return this.connections;
    }
    
    // This method takes in a location, and makes a two directional connection
    // between the passed in Location and this location.
    public void connect(Location location) {
        this.connections.add(location);
        location.getConnections().add(this);
    }
    
}
