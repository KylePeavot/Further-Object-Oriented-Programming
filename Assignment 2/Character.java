import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
/**
 * Enumeration class Character
 * A character in the game.
 * 
 * @author Olaf Chitil & Kyle Peavot
 * @version 18/03/2018
 */
public enum Character
{
    LAURA("Laura", 0.5f, Item.SANDWICH) {
    /**
     * Does nothing as of yet
     */
        public void enterRoom(Room r)
        {}
    }, 
    SALLY("Sally", 0.5f, Item.CRISPS) {
     /**
     * Does nothing as of yet
     */
        public void enterRoom(Room r)
        {}
    }, 
    ANDY("Andy", 0.5f, Item.DRINK) {
    /**
     * Does nothing as of yet
     */
        public void enterRoom(Room r)
        {}
    }, 
    ALEX("Alex", 0.5f) {
     /**
     * Does nothing as of yet
     */
        public void enterRoom(Room r)
        {}
    },
    COOKIEMONSTER("Cookie Monster", 1.0f) {
        /**
         * takes crisps from all players in the room 
         * @param the room this character is now in
         */
        public void enterRoom(Room r)
        {
            Set<Character> charsInRoom = new HashSet<Character>(r.getChars());//create a set of all characters in the room
            for (Character character : charsInRoom) {
                if (character != this) { //if the cookie monster repeatedly tried to steal crisps from himself, it would feel wrong
                    character.take(Item.CRISPS);
                }
            }
        }
    },
    CRISPGIVER("Crisp Giver", 1.0f) { //why not just call them the cookie giver and instead of crisps, have cookies????
        /**
         * gives all characters in the same room as the crisp giver crisps
         * @param room the character is now in
         */
        public void enterRoom(Room r)
        {
            Set<Character> charsInRoom = new HashSet<Character>(r.getChars());
            for (Character character : charsInRoom) {
                if (character != this) {
                    character.receive(Item.CRISPS);
                }
            }
        }
    },
    PLAYER("The Player", 0.0f) {
        public void enterRoom(Room r)
        {}
    };

    private String description;
    private float moveProbability;
    private ArrayList<Item> itemsHeld;

    /**
     * 
     * Constructor initialising description and item.
     */
    private Character(String desc, float moveProb, Item... items)
    {
        description = desc;
        moveProbability = moveProb;
        itemsHeld = new ArrayList<Item>();
        for (Item item : items) {
            itemsHeld.add(item);
        }

    }

    /**
     * Abstract method for special interactions for characters
     * @param the room in which the special interaction is taking place
     */
    public abstract void enterRoom(Room r);
    
    /**
     * Return the description and description of item if it exists.
     */
    public String toString()
    {
        String returnText = " having the item:";
        if (itemsHeld.isEmpty()) {
            return description;
        } else { 
            for (Item item : this.itemsHeld) {
                returnText += item.toString();
            }
            return description + returnText;
        }
    }

    /**
     * Take the given item from the character if it has that item.
     * Return whether item was taken.
     * @param The item to take away.
     * @returns true if the character had the item before the call.
     */
    public boolean take(Item it)
    {
        if (itemsHeld.contains(it)) { //if the character is holding the item
            itemsHeld.remove(it); //remove it
            return true;
        } else {
            return false;
        }
    }

    /**
     * Give the item to a character
     * @param The item to be received
     * @returns true if the character can and has been given the item
     */
    public boolean receive(Item e) {
        if (e != null) { //as long as it's not nothing
            if (itemsHeld.contains(e)){ //if it's already in the collection
                return false;
            } else {
                itemsHeld.add(e);
                return true;
            }
        } else { 
            return false;
        }
    }

    /**
     * returns true if the moveProbability of a character is low enough
     * @param the probability to compare moveProbability to
     */
    public boolean automove(double d) {
        if (this.moveProbability < d) {
            return true;
        } else {
            return false;
        }
    }
    
    public ArrayList getItemsHeld() {
        return itemsHeld;
    }
}
