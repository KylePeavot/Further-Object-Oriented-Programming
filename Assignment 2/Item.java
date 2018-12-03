import java.util.ArrayList;
/**
 * An item in the game.
 * 
 * @author Olaf Chitil
 * @version 2/2/2018
 */
public enum Item
{
    SANDWICH("sandwich"), CRISPS("crisps"), DRINK("drink");    

    private String description;    

    /**
     * Constructor with description and weight.
     * Pre-condition: description not null.
     */
    private Item(String description)
    {
        assert description != null : "Item.Item with null description";
        this.description = description;
        sane();
    }
    
    /**
     * Invariant:
     * toString never returns null.
     */
    public void sane()
    {
        assert toString() != null : "Item invariant broken: toString() returns null";
    }
    
    /** 
     * Return description.
     */
    public String toString() 
    {
        return description;
    }
}
