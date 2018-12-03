import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
/**
 *  This class is the central class of the "World of London" application. 
 *  "World of London" (Coop Meal Deal: The Game)is a very simple, text based travel game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 * @author  Michael Kölling, David J. Barnes, Olaf Chitil, and Kyle Peavot
 * @version 15/02/2018
 */

public class Game 
{
    private Room currentRoom; //Room the player is currently in
    private boolean finished; //flag to tell if the player has finished the game or not
    private int timeLeft; //a "time" limit for the game
    private Player mainCharacter; //an object so that the player can have features such as inventory and health 
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        finished = false;
        createRooms();
        timeLeft = 12;
        mainCharacter = new Player(); 
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room trafalgarSquare, chinatown, oxfordStreet, soho, coventGarden, 
        britishMuseum, stPancras, kingsCross, britishLibrary, leicesterSquare;

        // create the rooms
        trafalgarSquare = new Room("on Trafalgar Square");
        chinatown = new Room("in Chinatown");
        oxfordStreet = new Room("on Oxford Street");
        soho = new Room("in Soho");
        coventGarden = new Room("in Covent Garden");
        britishMuseum = new Room("in the British Museum");
        stPancras = new Room("in St Pancras");
        kingsCross = new Room("in Kings Cross");
        britishLibrary = new Room("in the British Library");
        leicesterSquare = new Room("on Leicester Square");

        // initialise room exits

        kingsCross.setExit(Direction.WEST, stPancras);
        stPancras.setExit(Direction.EAST, kingsCross);
        stPancras.setExit(Direction.WEST, britishLibrary);
        britishLibrary.setExit(Direction.EAST, stPancras);
        britishLibrary.setExit(Direction.SOUTH, britishMuseum);
        britishMuseum.setExit(Direction.NORTH, britishLibrary);
        britishMuseum.setExit(Direction.WEST, oxfordStreet);
        oxfordStreet.setExit(Direction.EAST, britishMuseum);
        britishMuseum.setExit(Direction.SOUTH, coventGarden);
        coventGarden.setExit(Direction.NORTH, britishMuseum);
        oxfordStreet.setExit(Direction.SOUTH, soho);
        soho.setExit(Direction.NORTH, oxfordStreet);
        soho.setExit(Direction.SOUTH, chinatown);
        chinatown.setExit(Direction.NORTH, soho);
        chinatown.setExit(Direction.SOUTH, leicesterSquare);
        leicesterSquare.setExit(Direction.NORTH, chinatown);
        leicesterSquare.setExit(Direction.EAST, coventGarden);
        coventGarden.setExit(Direction.WEST, leicesterSquare);
        leicesterSquare.setExit(Direction.SOUTH, trafalgarSquare);
        trafalgarSquare.setExit(Direction.NORTH, leicesterSquare);
        
        /*//Add characters to rooms for a version of a real game
        leicesterSquare.addCharacter(Characters.LAURA);
        leicesterSquare.addCharacter(Characters.ALEX);
        coventGarden.addCharacter(Characters.ANDY);
        britishMuseum.addCharacter(Characters.SALLY); */
        
        //add characters to leicesterSquare for testing
       leicesterSquare.addCharacter(Characters.LAURA);
       leicesterSquare.addCharacter(Characters.ALEX);
       leicesterSquare.addCharacter(Characters.ANDY);
       leicesterSquare.addCharacter(Characters.SALLY);
       // currentRoom = stPancras;  // start game at St Pancras
        currentRoom = leicesterSquare;  //temp to test win condition 
    }

    /**
     * Return whether the game has finished or not.
     */
    public boolean finished()
    {
        return finished;
    }

    /**
     * Opening message for the player.
     */
    public String welcome()
    {
        return "\nWelcome to the World of London!\n" +
        "World of London is a new travel game.\n" +
        currentRoom.getLongDescription() + "\n";
    }

    // implementations of user commands:
    /**
     * Give some help information.
     */
    public String help() 
    {
        return "You are lost. You are alone. You wander around foggy London.\n";
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room and return its long description; otherwise return an error message.
     * If the next room is trafalgar square, user wins
     * if time runs out, user loses
     * @param direction The direction in which to go.
     * Pre-condition: direction is not null.
     */
    public String goRoom(Direction direction) 
    {
        assert direction != null : "Game.goRoom gets null direction";
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            return "There is no exit in that direction!";
        }
        else {
            if (nextRoom.getShortDescription().equals("on Trafalgar Square")) {
                finished = true;
                return "You won! You've made it to Trafalgar Square on time";
            } 
            if (timeLeft-- == 0) {
                finished = true;
                return "You lost! You ran out of time";
            } else {
                timeLeft--;
                currentRoom = nextRoom;
                return currentRoom.getLongDescription();
            }
        }
    }
    /**
     * Looks in a direction
     * prints short description of room user is "looking" into
     * then long description of currentRoom
     * @param direction The direction in which to look
     * Pre-condition: direction is not null
     */
    public String lookRoom(Direction direction) {
        assert direction != null : "Game.lookRoom gets null direction";
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            return "There is no exit in that direction! You can't look there!";
        } else {
            System.out.println("You are looking " + nextRoom.getShortDescription() + "\n");
            return currentRoom.getLongDescription();
        }
    }
    
    /**
     * Tries to take item parsed into method
     * if it can, adds that item to player's inventory and then prints player's inventory
     * if it can't, returns appropriate message
     */
    public String takeItem(Item item) {
        if (currentRoom.take(item) == true) {
            mainCharacter.addItem(item);
            System.out.println(item.toString() + " has been taken and added to your inventory!");
            return "Your Inventory: " + mainCharacter.inventoryToString();
        }
        return "Item could not be found";
    }
    
    /**
     * if player has collected all items, player can "eat" them and win the game
     * if not, return what an appropriate string saying what items haven't been found
     */
    public String eat() {
        ArrayList<Item> allItems = new ArrayList<Item>();
        ArrayList<Item> mainCharsInventory = mainCharacter.getInventory();
        String itemsLeft = "";
        for (Item item : Item.values()) { //add all items to a new ArrayList
            allItems.add(item);
        }
        for (Item item : mainCharsInventory) {//remove all items from allItems that are in the players inventory
            if (allItems.contains(item)) {
                allItems.remove(item);
            }
        }
        if (!allItems.isEmpty()) { //if allItems is not empty, then the player hasn't collected all items
            for (Item item : allItems) {
                itemsLeft += item.toString() + " ";
            }
            return "You have not finished! You still need to collect these items: " + itemsLeft;
        } else {
            finished = true;
            return "Well done! You have eaten all the food to be found in London! You Win!";
        }
    }
    
    /**
     * Execute quit command.
     */
    public String quit()
    {
        finished = true;
        return "Thank you for playing.  Good bye.";
    }
}
