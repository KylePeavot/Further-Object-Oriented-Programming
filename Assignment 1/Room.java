import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

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
 * @version 15/02/2018
 */

public class Room 
{
    private String description;
    private HashMap<Direction, Room> exits;        // stores exits of this room.
    private ArrayList<Characters> charsInRoom;     // stores the characters in the room;
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
        sane();
        
        charsInRoom = new ArrayList<Characters>();
    }
    
    /**
     * Adds a character to charsInRoom array list
     * @param charToBeAdded the character to be added to charsInRoom
     */
    public void addCharacter(Characters charToBeAdded) 
    {
        charsInRoom.add(charToBeAdded);
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
     *     Exits: north west
     *     These players are in this room:
     *     a is holding item b
     *     
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getCharsInRoom();
    }
    
    /**
     * returns all characters in a room
     * if there are no characters, returns appropriate string
     */
    public String getCharsInRoom() {
        String allCharsInRoom = "";
        if (charsInRoom != null) {
            for (Characters charTemp : charsInRoom) {
                allCharsInRoom += charTemp.toString() + "\n";
            }
            return "These characters are in this room: \n " + allCharsInRoom;
        } else {
            return "There are no characters in this room.";
        }
    }
    
    /**
     * returns ArrayList charsInRoom
     */
    public ArrayList getCharArrayList() {
        return charsInRoom;
    }
    
    /**
     * Looks at every character in the room
     * checks if they have an item
     * if so, call take() in Characters and "take" the item
     * @param Item item item to be taken
     * @return true if item taken, false if item not found in room
     */
    public Boolean take(Item item) {
        for (Characters tempChar : charsInRoom) {
            if (tempChar.take(item) == true) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
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
}

