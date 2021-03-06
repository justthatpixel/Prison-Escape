import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *  This class is the main class of the "Prison Escape" application. 
 *  "Prison Escape" is a  simple, text based adventure game. Here you will fight other inmates and trade items with other characters to receive the tools needed to escape the prison.  
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser, creates the objects + weight and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  It also contains the  turn based mini-game where you fight an inmate ( 2 choices : attack or heal)
 * 
 * @author  Michael Kölling and David J. Barnes and Raihan Kamal
 * @version 2021.12.03
 */

/* 
 * Main class that holds the game
 */

public class Game 
{
    // Declared the variables 
    private Parser parser;
    private player man;
    private NPC npc;

    //Declaring the Rooms (for random Room challenge task)     
    Room previousRoom;
    private Room TransporterRoom;
    private Room Lab;
    private Room Office;
    private Room Cell;
    private Room Courtyard;
    private Room Gym;
    private Room currentRoom;

    // New Scanner used for turn-based mini game
    Scanner in= new Scanner(System.in);
    Random random= new Random();

    //Player variables for the turn based game
    int playerHealth=100;                   //Player's Health 
    int attackDmg=30;                       //Player's Attack damage
    int numOfHealthItem=3;                  //Number of times Player can heal
    int healAmount= 30;                     //How much Health the player can heal from Health Item

