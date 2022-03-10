import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
/**
 * This class is part of the "Prison Escape" application. 
 *"Prison Escape" is a simple, text based adventure game.  
 * Player Class contains : Player's Inventory, Weight of Objects, 
 * This Class allows you to Add or Remove items from the Player's inventory
 * @author (Raihan Kamal)
 * @version (03/12/2021)
 */
public class player
{

    // instance variables - replace the example below with your own
    private String object;                                                     
    private ArrayList<String> inventoryItems = new ArrayList<String>();
    private HashMap<String,Integer> Weight;
    int inventoryWeight=0;
    private String profile;

    
    /**
     * Constructor for objects of class player
     * Constructor for  inventoryItems and weight
     */
    public player(String profile)
    {
        this.profile=profile;
        inventoryItems= new ArrayList<>(); // actual inventory that holds the items
        Weight= new HashMap<>(); //objects and its weights
    }

    public void addItem(String Object){
        if (inventoryWeight<10&& Weight.containsKey(Object)){
            inventoryItems.add(Object); 
            System.out.println(" The "+Object+" has been added to your inventory ! ");
            System.out.println(Weight.get(Object));
            int temp=Weight.get(Object);
            inventoryWeight=inventoryWeight+temp;
            System.out.println("The weight of the inventory is : "+ inventoryWeight);
        }
        else {
            System.out.println("sorry cant take item, your inventory is too heavy !!!:"+inventoryWeight);
        }

    }

    public void removeItem(String Object){
        inventoryItems.remove(Object); 
        System.out.println(" The "+Object+" has been removed from your inventory ! ");

    }

    public void addWeight(String objectDescription, Integer weight)
    {
        Weight.put(objectDescription, weight);
    }

    public int inventoryWeight()
    {
        return inventoryWeight;
    }

    public boolean hasItemInInventory(String object){
        return  inventoryItems.contains(object);

    }

}
