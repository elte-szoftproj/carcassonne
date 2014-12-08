package hu.elte.szoftproj.carcassonne.domain;

public class StandardTiles {

    // starter (city with road)
    static final Tile stdCity1rwe = new Tile("XCCCX-GGGGG-RRRRR-GGGGG-GGGGG", "city1rwe");

    // city without road
    static final Tile stdCity4 =        new Tile("XCCCX-CCCCC-CCCCC-CCCCC-XCCCX", "city4");
    static final Tile stdCity3 =        new Tile("XCCCX-CCCCC-CCCCC-CCCCC-XGGGX", "city3");
    static final Tile stdCity2we =      new Tile("XGGGX-CCCCC-CCCCC-CCCCC-XGGGX", "city2we");
    static final Tile stdCity2nw =      new Tile("XCCCX-CCCGG-CCGGG-CGGGG-XGGGG", "city2nw");
    static final Tile stdCity11ne =     new Tile("XCCCX-GGGGC-GGGGC-GGGGC-GGGGX", "city11ne");
    static final Tile stdCity11we =     new Tile("XGGGX-CGGGC-CGGGC-CGGGC-XGGGX", "city11we");
    static final Tile stdCity1 =        new Tile("XCCCX-GGGGG-GGGGG-GGGGG-GGGGG", "city1");

    // city + road (+starter separately)
    static final Tile stdCity3r =       new Tile("XCCCX-CCCCC-CCCCC-CCCCC-XGRGX", "city3r");
    static final Tile stdCity2nwr =     new Tile("XCCCX-CCCGG-CCRRR-CGRGG-XGRGG", "city2nwr");
    static final Tile stdCity1rse =     new Tile("XCCCX-GGGGG-GGRRR-GGRGG-GGRGG", "city1rse");
    static final Tile stdCity1rsw =     new Tile("XCCCX-GGGGG-RRRGG-GGRGG-GGRGG", "city1rsw");
    static final Tile stdCity1rswe =    new Tile("XCCCX-GGGGG-RRORR-GGRGG-GGRGG", "city1rswe");

    // road
    static final Tile stdRoad4     =    new Tile("GGRGG-GGRGG-RRORR-GGRGG-GGRGG", "road4");
    static final Tile stdRoad3     =    new Tile("GGGGG-GGGGG-RRORR-GGRGG-GGRGG", "road3");
    static final Tile stdRoad2ns   =    new Tile("GGRGG-GGRGG-GGRGG-GGRGG-GGRGG", "road2ns");
    static final Tile stdRoad2sw   =    new Tile("GGGGG-GGGGG-RRRGG-GGRGG-GGRGG", "road2sw");

    // temples
    static final Tile stdCloister1   =  new Tile("GGGGG-GGGGG-GGTGG-GGGGG-GGGGG", "cloister");
    static final Tile stdCloister1r  =  new Tile("GGGGG-GGGGG-GGTGG-GGRGG-GGRGG", "cloisterr");
}
