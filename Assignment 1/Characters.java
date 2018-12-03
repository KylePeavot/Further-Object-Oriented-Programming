
/**
 * Characters in the game
 * Characters have a name and can have an item
 *
 * @author (Kyle Peavot)
 * @version (15/02/2018)
 */
public enum Characters
{
    LAURA("Laura", Item.SANDWICH), SALLY("Sally", Item.CRISPS), ANDY("Andy", Item.DRINK), ALEX("Alex", null);
    
    private String name;
    private Item itemHeld;
    /**
     * Constructor with two parameters.
     */
    private Characters(String name, Item itemHeld)
    {
        assert name != null : "Characters.Characters has null name";
        //no need to check if itemHelp is null as a character can hold no item
        this.name = name;
        this.itemHeld = itemHeld;
        assert (toString().equals(name + " is holding " + itemHeld) || (toString().equals(name + " is holding nothing"))): "Item.Item produces wrong toString";
    }
    /**
     * Takes item from a character 
     * If successful returns true, if not returns false 
     * @param Item item the item to be taken
     */
    public boolean take(Item item) 
    {
        if (this.itemHeld != null) {
            if (this.itemHeld == item) {
                this.itemHeld = null;
                return true;
            }
        }
        return false;
    }
    /**
     * Return the name of the character and what item they're holding
     */
    public String toString()
    {
        if (itemHeld == null) { 
            return name + " is holding nothing"; 
        } else { 
            return name + " is holding " + itemHeld; 
        }
    }
}
