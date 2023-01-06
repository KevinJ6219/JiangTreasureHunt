/**
 * The Town Class is where it all happens.
 * The Town is designed to manage all of the things a Hunter can do in town.
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */
import java.io.*;
public class Town
{
    //instance variables
    private Hunter hunter;
    private Shop shop;
    private Terrain terrain;
    private String printMessage;
    private boolean toughTown;
    private Treasure treasure;
    private double toughness;
    private int winCondition;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    //Constructor
    /**
     * The Town Constructor takes in a shop and the surrounding terrain, but leaves the hunter as null until one arrives.
     * @param shop The town's shoppe.
     */
    public Town(Shop shop, double toughness)
    {
        this.shop = shop;
        this.terrain = getNewTerrain();

        // the hunter gets set using the hunterArrives method, which
        // gets called from a client class
        hunter = null;

        printMessage = "";

        // higher toughness = more likely to be a tough town
        toughTown = (Math.random() < toughness);
        this.toughness = toughness;
        treasure = new Treasure();
        winCondition = 0;
    }

    public int getWinCondition() {
        return winCondition;
    }
    public String getLatestNews()
    {
        return printMessage;
    }

    /**
     * Assigns an object to the Hunter in town.
     * @param hunter The arriving Hunter.
     */
    public void hunterArrives(Hunter hunter)
    {
        this.hunter = hunter;
        printMessage = "Welcome to town, " + hunter.getHunterName() + ".";

        if (toughTown)
        {
            printMessage += "\nIt's pretty rough around here, so watch yourself.";
        }
        else
        {
            printMessage += "\nWe're just a sleepy little town with mild mannered folk.";
        }
    }

    /**
     * Handles the action of the Hunter leaving the town.
     * @return true if the Hunter was able to leave town.
     */
    public boolean leaveTown()
    {
        boolean canLeaveTown = terrain.canCrossTerrain(hunter);
        if (canLeaveTown)
        {
            String item = terrain.getNeededItem();
            printMessage = "You used your " + item + " to cross the " + terrain.getTerrainName() + ".";
            if (checkItemBreak())
            {
                hunter.removeItemFromKit(item);
                printMessage += "\nUnfortunately, your " + item + " broke.";
            }

            return true;
        }

        printMessage = "You can't leave town, " + hunter.getHunterName() + ". You don't have a " + terrain.getNeededItem() + ".";
        return false;
    }

    public void enterShop(String choice)
    {
        shop.enter(hunter, choice);
    }

    /**
     * Gives the hunter a chance to fight for some gold.<p>
     * The chances of finding a fight and winning the gold are based on the toughness of the town.<p>
     * The tougher the town, the easier it is to find a fight, and the harder it is to win one.
     */
    public void lookForTrouble()
    {
        int hp1 = 0;
        int hp2 = 0;
        double noTroubleChance;
        int payment = 0;
        if (toughTown)
        {
            noTroubleChance = 0.66;
        }
        else if (TreasureHunter.isEasyMode()) {
            noTroubleChance = 0.20;
        }
        else if(TreasureHunter.isCheatMode()) {
            noTroubleChance = 0.1;
        }
        else
        {
            noTroubleChance = 0.33;
        }

        if (Math.random() > noTroubleChance)
        {
            printMessage = "You couldn't find any trouble";
        }
        else
        {
            printMessage = "You want trouble, stranger!  You got it!\nOof! Umph! Ow!\n";
            int goldDiff = (int)(Math.random() * 10) + 1;
            if (toughness == 0.1) {
                goldDiff = (int) (Math.random() * 20) + 1;
                payment = (int) (Math.random() * 2) + 1;
            }
            if (TreasureHunter.isCheatMode()) {
                payment = 0;
                goldDiff = 100;
            }
            if (TreasureHunter.isEasyMode()) {
                payment = 0;
                goldDiff = (int) (Math.random() * 30) + 15;
            }
            if (Math.random() > noTroubleChance)
            {
                hp1 = ((int) (Math.random() * 55));
                hp2 = 0;
                System.out.print("Your HP: ");
                for (int i = 0;i < hp1; i++) {
                    System.out.print(ANSI_GREEN + "|" + ANSI_RESET);
                }
                System.out.print("          ");
                System.out.print("Enemy's HP: ");
                System.out.print(ANSI_RED + "|" + ANSI_RESET);
                printMessage += "Okay, stranger! You proved yer mettle. Here, take my gold.";
                printMessage += "\nYou won the brawl and receive " +  goldDiff + " gold.";
                hunter.changeGold(goldDiff);
            }
            else
            {
                hp1 = 0;
                hp2 = (int) (Math.random() * 55);
                System.out.print("Your HP: ");
                System.out.print(ANSI_RED + "|" + ANSI_RESET);
                System.out.print("          ");
                System.out.print("Enemy's HP: ");
                for (int i = 0;i < hp2; i++) {
                    System.out.print(ANSI_GREEN + "|" + ANSI_RESET);
                }
                System.out.println();
                if (hunter.getGold() - goldDiff < 0) {
                    printMessage += "What?! You don't have enough to pay up? Guess your time is up!";
                    winCondition = 2;
                }
                else {
                    printMessage += "That'll teach you to go lookin' fer trouble in MY town! Now pay up!";
                    printMessage += "\nYou lost the brawl and pay " + payment + " gold.";
                    hunter.changeGold(-1 * payment);
                }
            }
        }
    }

    public String toString()
    {
        return "This nice little town is surrounded by " + terrain.getTerrainName() + ".";
    }

    /**
     * Determines the surrounding terrain for a town, and the item needed in order to cross that terrain.
     *
     * @return A Terrain object.
     */
    private Terrain getNewTerrain()
    {
        double rnd = Math.random();
        if (rnd < .16)
        {
            return new Terrain("Mountains", "Rope");
        }
        else if (rnd < .32)
        {
            return new Terrain("Ocean", "Boat");
        }
        else if (rnd < .48)
        {
            return new Terrain("Plains", "Horse");
        }
        else if (rnd < .64)
        {
            return new Terrain("Desert", "Water");
        }
        else if (rnd < .80)
        {
            return new Terrain("Jungle", "Machete");
        }
        else {
            return new Terrain("Lost City", "Axe");
        }
    }

    /**
     * Determines whether or not a used item has broken.
     * @return true if the item broke.
     */
    private boolean checkItemBreak()
    {
        return true;
    }

    public void huntForTreasure() {
        String treasureStr = treasure.getType();

        printMessage = "You search the own for treausre... and find " + treasureStr;

        if (treasureStr.equals(Treasure.DUST)) {
            printMessage += ("\nAhhhchoo! That dust is mighty dusty, and it's certainly no treasure!");
        }
        else {
            if (hunter.collectTreasure(treasure)) {
                printMessage += ("\nThat's a new one! You pick it up and add it to your treasure collection.");

                if (Treasure.collectionHasAllTreasures(hunter.getTreasureCollection())) {
                    winCondition = 1;
                }
            }
            else {
                printMessage += ("\nYou have one of those already, who needs two?! You put it back.");
            }
        }

    }


    }