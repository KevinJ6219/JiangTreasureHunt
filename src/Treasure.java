public class Treasure {
    public static final String GEM = "Gems";
    public static final String TROPHY = "the Trophy";
    public static final String CROWN = "the Crown";
    public static final String DUST = "Dust";

    private String type;

    public Treasure() {
        int random = (int) (Math.random() * 3) + 1;
        if (random == 1) {
            type = GEM;
        }
        else if (random == 2) {
            type = TROPHY;
        }
        else if (random == 3) {
            type = CROWN;
        }
        else if (random == 4) {
            type = DUST;
        }
    }

    public static boolean collectionHasAllTreasures(String collection) {
        boolean hasGem = (collection.indexOf(GEM) != -1);
        boolean hasTrophy = (collection.indexOf(TROPHY) != -1);
        boolean hasCrown = (collection.indexOf(CROWN) != -1);

        return (hasGem && hasTrophy && hasCrown);
    }
    public String getType() {
        return type;
    }

}
