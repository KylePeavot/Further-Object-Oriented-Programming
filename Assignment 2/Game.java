import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
/**
 *  This class is the central class of the "World of London" application. 
 *  "World of London" is a very simple, text based travel game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 * @author  Michael Kölling, David J. Barnes, Olaf Chitil, and Kyle Peavot
 * @version 22/03/2018
 */

public class Game 
{
    static private final int TIME_LIMIT = 12;
    private int time;
    private Room currentRoom;
    private Room goalRoom;
    private boolean finished;

    private HashMap<Character, Room> characterInRoom; //array list of characters to the rooms they are in

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        finished = false;
        time = 0;
        characterInRoom = new HashMap<Character, Room>();
        createRooms();
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

        //initialise variables for each players' start rooms
        //makes it so that only one variable has to be changed to change start room of characters
        
        Room lauraRoom = britishLibrary;
        Room sallyRoom = oxfordStreet;
        Room andyRoom = leicesterSquare;
        Room alexRoom = trafalgarSquare;
        Room cookieMonsterRoom = soho;
        Room crispGiverRoom = britishLibrary;
        
        //initialise room exits

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

        //add characters to rooms
        lauraRoom.addCharacter(Character.LAURA);
        sallyRoom.addCharacter(Character.SALLY);
        andyRoom.addCharacter(Character.ANDY);
        alexRoom.addCharacter(Character.ALEX);
        cookieMonsterRoom.addCharacter(Character.COOKIEMONSTER);
        crispGiverRoom.addCharacter(Character.CRISPGIVER);

        //put characters in arraylist
        characterInRoom.put(Character.LAURA, lauraRoom);
        characterInRoom.put(Character.SALLY, sallyRoom);
        characterInRoom.put(Character.ANDY, andyRoom);
        characterInRoom.put(Character.ALEX, alexRoom);
        characterInRoom.put(Character.COOKIEMONSTER, cookieMonsterRoom);
        characterInRoom.put(Character.CRISPGIVER, crispGiverRoom);

        currentRoom = stPancras;  // start game at St Pancras
        goalRoom = trafalgarSquare;
    }

    /**
     * Current time is within time limit.
     */
    public boolean inTime()
    {
        return 0 <= time && time <= TIME_LIMIT;
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
        
    /**
     * move each character in a random direction
     */
    public void moveCharacters() {
        Random rand = new Random();
        float randMoveChance;
        Room room;
        for (Character character : Character.values()) {
            randMoveChance = rand.nextFloat();
            if (characterInRoom.containsKey(character)) {
                if (character.automove(randMoveChance)) { 
                    room = characterInRoom.get(character);

                    //remove character from the room they're in
                    room.removeCharacter(character);

                    //get random room
                    Room randRoom = room.getExit(room.randomExit());

                    //add char to that room
                    randRoom.addCharacter(character);

                    //update hashmap
                    characterInRoom.put(character, randRoom);
                }
            }
        }
    }
    
    /**
     * calls enterRoom function on each character
     */
    public void enterRoomAction() {
        for (Character character : Character.values()) {
            character.enterRoom(characterInRoom.get(character));
        }
    }
    
    /**
     * returns the room that the player is currently in
     */
    public Room getCurrentRoom() {
        return currentRoom;
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
     * @param direction The direction in which to go.
     * Pre-condition: direction is not null.
     */
    public String goRoom(Direction direction) 
    {
        assert direction != null : "Game.goRoom gets null direction";

        time++;

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            return "There is no exit in that direction!";
        }
        else {
            moveCharacters();
            enterRoomAction();
            
            currentRoom = nextRoom;
            String result = look();
            if (currentRoom == goalRoom) {
                result += "\nCongratulations! You reached the goal of the game.\n";
                result += quit();
            } else if (!inTime()) {
                result += "\nYou ran out of time. You have lost.\n";
                result += quit();
            }
            
            return result;
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

    /**
     * Execute look command.
     */
    public String look()
    {
        return currentRoom.getLongDescription();
    }

    /**
     * Execute take command.
     * Pre-condition: item is not null.
     */
    public String take(Item item)
    {
        assert item != null : "Game.take gets null item";
        if (currentRoom.take(item)) {
            //items.add(item);
            Character.PLAYER.receive(item);
            return "Item taken.";
        } else {
            return "Item not in this room.";
        }
    }

    /**
     * Execute eat command.
     */
    public String eat()
    {
        final Set<Item> meal = 
            new HashSet<>(Arrays.asList(Item.SANDWICH, Item.CRISPS, Item.CRISPS));

        if (Character.PLAYER.getItemsHeld().containsAll(meal)) {
            return "Congratulations! You have won.\n" + quit();
        } else {
            return "You cannot eat yet.";
        }
    }

}
