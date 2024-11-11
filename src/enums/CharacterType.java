
package tkm.enums;

import java.awt.Color;

/**
 * @file CharacterType.java
 * @date 10/25/2024
 * @author Mike
 * 
 * This enum type represents the playable characters in Clue-less.
 */

public enum CharacterType {
    MISS_SCARLET("Miss Scarlet", Color.RED), 
    KERNEL_MUSTARD("Kernel Mustard", Color.YELLOW),
    MRS_WHITE("Mrs. White", Color.PINK), 
    MR_GREEN("Mr. Green", Color.GREEN), 
    MRS_PEACOCK("Mrs. Peacock", Color.BLUE), 
    PROFESSOR_PLUM("Professor Plum", Color.MAGENTA);
    
    private final String name;
    private final Color color;
    
    CharacterType(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Color getColor() {
        return this.color;
    }
}