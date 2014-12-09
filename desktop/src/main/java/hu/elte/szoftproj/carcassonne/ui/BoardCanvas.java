package hu.elte.szoftproj.carcassonne.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableTable;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.screen.CurrentGameInterface;

import java.util.Optional;

public class BoardCanvas {

    static final double TILESIZE = 90.0f;

    public final int topX;
    public final int topY;
    public final int bottomX;
    public final int bottomY;

    private final GameTextureProvider textureProvider;
    private final CurrentGameInterface currentGame;
    private final ShapeRenderer shapeRenderer;

    public int centerX;
    public int centerY;

    public BoardCanvas(int topX, int topY, int bottomX, int bottomY, GameTextureProvider textureProvider, CurrentGameInterface currentGame) {
        this.topX = topX;
        this.topY = topY;
        this.bottomX = bottomX;
        this.bottomY = bottomY;
        this.textureProvider = textureProvider;
        this.currentGame = currentGame;
        centerX = 0;
        centerY = 0;
        this.shapeRenderer = new ShapeRenderer();
    }

    protected void drawTile(SpriteBatch batch, Tile tile, int ix, int iy, Rotation r, float alpha) {
        batch.draw(
                textureProvider.getTextureFor(tile.getName()),
                mapX(ix),
                mapY(iy),
                45.0f,
                45.0f,
                90.0f,
                90.0f,
                alpha,
                alpha,
                360-r.getDegrees()
        );
    }

    protected void maybeDrawPossible(SpriteBatch batch, Tile tile, int ix, int iy, Rotation r, ImmutableSet<Coordinate> placement) {
        if (visible(ix, iy) && placement.contains(new Coordinate(iy, ix))) {
            drawTile(batch, tile, ix, iy, r, 0.7f);
        }
    }

    public void draw(SpriteBatch batch, CarcassonneGame gameObject) {
        ImmutableTable<Integer, Integer, Square> grid = gameObject.getBoard().get().getGrid();
        Board board = gameObject.getBoard().get();

        Optional<Tile> currentTile = currentGame.getCurrentTile();
        Optional<Rotation> currentRotation = currentGame.getCurrentTileRotation();

        ImmutableSet<Coordinate> placement = null;
        if (currentTile.isPresent()) {
            placement = gameObject.getBoard().get().whereCanBePlaced(currentTile.get(), currentRotation.get());
        }


        for (Integer iy: ImmutableSortedSet.copyOf(grid.rowKeySet())) {
            for (Integer ix : ImmutableSortedSet.copyOf(grid.columnKeySet())) {

                if (!grid.contains(iy, ix)) {
                    continue;
                }

                if (currentTile.isPresent()) {
                    maybeDrawPossible(batch, currentTile.get(), ix, iy - 1, currentRotation.get(), placement);
                    maybeDrawPossible(batch, currentTile.get(), ix, iy+1, currentRotation.get(), placement);
                    maybeDrawPossible(batch, currentTile.get(), ix-1, iy, currentRotation.get(), placement);
                    maybeDrawPossible(batch, currentTile.get(), ix+1, iy, currentRotation.get(), placement);
                }

                Square s = grid.get(iy, ix);

                if (visible(ix, iy)) {
                    drawTile(batch, s.getTile(), ix, iy, s.getTileRotation(), 1.0f);
                }
            }
        }

        batch.end();

        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Integer iy: ImmutableSortedSet.copyOf(grid.rowKeySet())) {
            for (Integer ix : ImmutableSortedSet.copyOf(grid.columnKeySet())) {

                if (!grid.contains(iy, ix)) {
                    continue;
                }

                Square s = grid.get(iy, ix);

                if (visible(ix, iy)) {
                    Coordinate currCoord = new Coordinate(iy, ix);
                    if (currentGame.canPlaceFollowersNow() && board.getLastCoordinates().get().equals(currCoord)) {
                        for (int dy=0;dy<5;dy++) {
                            for (int dx=0;dx<5;dx++) {
                                if (board.canPlaceFollower(iy, ix, currentGame.getFollowerForType(currentGame.getCurrentFollowerSelection().get()), dy, dx)) {
                                    drawFollowerPossibility(batch, iy, ix, dy, dx);
                                }
                            }
                        }
                    }
                }
            }
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL10.GL_BLEND);

        batch.begin();
    }

    private void drawFollowerPossibility(SpriteBatch batch, Integer iy, Integer ix, int dy, int dx) {
        final int SIZE = (int)(TILESIZE / 5);
        shapeRenderer.setColor(1.0f, 0.0f, 0.0f, 0.5f);
        shapeRenderer.rect(
                mapX(ix) + dx * SIZE,
                mapY(iy) + (4-dy) * SIZE,
                SIZE,
                SIZE
        );
    }

    boolean visible(int tx, int ty) {
        return true
                && ((mapX(tx) >= topX && mapX(tx) <= bottomX) || (mapX(tx+1) >= topX && mapX(tx+1) <= bottomX))
                && ((mapY(ty) >= topY && mapY(ty) <= bottomY) || (mapY(ty+1) >= topY && mapY(ty + 1) <= bottomY))
                ;
    }

    int mapX(int tx) {
        return (int)Math.floor((tx + centerX) * TILESIZE + ((bottomX-topX) / 2)-TILESIZE/2) + topX;
    }

    int mapY(int ty) {
        ty += centerY;
        double centerPos = topY + ((bottomY - topY) / 2.0) + TILESIZE/2.0;
        return (int)Math.floor( centerPos - ty*TILESIZE );
    }

    int unmapX(int tx) {
        return (int) (Math.floor((tx-topX+(TILESIZE/2)- ((bottomX-topX) / 2) ))-centerX*TILESIZE);
    }

    int unmapY(int ty) {
        return - (ty - mapY(0) );
    }

    public Coordinate unmapInnerSpace(int x, int y) {
        Coordinate tileCoord = unmapTileSpace(x, y);

        final int SIZE = (int)Math.floor(TILESIZE/5);

        x -= mapX(tileCoord.getX());
        y -= mapY(tileCoord.getY());

        return new Coordinate(4 - (int)Math.floor(y / SIZE), (int)Math.floor(x / SIZE));
    }

    public Coordinate unmapTileSpace(int x, int y) {
        x = unmapX(x);
        y = unmapY(y);

        return new Coordinate((int)Math.ceil(y / TILESIZE), (int)Math.floor(x/TILESIZE));
    }

    public void cameraUp() {
        centerY--;
    }

    public void cameraDown() {
        centerY++;
    }

    public void cameraLeft() {
        centerX--;
    }

    public void cameraRight() {
        centerX++;
    }
}
