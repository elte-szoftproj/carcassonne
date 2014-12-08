package hu.elte.szoftproj.carcassonne.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TileTest {

    @Test
    public void testRotationWithCloister1r() {

        // new Tile("GGGGG-GGGGG-GGTGG-GGRGG-GGRGG", "cloisterr");

        Tile rotated90 = new Tile("GGGGG-GGGGG-RRTGG-GGGGG-GGGGG", "cloisterr");
        Tile rotated180 = new Tile("GGRGG-GGRGG-GGTGG-GGGGG-GGGGG", "cloisterr");
        Tile rotated270 = new Tile("GGGGG-GGGGG-GGTRR-GGGGG-GGGGG", "cloisterr");

        assertThat(StandardTiles.stdCloister1r.getRepresentation90(), equalTo(rotated90.getRepresentation0()));
        assertThat(StandardTiles.stdCloister1r.getRepresentation180(), equalTo(rotated180.getRepresentation0()));
        assertThat(StandardTiles.stdCloister1r.getRepresentation270(), equalTo(rotated270.getRepresentation0()));
    }

    @Test
    public void testPlacementWithCloister1r() {
        assertThat(
                StandardTiles.stdCloister1r.canBePlacedAt(Rotation.R0, StandardTiles.stdCloister1r, Rotation.R0, Placement.LEFT),
                equalTo(true));
        assertThat(
                StandardTiles.stdCloister1r.canBePlacedAt(Rotation.R0, StandardTiles.stdCloister1r, Rotation.R0, Placement.RIGHT),
                equalTo(true));
        assertThat(
                StandardTiles.stdCloister1r.canBePlacedAt(Rotation.R0, StandardTiles.stdCloister1r, Rotation.R0, Placement.TOP),
                equalTo(false));
        assertThat(
                StandardTiles.stdCloister1r.canBePlacedAt(Rotation.R0, StandardTiles.stdCloister1r, Rotation.R0, Placement.BOTTOM),
                equalTo(false));

        assertThat(
                StandardTiles.stdCloister1r.canBePlacedAt(Rotation.R0, StandardTiles.stdCloister1r, Rotation.R180, Placement.BOTTOM),
                equalTo(true));
    }

    @Test
    public void testPlacementWithCity1rs() {
        assertThat(
                StandardTiles.stdCity1rswe.canBePlacedAt(Rotation.R0, StandardTiles.stdCity1rse, Rotation.R0, Placement.LEFT),
                equalTo(true));

        assertThat(
                StandardTiles.stdCity1rswe.canBePlacedAt(Rotation.R0, StandardTiles.stdCity1rse, Rotation.R0, Placement.RIGHT),
                equalTo(false));

        assertThat(
                StandardTiles.stdCity1rswe.canBePlacedAt(Rotation.R0, StandardTiles.stdCity1rse, Rotation.R180, Placement.RIGHT),
                equalTo(true));

        assertThat(
                StandardTiles.stdCity1rswe.canBePlacedAt(Rotation.R0, StandardTiles.stdCity1rse, Rotation.R0, Placement.TOP),
                equalTo(false));

        assertThat(
                StandardTiles.stdCity1rswe.canBePlacedAt(Rotation.R0, StandardTiles.stdCity1rse, Rotation.R180, Placement.TOP),
                equalTo(true));
    }
}
