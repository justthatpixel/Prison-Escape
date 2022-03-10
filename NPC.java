import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * This class is part of the "Prison Escape" application. 
 * "Prison Escape" is a simple, text based adventure game.
 * 
 * The NPC class holds the charachter information
 * It allows you to add or remove items from the player's inventory.
 *
 * @author (Raihan Kamal)
 * @version 2021.12.03
 */
public class NPC
{
    // instance variables - replace the example below with your own

    private String description;
    private ArrayList<String> NPCItems = new ArrayList<String>();

    /**
     * Constructor for objects of class NPC
     */
    public NPC(String description)
    {
        // initialise instance variables

        this.description = description;
    }

    public void addObjectNPC(String objectDescription) 
    {

        NPCItems.add(objectDescription);
        System.out.println(objectDescription+"has been added to NPC's inventory");

    }

    public void removeObjectNPC(String objectDescription){

        //    roomItems.remove(objectDescription);
        NPCItems.remove(objectDescription);
        System.out.println(objectDescription+"has been removed from NPC's inventory");

    }
    public void npcDialogue()
    {

        System.out.println("Give me a basketball and il give something to help you on your escape");
    }

    /**
     * Return description of NPC
     */
    public String NPCdescription(){
        String x= description;
        return x; 

    }
 
}
