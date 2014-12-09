package hu.elte.szoftproj.carcassonne.domain;

public class StandardTiles {

    // starter (city with road)
    public static final Tile stdCity1rwe = new Tile("XCCCX-GGGGG-RRRRR-GGGGG-GGGGG", "city1rwe");

    // city without road
    public static final Tile stdCity4 =        new Tile("XCCCX-CCCCC-CCCCC-CCCCC-XCCCX", "city4");
    public static final Tile stdCity3 =        new Tile("XCCCX-CCCCC-CCCCC-CCCCC-XGGGX", "city3");
    public static final Tile stdCity2we =      new Tile("XGGGX-CCCCC-CCCCC-CCCCC-XGGGX", "city2we");
    public static final Tile stdCity2nw =      new Tile("XCCCX-CCCGG-CCGGG-CGGGG-XGGGG", "city2nw");
    public static final Tile stdCity11ne =     new Tile("XCCCX-GGGGC-GGGGC-GGGGC-GGGGX", "city11ne");
    public static final Tile stdCity11we =     new Tile("XGGGX-CGGGC-CGGGC-CGGGC-XGGGX", "city11we");
    public static final Tile stdCity1 =        new Tile("XCCCX-GGGGG-GGGGG-GGGGG-GGGGG", "city1");

    // city + road (+starter separately)
    public static final Tile stdCity3r =       new Tile("XCCCX-CCCCC-CCCCC-CCCCC-XGRGX", "city3r");
    public static final Tile stdCity2nwr =     new Tile("XCCCX-CCCGG-CCRRR-CGRGG-XGRGG", "city2nwr");
    public static final Tile stdCity1rse =     new Tile("XCCCX-GGGGG-GGRRR-GGRGG-GGRGG", "city1rse");
    public static final Tile stdCity1rsw =     new Tile("XCCCX-GGGGG-RRRGG-GGRGG-GGRGG", "city1rsw");
    public static final Tile stdCity1rswe =    new Tile("XCCCX-GGGGG-RRORR-GGRGG-GGRGG", "city1rswe");

    // road
    public static final Tile stdRoad4     =    new Tile("GGRGG-GGRGG-RRORR-GGRGG-GGRGG", "road4");
    public static final Tile stdRoad3     =    new Tile("GGGGG-GGGGG-RRORR-GGRGG-GGRGG", "road3");
    public static final Tile stdRoad2ns   =    new Tile("GGRGG-GGRGG-GGRGG-GGRGG-GGRGG", "road2ns");
    public static final Tile stdRoad2sw   =    new Tile("GGGGG-GGGGG-RRRGG-GGRGG-GGRGG", "road2sw");

    // temples
    public static final Tile stdCloister1   =  new Tile("GGGGG-GGGGG-GGTGG-GGGGG-GGGGG", "cloister");
    public static final Tile stdCloister1r  =  new Tile("GGGGG-GGGGG-GGTGG-GGRGG-GGRGG", "cloisterr");

    public static Tile get(String name) {
        // TOOD: refactor! ... this entire class

        switch(name) {
            case "city1rwe": return stdCity1rwe;

            case "city4": return stdCity4;
            case "city3": return stdCity3;
            case "city2we": return stdCity2we;
            case "city2nw": return stdCity2nw;
            case "city11ne": return stdCity11ne;
            case "city11we": return stdCity11we;
            case "city1": return stdCity1;

            case "city3r": return stdCity3r;
            case "city2nwr": return stdCity2nwr;
            case "city1rse": return stdCity1rse;
            case "city1rsw": return stdCity1rsw;
            case "city1rswe": return stdCity1rswe;

            case "road4": return stdRoad4;
            case "road3": return stdRoad3;
            case "road2ns": return stdRoad2ns;
            case "road2sw": return stdRoad2sw;

            case "cloister": return stdCloister1;
            case "cloisterr": return stdCloister1r;
        }
        throw new IllegalArgumentException("NO_SUCH_TILE");
    }
}
