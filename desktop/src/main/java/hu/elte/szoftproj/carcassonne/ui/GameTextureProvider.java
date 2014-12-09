package hu.elte.szoftproj.carcassonne.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface GameTextureProvider {
    public TextureRegion getTextureFor(String name);
}
