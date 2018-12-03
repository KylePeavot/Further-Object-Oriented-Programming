
/**
 * An item in the game.
 * There exist only a few different items.
 * @author (Kyle Peavot)
 * @version (14/02/2018)
 */
public enum Item
{
    SANDWICH("sandwich"), CRISPS("crisps"), DRINK("drink");
    
    private String description;
    
    /**
     * Constructor with parameter.
     * Pre-condition: name is not null.
     */
    private Item(String description)
    {
        assert description != null : "Direction.Direction has null name";
        this.description = description;
        assert toString().equals(description) : "Item.Item produces wrong toString";
    }
    
    /**
     * Return the direction name.
     */
    public String toString()
    {
        return description;
    }
}
