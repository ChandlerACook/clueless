
package tkm.enums;

/**
 * @file HallwayType.java
 * @date 10/30/2024
 * @author Mike
 * 
 * This enum type represents all the hallways that exist in project Clue-Less
 */
public enum HallwayType {
    
        HALL_BILLIARD("Hall-Billiard", true),
        HALL_STUDY("Hall-Study", false),
        HALL_LOUNGE("Hall-Lounge", false),
        LIBRARY_STUDY("Library-Study", true),
        LIBRARY_BILLIARD("Library-Billiard", false),
        LIBRARY_CONSERVATORY("Library-Conservatory", true),
        DINING_LOUNGE("Dining-Lounge", true),
        DINING_BILLIARD("Dining-Billiard", false),
        DINING_KITCHEN("Dining-Kitchen", true),
        BALLROOM_BILLIARD("Ballroom-Billiard", true),
        BALLROOM_CONSERVATORY("Ballroom-Conservatory", false),
        BALLROOM_KITCHEN("Ballroom-Kitchen", false);
    
    private final String name;
    private final boolean isVertical;
        
    HallwayType(String name, boolean isVertical) {
        this.name = name;
        this.isVertical = isVertical;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isVertical() {
        return isVertical;
    }
}