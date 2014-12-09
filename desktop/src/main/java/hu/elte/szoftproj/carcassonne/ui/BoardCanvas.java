package hu.elte.szoftproj.carcassonne.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableTable;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.Square;

public class BoardCanvas {

    static final double TILESIZE = 90.0f;

    public final int topX;
    public final int topY;
    public final int bottomX;
    public final int bottomY;

    private final GameTextureProvider textureProvider;

    public int centerX;
    public int centerY;

    public BoardCanvas(int topX, int topY, int bottomX, int bottomY, GameTextureProvider textureProvider) {
        this.topX = topX;
        this.topY = topY;
        this.bottomX = bottomX;
        this.bottomY = bottomY;
        this.textureProvider = textureProvider;
        centerX = 0;
        centerY = 0;
    }

    public void draw(SpriteBatch batch, CarcassonneGame gameObject) {
        ImmutableTable<Integer, Integer, Square> grid = gameObject.getBoard().get().getGrid();

        for (Integer iy: ImmutableSortedSet.copyOf(grid.rowKeySet())) {
            for (Integer ix : ImmutableSortedSet.copyOf(grid.columnKeySet())) {

                Square s = grid.get(iy, ix);

                if (visible(ix, iy)) {
                    batch.draw(
                            textureProvider.getTextureFor(s.getTile().getName()),
                            mapX(ix),
                            mapY(iy),
                            45.0f,
                            45.0f,
                            90.0f,
                            90.0f,
                            1.0f,
                            1.0f,
                            360-s.getTileRotation().getDegrees()
                    );
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
