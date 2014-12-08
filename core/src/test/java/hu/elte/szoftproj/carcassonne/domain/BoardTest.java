package hu.elte.szoftproj.carcassonne.domain;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.follower.BasicFollower;
import hu.elte.szoftproj.carcassonne.domain.follower.BigFollower;
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

    @Test
    public void basicEdgeCounts() {
        int roadCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('R')) {
                roadCount++;
                assertThat(a.getOpenEdgeCount(), equalTo(2));
            }
        }
        assertThat(roadCount, equalTo(1));

        int cityCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('C')) {
                cityCount ++;
                assertThat(a.getOpenEdgeCount(), equalTo(3));
            }
        }
        assertThat(cityCount , equalTo(1));
    }

    @Test
    public void advEdgeCounts() {
        board = board.placeTile(StandardTiles.stdCity1, Rotation.R180, -1, 0);
        int cityCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('C')) {
                cityCount ++;
                assertThat(a.getOpenEdgeCount(), equalTo(0));
            }
        }
        assertThat(cityCount , equalTo(1));
    }

    @Test
    public void adv2EdgeCounts() {
        board = board.placeTile(StandardTiles.stdCity4, Rotation.R0, -1, 0);
        board = board.placeTile(StandardTiles.stdCity1, Rotation.R180, -2, 0);
        board = board.placeTile(StandardTiles.stdCity1, Rotation.R270, -1, 1);
        board = board.placeTile(StandardTiles.stdCity1, Rotation.R90, -1, -1);

        int cityCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('C')) {
                cityCount ++;
                assertThat(a.getOpenEdgeCount(), equalTo(0));
            }
        }
        assertThat(cityCount , equalTo(1));
    }

    @Test
    public void testAddFollower() {
        Follower f = new BasicFollower(new Player("test"));
        board = board.placeFollower(0, 0, f, 0, 3);

        assertThat(board.getUsedFollowers().size(), equalTo(1));

        Follower f2 = new BasicFollower(new Player("test2"));
        board = board.placeFollower(0, 0, f2, 2, 3);

        assertThat(board.getUsedFollowers().size(), equalTo(2));

        int roadCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('R')) {
                roadCount++;
                assertThat(a.getFollowers().size(), equalTo(1));
            }
        }
        assertThat(roadCount, equalTo(1));

        int cityCount = 0;
        for (Area a: board.getAreas()) {
            if (a.getType().equals('C')) {
                cityCount ++;
                assertThat(a.getFollowers().size(), equalTo(1));
            }
        }
        assertThat(cityCount , equalTo(1));
    }

    @Test
    public void testRemoveFollower() {
        Follower f = new BasicFollower(new Player("test"));
        board = board.placeFollower(0, 0, f, 0, 3);

        Follower f2 = new BasicFollower(new Player("test2"));
        board = board.placeFollower(0, 0, f2, 2, 3);

        board = board.removeFollowersFromArea(board.getUsedFollowers().values().asList().get(0));
        assertThat(board.getUsedFollowers().size(), equalTo(1));
        board = board.removeFollowersFromArea(board.getUsedFollowers().values().asList().get(0));
        assertThat(board.getUsedFollowers().size(), equalTo(0));
    }

    @Test
    public void testAreaScore() {
        Follower f1 = new BasicFollower(new Player("test1"));
        board = board.placeFollower(0, 0, f1, 0, 3);

        board = board.placeTile(StandardTiles.stdCity1rse, Rotation.R0, 0, -1);
        Follower f2 = new BigFollower(new Player("test2"));
        board = board.placeFollower(0, -1, f2, 0, 3);

        board = board.placeTile(StandardTiles.stdCity4, Rotation.R0, -1, -1);
        board = board.placeTile(StandardTiles.stdCity4, Rotation.R0, -1, 0);

        assertThat(board.getUsedFollowers().size(), equalTo(2));

        Area followerArea = board.getUsedFollowers().values().asList().get(0);

        assertThat(followerArea.getFollowers().size(), equalTo(2));

        ImmutableList<AreaScore> scores = followerArea.getScores();

        assertThat(scores.get(0).getScore(), equalTo(2));
        assertThat(scores.get(0).getPlayer().getName(), equalTo("test2"));
        assertThat(scores.get(1).getScore(), equalTo(1));
        assertThat(scores.get(1).getPlayer().getName(), equalTo("test1"));
    }

    @Test
    public void testAreaSize() {
        Follower f1 = new BasicFollower(new Player("test1"));
        board = board.placeFollower(0, 0, f1, 0, 3);
        Area followerArea = board.getUsedFollowers().values().asList().get(0);
        assertThat(followerArea.getContainedTileCount(), equalTo(1));

        board = board.placeTile(StandardTiles.stdCity4, Rotation.R0, -1, 0);
        followerArea = board.getUsedFollowers().values().asList().get(0);
        assertThat(followerArea.getContainedTileCount(), equalTo(2));

    }

    @Test
    public void testAreaNeighbors() {
        Follower f1 = new BasicFollower(new Player("test1"));
        board = board.placeFollower(0, 0, f1, 0, 3);
        Area followerArea = board.getUsedFollowers().values().asList().get(0);
        assertThat(board.getNeighborAreasOfTYpe(followerArea, 'R').size(), equalTo(0));
        assertThat(board.getNeighborAreasOfTYpe(followerArea, 'G').size(), equalTo(1));
        assertThat(board.getNeighborAreasOfTYpe(followerArea, 'C').size(), equalTo(0));

        board = board.placeTile(StandardTiles.stdCity1, Rotation.R180, -1, 0);
        followerArea = board.getUsedFollowers().values().asList().get(0);
        assertThat(board.getNeighborAreasOfTYpe(followerArea, 'G').size(), equalTo(2));
    }
}

