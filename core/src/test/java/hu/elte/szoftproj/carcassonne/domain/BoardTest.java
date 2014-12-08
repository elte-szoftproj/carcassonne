package hu.elte.szoftproj.carcassonne.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BoardTest {

    Board board;

    @Before
    public void createBoard() {
        board = new Board(StandardTiles.stdCity1rwe, Rotation.R0);
    }

    @Test
    public void testCity4Placement() {
        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, -1, 0), equalTo(true));
        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, 1, 0), equalTo(false));
        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, 0, -1), equalTo(false));
        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, 0, 1), equalTo(false));

        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, 0, 0), equalTo(false));

        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, -2, 0), equalTo(false));

        assertThat(board.canPlaceAt(StandardTiles.stdCity4, Rotation.R0, -50, -50), equalTo(false));
    }

    @Test
    public void testRoadPlacement() {
        assertThat(board.canPlaceAt(StandardTiles.stdRoad3, Rotation.R0, -1, 0), equalTo(false));
        assertThat(board.canPlaceAt(StandardTiles.stdRoad3, Rotation.R0,  1, 0), equalTo(true));
        assertThat(board.canPlaceAt(StandardTiles.stdRoad3, Rotation.R0,  0, 1), equalTo(true));
        assertThat(board.canPlaceAt(StandardTiles.stdRoad3, Rotation.R0,  0, -1), equalTo(true));

        assertThat(board.canPlaceAt(StandardTiles.stdRoad3, Rotation.R180,  1, 0), equalTo(false));
    }

    @Test
    public void testPlacement() {
        board = board.placeTile(StandardTiles.stdCity1, Rotation.R180, -1, 0);

        assertThat(board.placedTileCount(), equalTo(2));

        assertThat(board.canPlaceAt(StandardTiles.stdCity1, Rotation.R180, -1, 0), equalTo(false));
        assertThat(board.canPlaceAt(StandardTiles.stdCity1, Rotation.R0, -2, 0), equalTo(true));
        assertThat(board.canPlaceAt(StandardTiles.stdCity1, Rotation.R0, -1, -1), equalTo(true));
        assertThat(board.canPlaceAt(StandardTiles.stdCity1, Rotation.R0, -1, 1), equalTo(true));
    }

    @Test
    public void testPlacable() {
        assertThat(board.canBePlaced(StandardTiles.stdCity1), equalTo(true));
        assertThat(board.canBePlaced(StandardTiles.stdCity4), equalTo(true));
    }

    @Test
    public void testUnplacable() {
        board = board.placeTile(StandardTiles.stdCity1, Rotation.R180, -1, 0);

        assertThat(board.canBePlaced(StandardTiles.stdCity1), equalTo(true));
        assertThat(board.canBePlaced(StandardTiles.stdCity4), equalTo(false));
    }

    @Test
    public void testPlacableCount() {
        board = board.placeTile(StandardTiles.stdCity1rse, Rotation.R180, -1, 0);

        assertThat(board.whereCanBePlaced(StandardTiles.stdRoad2ns, Rotation.R90).size(), equalTo(4));
        assertThat(board.whereCanBePlaced(StandardTiles.stdRoad2ns, Rotation.R0).size(), equalTo(2));
        assertThat(board.whereCanBePlaced(StandardTiles.stdRoad2sw, Rotation.R0).size(), equalTo(3));
        assertThat(board.whereCanBePlaced(StandardTiles.stdRoad3, Rotation.R0).size(), equalTo(5));
    }

    @Test
    public void basicAreaCount() {
        assertThat(board.getAreas().size(), equalTo(4));

        int groundCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('G')) groundCount++;
        }
        assertThat(groundCount, equalTo(2));

        int roadCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('R')) {
                roadCount++;
                assertThat(a.getCoordinates().size(), equalTo(5));
            }
        }
        assertThat(roadCount, equalTo(1));
    }

    @Test
    public void advancedAreaCount() {
        board = board.placeTile(StandardTiles.stdCity1rsw, Rotation.R0, 0, 1);
        assertThat(board.getAreas().size(), equalTo(4+1));

        board = board.placeTile(StandardTiles.stdCity1rswe, Rotation.R0, 0, -1);
        assertThat(board.getAreas().size(), equalTo(4+1+4));

        board = board.placeTile(StandardTiles.stdCity4, Rotation.R0, -1, 0);
        assertThat(board.getAreas().size(), equalTo(4+1+4));
    }

}
