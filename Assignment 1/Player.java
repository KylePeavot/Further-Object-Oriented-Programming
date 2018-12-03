import java.util.ArrayList;
/**
 * A class for the player that the user plays
 * Has an inventory
 *
 * @author (Kyle Peavot)
 * @version (15/02/2018)
 */
public class Player
{
    private ArrayList<Item> inventory; 
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        inventory = new ArrayList<Item>();
    }
    
    /**
     * Adds item to players inventory
     * @param Item item item to be added to inventory
     */
    public void addItem(Item item) 
    {
        inventory.add(item);
    }
    
    /**
     * returns players inventory as an array list
     */
    public ArrayList getInventory() 
    {
        return inventory;
    }
    
    /**
     * Prints inventory out as a string
     */
    public String inventoryToString() {
        String currentInventory = "";
        for (Item item : inventory) {
            currentInventory += item.toString() + " ";
        }
        return currentInventory;
    }
}
