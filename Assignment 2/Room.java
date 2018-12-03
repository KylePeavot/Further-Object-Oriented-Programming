import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

/**
 * Class Room - a room in a game.
 *
 * This class is part of the "World of London" application. 
 * "World of London" is a very simple, text based travel game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling, David J. Barnes, Olaf Chitil, and Kyle Peavot
 * @version 22/03/2018
 */

public class Room 
{
    private String description;
    private HashMap<Direction, Room> exits;        // stores exits of this room.
    private Set<Character> chars;  // stores the characters that are in this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * Pre-condition: description is not null.
     */
    public Room(String description) 
    {
        assert description != null : "Room.Room has null description";
        this.description = description;
        exits = new HashMap<Direction, Room>();
        chars = new HashSet<Character>();
        sane();
    }

    /**
     * Class invariant: getShortDescription() and getLongDescription() don't return null.
     */
    public void sane()
    {
        assert getShortDescription() != null : "Room has no short description" ;
        assert getLongDescription() != null : "Room has no long description" ;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     * Pre-condition: neither direction nor neighbor are null; 
     * there is no room in given direction yet.
     */
    public void setExit(Direction direction, Room neighbor) 
    {
        assert direction != null : "Room.setExit gets null direction";
        assert neighbor != null : "Room.setExit gets null neighbor";
        assert getExit(direction) == null : "Room.setExit set for direction that has neighbor";
        sane();
        exits.put(direction, neighbor);
        sane();
        assert getExit(direction) == neighbor : "Room.setExit has wrong neighbor";
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Items: map
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + getCharacterString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<Direction> keys = exits.keySet();
        for(Direction exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     * Pre-condition: direction is not null
     */
    public Room getExit(Direction direction) 
    {
        assert direction != null : "Room.getExit has null direction";
        sane();
        return exits.get(direction);
    }

    /**
     * Add given character to the room.
     * @param c The character to add.
     * Pre-condition: character is not null.
     * Pre-condition: character is not already in the room.
     */
    public void addCharacter(Character c)
    {
        assert c != null : "Room.addCharacter has null character";
        assert !chars.contains(c) : "Room.addCharacter for existing character";
        sane();
        chars.add(c);
        sane();
    }
    
    /**
     * Remove given character to the room.
     * @param c The character to remove.
     * Pre-condition: character is not null.
     * Pre-condition: character is already in the room.
     */
    public void removeCharacter(Character c) {
        assert c != null : "Room.removeCharacter has null character";
        assert chars.contains(c) : "Room.removeCharacter for character not in room";
        sane();
        chars.remove(c);
        sane();
    }
    
    /**
     * Take given item from a character in the room.
     * @param item The item to take.
     * @return true if taking was successful, false otherwise
     * Pre-Condition: item is not null.
     */
    public boolean take(Item item)
    {
        assert item != null : "Room.take is given null item";
        sane();
        for (Character c : chars) { //go through all characters in the room
            if (c.take(item)) { //if this returns true (if the item has been taken from a character successfully)
                sane();
                return true;
            }
        }
        sane();
        return false;
    }

    /**
     * Return a string listing the characters in the room.
     */
    private String getCharacterString()
    {
        if (chars.isEmpty()) {
            return "";
        } else {
            String returnString = "\nCharacters: \n";
            for (Character c : chars) {
                returnString += " - " + c.toString() + "; \n";
            }
            return returnString;
        }
    }

    /**
     * Returns the array list chars
     */
    public Set<Character> getChars() {
        return chars;
    }

    /**
     * Returns a random exit for the room the player is currently in
     * if there are no exits, return null
     */
    public Direction randomExit() {
        if (exits.isEmpty()) { //if the room isn't a void in the middle of nowhere with no exits
            return null; 
        } else {
            Random rand = new Random();
            int random = rand.nextInt(exits.size()); //random index for direction array
            int count = 0;
            Direction[] directionArray = new Direction[exits.size()]; //new array to hold all possible directions

            for (Direction direction : exits.keySet()) { //for each exit
                directionArray[count] = direction; //add the direction of the exit to the array
                count++; 
            }

            return directionArray[random]; //return a random index of the array
        }
    }
}

