import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * Each room holds an arrayList roomItems to store Objects,charachters(AKA. NPCs)
 * 
* @author (Raihan Kamal)
 * @version 2021.12.03
 */

public class Room 
{
    private String description;
    private String shortDescription;
    private static String x;
    private HashMap<String, Room> exits;// stores exits of this room.
    private ArrayList<String> roomItems = new ArrayList<String>();//stores items in the room
    private ArrayList<String> NPCs = new ArrayList<String>();

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */

    public Room(String description, String shortDescription) 
    {
        this.description = description;
        this.shortDescription=shortDescription;

        exits = new HashMap<>();
        roomItems= new ArrayList<>();
        NPCs= new ArrayList<>();

    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {

        //could just put objects directly in from here
        exits.put(direction, neighbor);
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void addObject(String objectDescription) 
    {

        roomItems.add(objectDescription);
    }

    /**
     *Add NPC to the room
     */

    public void addNPC(String NPCDescription) 
    {

        NPCs.add(NPCDescription);
    }

    public void NPCDescription(){
        for (int i = 0; i < NPCs.size();i++) 
        {               
            System.out.println(NPCs.get(i));         
        }  
    }

    public void removeObject(String objectDescription){

        //    roomItems.remove(objectDescription);
        roomItems.remove(objectDescription);

    }

    public void getShortDescriptionObject()
    {
        for (int i = 0; i < roomItems.size();i++) 
        {               
            System.out.println(roomItems.get(i));         
        }   

    }

    public String descriptionStatic()
    {
        x=shortDescription;
        return x;
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
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

