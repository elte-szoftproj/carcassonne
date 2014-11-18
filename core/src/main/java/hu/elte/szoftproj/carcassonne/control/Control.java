package hu.elte.szoftproj.carcassonne.control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Control {
    
    void create();
    
    void dispose();
    
    void update();
    
    void render(SpriteBatch batch);
    
    void activate();
    
    void deactivate();

}
