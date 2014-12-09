package hu.elte.szoftproj.carcassonne.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

        Optional<Tile> currentTile = currentGame.getCurrentTile();
        Optional<Rotation> currentRotation = currentGame.getCurrentTileRotation();

        ImmutableSet<Coordinate> placement = null;
        if (currentTile.isPresent()) {
            placement = gameObject.getBoard().get().whereCanBePlaced(currentTile.get(), currentRotation.get());
        }

        for (Integer iy: ImmutableSortedSet.copyOf(grid.rowKeySet())) {
            for (Integer ix : ImmutableSortedSet.copyOf(grid.columnKeySet())) {

                if (currentTile.isPresent()) {
                    maybeDrawPossible(batch, currentTile.get(), ix, iy-1, currentRotation.get(), placement);
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
        return (int)Math.floor(-(ty+centerY) * TILESIZE + ((bottomY-topY) / 2)-TILESIZE/2) + topY;
    }

    int unmapX(int tx) {
        return (int) (Math.floor((tx-topX+(TILESIZE/2)- ((bottomX-topX) / 2) ) / TILESIZE)-centerX);
    }

    int unmapY(int ty) {
        return (int) (Math.floor((ty-topY+(TILESIZE/2) - ((bottomY-topY) / 2) ) / TILESIZE)+centerY)*-1;
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