    //Enemy variables for the turn based game
    int maxenemyHealth= 50;                 // Max Health the enemy can have
    int enemyAttackDamge= 25;               // Max damage an enemy can do

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     * Create all objects and assign weights to object
     */
    private void createRooms()
    {
        Room courtyard, gym, kitchen, lab, office,transporterRoom, cell;

        // create the rooms
        courtyard = new Room("the courtyard outside of your prison cell ","courtyard");
        gym = new Room("in a prison gym","gym");
        kitchen = new Room("in the prison kitchen","kitchen");
        lab = new Room("in a chemical labatory","lab");
        office = new Room("in the computing admin office","office");
        transporterRoom= new Room("A room that sends you to a random room","transporterroom");                                          // Send Player to random location
        cell= new Room("You enter your cell, there seems to be cross on the ground. Maybe I should dig here?","cell");                  //New room which the Player will use to escape & win the game

        //Create a charachter AKA. ("NPC")
        lab.addNPC("Dwarf sits in the corner");

        man= new player("an angry prisoner");
        //Room items
        office.addObject("computer");
        gym.addObject("dumbell");
        courtyard.addObject("basketball");

        //Item weights
        man.addWeight("computer",5);
        man.addWeight("popcorn",100);
        man.addWeight("basketball",3);
        man.addWeight("shovel",2);

        // initialise room exits
        courtyard.setExit("east", gym);
        courtyard.setExit("south", lab);
        courtyard.setExit("west", kitchen);
        courtyard.setExit("north",cell);

        cell.setExit("south",courtyard);
        gym.setExit("west", courtyard);
        gym.setExit("east", transporterRoom);
        gym.setExit("south", lab);
        kitchen.setExit("east", courtyard);
        lab.setExit("east", gym);
        lab.setExit("west",transporterRoom);
        lab.setExit("north", courtyard);
        office.setExit("west", lab);

        //initialise objects in rooms
        currentRoom = courtyard; 

        //Assigned room to a variable (for Random Room challenge task)
        Lab=lab;
        Office=office;
        Cell=cell;
        Courtyard=courtyard;
        Gym=gym;
        TransporterRoom=transporterRoom;
        // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     *  Enemy "Prisoner" is introduced, once the enemy health=0 they may continue with the game
     *  Turn based mini-game is run here, once enemy has been defeated they may continue with the game
     */
    public void play() 
    {            
        printWelcome();                                     //Welcome message is printed at the start of the game

        boolean finished = false;                           // If the player chooses to quit/break out the game this variable is set to true
        boolean Defeated = false;                           // While loop holds the turn based game, 
        int enemyHealth= random.nextInt(maxenemyHealth);    //random health chosen for enemy 
        adventure:                                          //Tag used to break out of loop when enemy is defeated
        while (! finished) {
            // Enter the main command loop.  Here we repeatedly read commands and
            // execute them until the game is over.
            Command command = parser.getCommand();
            finished = processCommand(command);

            // The charachter(NPC) location is set to lab and output the charachter's description
            if (currentRoom.descriptionStatic().equals("lab")){
                currentRoom.NPCDescription();
            }

            if (Defeated==false){
                String enemy= "Prisoner";                           //name of enemy 
                System.out.println(""+ enemy + " has appeared!");   // output name of enemy on screen

                while (enemyHealth>1) {

                    //Formatting for the mini-game

                    System.out.println("-----------------------");
                    System.out.println("\tYour HP: "+playerHealth);
                    System.out.println(enemy+ "'s HP"+ enemyHealth);
                    System.out.println("\t You have 2 options, Please Type in one of the following : attack or heal");
                    System.out.println("\t •Attack the enemy");
                    System.out.println("\t •Eat A Snicker bar");
                    System.out.println("-----------------------");
                    String input= in.nextLine();

                    //If the enemyHealth is bellow 0, this condition is met and the player continues with their adventure

                    if (input.equals("attack")){
                        int playerAttackDmg= attackDmg;                         // damage given to enemy
                        int enemyAttackDmg= random.nextInt(attackDmg);          //random damage taken from enemy

                        enemyHealth -=playerAttackDmg;                          //enemyHealth is updated after damage is registered
                        playerHealth-=enemyAttackDamge;                         // Player recieves damage

                        System.out.println( enemy + " recieves: " +  playerAttackDmg + " damage.");
                        System.out.println("You recieve: " +enemyAttackDmg+ "damage");

                        if (enemyHealth<1){ 
                            Defeated= true;
                            System.out.println("You have defeated the enemy");
                            System.out.println("You have returned to the game");
                            System.out.println("------------------------------------------");
                            System.out.println(currentRoom.getLongDescription());
                            currentRoom.getShortDescriptionObject();
                            System.out.println(" are the objects in this room");
                            System.out.println("What would you like to do with this item? e.g (Take item/Remove item)");
                            

                            continue adventure;

                        }
                        if(playerHealth<1){System.out.println("You have been defeated");}
                        break;

                    }

                    else if(input.equals("heal")){
                        if (numOfHealthItem>1)
                            playerHealth+=healAmount;
                        numOfHealthItem=numOfHealthItem-1;  //1 heal item is used
                        System.out.println("You gain 30 hp" + "\n Your HP: "+ playerHealth+"hp");

                    }

                }
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Prison!");
        System.out.println("Arriving here in Prison you must fight a prisoner, so get Ready!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("Umm what?...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("take")) {
            pickItem(command);
            //inventory function here
        }
        else if (commandWord.equals("remove")) {
            removeItem(command);
            //inventory function here
        }
        else if (commandWord.equals("talk")) {
            NPCspeechOptions();
            //inventory function here
        }
        else if (commandWord.equals("give")) {
            importantNPCItem(command);
            //inventory function here
        }
        else if (commandWord.equals("back")) {
            goBack();
            //inventory function here
        }
        else if (commandWord.equals("dig")) {
            winCondition();
            //inventory function here
        }

        // else command not recognised.
        return wantToQuit;
    }

    /**
     * If the player has the "shovel" and is in the room "cell", they may use the command "dig" which allows them to Win the game
     * command words.
     */
    private void winCondition()
    {
        if (currentRoom.descriptionStatic().equals("cell") && man.hasItemInInventory("shovel")){
            System.out.println("Looks like you've escaped, but you may explore the prison as you please");

        }

    }

    /**
     * "back" command, takes you to previous room
     * When "go" command is used, previous room  is updated 
     * Sets currentRoom=previousrooom
     */
    private void goBack()
    {

        currentRoom=previousRoom;
        System.out.println("You have gone back to the previous room!");
        System.out.println(" is in this room");
        System.out.println("What would you like to do with this item? e.g (Take item/Remove item)");
        System.out.println("The previous room is: "+ previousRoom);
    }

    /**
     * Extend Parser to Scan 3 words
     * If valid command inputted, returns "shovel" to players inventory, nothing otherwise
     */
    private void importantNPCItem(Command command){
        //The words retrieved from the Parser
        String firstword= command.getCommandWord();     
        String secondword = command.getSecondWord();
        String thirdword= command.getThirdWord();

        // Check the current room is in the lab( where the charachter is currently staying and the player has a "basketball" in their inventory)         
        if( currentRoom.descriptionStatic().equals("lab") && man.hasItemInInventory("basketball")) 
        {
            System.out.println("please enter what you want to trade?");
            if (secondword.equals("dwarf"))
            {
                if(thirdword.equals("basketball"))
                {
                    man.addItem("shovel");
                    System.out.println("The Dwarf out of generocity lets you keep the basketball aswell");

                }
            }
        }
        else{

            System.out.println("Your either not in the lab or dont have a basketball");
        }

    }

    /**
     * Print out Npc description, e.g ("Dwarf sits in the corner")
     * Purpose is to tell player NPC is in the room
     */
    private void NPCspeechOptions(){
        npc.npcDialogue();
    }
    // implementations of user commands:

    /**
     * Random number generator sends you to a random room
     */
    private void randomRoom()

    {
        int numOfRooms=5;
        int value= random.nextInt(numOfRooms);
        switch (value) {
            case 1:
                currentRoom=Lab;
                System.out.println("You have been transported to a random room");
                System.out.println("You are in the lab.");
                break;
            case 2:
                currentRoom=Office;
                System.out.println("You have been transported to a random room");
                System.out.println("You are in the office.");
                break;
            case 3:
                currentRoom=Courtyard;
                System.out.println("You have been transported to a random room");
                System.out.println("You are in the courtyard.");
                break;
            case 4:
                currentRoom=Gym;
                System.out.println("You have been transported to a random room");
                System.out.println("You are in the gym.");
                break;
            case 5:
                currentRoom=Cell;    
                System.out.println("You have been transported to a random room");
                System.out.println("You are in the cell.");
                break;
        }

    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * Returns all the objects in the current Room
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom==TransporterRoom)
        {

            randomRoom();
        }
        else{

            if (nextRoom == null) {
                System.out.println("There is no door!");
            }

            else    {
                previousRoom=currentRoom;  //Stores value for previous room and 
                currentRoom = nextRoom;
                System.out.println("------------------------------------------");
                System.out.println(currentRoom.getLongDescription());
                currentRoom.getShortDescriptionObject();
                System.out.println(" are the objects in this room");
                System.out.println("What would you like to do with this item? e.g (Take item/Remove item)");

            }
        }
    }

    /** 
     * Pick up item From Room and put in Player's inventory
     * First check secondWord equals to an item in the room before continuing
     * Add the valid item to Player's inventory (arrayList)
     * Remove item from Room (arrayList)
     * If there are no items in the room we once again ask Player what they would like to do
     */
    private void pickItem(Command command) 
    {

        String secondword = command.getSecondWord();
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return;
        }
        else{ 

            currentRoom.removeObject(secondword);
            man.addItem(secondword);

            // actually remove item from room and add item to inventory.
            String currentRoomObject=currentRoom.getShortDescription();

            if (currentRoomObject.equals(null)) {
                System.out.println("There is no item here!");
            }

            else{
                System.out.println("------------------------------------------");
                System.out.println(currentRoom.getLongDescription());
                System.out.println(" Objects in this room are listed bellow : ");
                currentRoom.getShortDescriptionObject();                 
                System.out.println("What would you like to do with it e.g (take item/remove item)");

            }
        }
    }

    /** 
     *  Removes Item From Player's Inventory and Adds Item to Room
     *
     */
    private void removeItem(Command command) 

    {
        String secondword = command.getSecondWord();

        if(!command.hasSecondWord()){

            System.out.println("Remove what?");
            return;
        }
        else{
            currentRoom.addObject(secondword);
            man.removeItem(secondword);
        }

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
