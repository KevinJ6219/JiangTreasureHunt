public class Treasure {
    public static final String CROWN = "Crown";
    public static final String CAPE= "Cape";
    public static final String RING = "ring";
    public static final String NOTHING = "nothing";

    private String treasure;

    public Treasure() {
        int random = (int) (Math.random() * 3) + 1;
        if (random == 1) {
            treasure = CROWN;
        }
        else if (random == 2) {
            treasure = CAPE;
        }
        else if (random == 3) {
            treasure = RING;
        }
        else if (random == 4) {
            treasure = NOTHING;
        }
    }

    public static boolean hasAllTreasures(String collection) {
        return ((collection.indexOf(CROWN) != -1) && (collection.indexOf(CAPE) != -1) && (collection.indexOf(RING) != -1));
    }
    public String getTreasure() {
        return treasure;
    }

}
