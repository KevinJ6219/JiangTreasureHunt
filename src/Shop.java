/**
 * The Shop class controls the cost of the items in the Treasure Hunt game.<p>
 * The Shop class also acts as a go between for the Hunter's buyItem() method.<p>
 * This code has been adapted from Ivan Turner's original program -- thank you Mr. Turner!
 */
import java.util.Scanner;

public class Shop
{
    // constants
    private static int WATER_COST = 2;
    private static int ROPE_COST = 4;
    private static int MACHETE_COST = 6;
    private static int HORSE_COST = 12;
    private static int BOAT_COST = 20;
    private static int AXE_COST = 35;


    // instance variables
    private double markdown;
    private Hunter customer;
    private double discount = 0.6;

    //Constructor
    public Shop(double markdown)
    {
        this.markdown = markdown;
        customer = null;
        if (TreasureHunter.isCheatMode()) {
            WATER_COST = 1;
            ROPE_COST = 1;
            MACHETE_COST = 1 ;
            HORSE_COST = 1;
            BOAT_COST = 1;
            AXE_COST = 1;
        }
        if (TreasureHunter.isEasyMode()) {
            WATER_COST *= discount;
            ROPE_COST *= discount;
            MACHETE_COST *= discount ;
            HORSE_COST *= discount;
            BOAT_COST *= discount;
            AXE_COST *= discount;
        }
    }

    /** method for entering the shop
     * @param hunter  the Hunter entering the shop
     * @param buyOrSell  String that determines if hunter is "B"uying or "S"elling
     */
    public void enter(Hunter hunter, String buyOrSell)
    {
        customer = hunter;

        Scanner scanner = new Scanner(System.in);
        if (buyOrSell.equals("B") || buyOrSell.equals("b"))
        {
            System.out.println("Welcome to the shop! We have the finest wares in town.");
            System.out.println("Currently we have the following items:");
            System.out.println(inventory());
            System.out.print("What're you lookin' to buy? ");
            String item = scanner.nextLine();
            item = findItem(item);
            int cost = checkMarketPrice(item, true);
            if (cost == 0)
            {
                System.out.println("We ain't got none of those.");
            }
            else
            {
                System.out.print("It'll cost you " + cost + " gold. Buy it (y/n)? ");
                String option = scanner.nextLine();

                if (option.equals("y") || option.equals("Y"))
                {
                    buyItem(item);
                }
            }
        }
        else
        {
            System.out.println("What're you lookin' to sell? ");
            System.out.print("You currently have the following items: " + customer.getInventory());
            String item = scanner.nextLine();

            int cost = checkMarketPrice(item, false);
            if (cost == 0)
            {
                System.out.println("We don't want none of those.");
            }
            else
            {
                System.out.print("It'll get you " + cost + " gold. Sell it (y/n)? ");
                String option = scanner.nextLine();

                if (option.equals("y") || option.equals("Y"))
                {
                    sellItem(item);
                }
            }
        }
    }

    /** A method that returns a string showing the items available in the shop (all shops sell the same items)
     *
     * @return  the string representing the shop's items available for purchase and their prices
     */
    public String inventory()
    {
        String str = "Water(W): " + WATER_COST + " gold\n";
        str += "Rope(R): " + ROPE_COST + " gold\n";
        str += "Machete(M): " + MACHETE_COST + " gold\n";
        str += "Horse(H): " + HORSE_COST + " gold\n";
        str += "Boat(B): " + BOAT_COST + " gold\n";
        str += "Axe(A): " + AXE_COST + " gold\n";

        return str;
    }

    /**
     * A method that lets the customer (a Hunter) buy an item.
     * @param item The item being bought.
     */
    public void buyItem(String item)
    {
        int costOfItem = checkMarketPrice(item, true);
        if (customer.buyItem(item, costOfItem))
        {
            System.out.println("Ye' got yerself a " + item + ". Come again soon.");
        }
        else
        {
            System.out.println("Hmm, either you don't have enough gold or you've already got one of those!");
        }
    }

    /**
     * A pathway method that lets the Hunter sell an item.
     * @param item The item being sold.
     */
    public void sellItem(String item)
    {
        int buyBackPrice = checkMarketPrice(item, false);
        if (customer.sellItem(item, buyBackPrice))
        {
            System.out.println("Pleasure doin' business with you.");
        }
        else
        {
            System.out.println("Stop stringin' me along!");
        }
    }

    /**
     * Determines and returns the cost of buying or selling an item.
     * @param item The item in question.
     * @param isBuying Whether the item is being bought or sold.
     * @return The cost of buying or selling the item based on the isBuying parameter.
     */
    public int checkMarketPrice(String item, boolean isBuying)
    {
        if (isBuying)
        {
            return getCostOfItem(item);
        }
        else
        {
            return getBuyBackCost(item);
        }
    }

    /**
     * Checks the item entered against the costs listed in the static variables.
     *
     * @param item The item being checked for cost.
     * @return The cost of the item or 0 if the item is not found.
     */
    public int getCostOfItem(String item)
    {
        String input = item.toLowerCase();
        if (input.equals("water") || input.equals("w"))
        {
            return WATER_COST;
        }
        else if (input.equals("rope") || input.equals("r"))
        {
            return ROPE_COST;
        }
        else if (input.equals("machete") || input.equals("m"))
        {
            return MACHETE_COST;
        }
        else if (input.equals("horse") || input.equals("h"))
        {
            return HORSE_COST;
        }
        else if (input.equals("boat") || input.equals("b"))
        {
            return BOAT_COST;
        }
        else if (input.equals("axe") || input.equals("a")) {
            return AXE_COST;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Checks the cost of an item and applies the markdown.
     *
     * @param item The item being sold.
     * @return The sell price of the item.
     */
    public int getBuyBackCost(String item)
    {
        int cost = (int)(getCostOfItem(item) * markdown);
        return cost;
    }

    public String findItem(String item)
    {
        String input = item.toLowerCase();
        if (input.equals("w"))
        {
            return "Water";
        }
        else if (input.equals("r"))
        {
            return "Rope";
        }
        else if (input.equals("m"))
        {
            return "Machete";
        }
        else if (input.equals("h"))
        {
            return "Horse";
        }
        else if (input.equals("b"))
        {
            return "Boat";
        }
        else if (input.equals("a")) {
            return "Axe";
        }
        else
        {
            return item;
        }
    }
}