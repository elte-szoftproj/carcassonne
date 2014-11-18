package hu.elte.szoftproj.carcassonne.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroControl implements Control {

    public IntroControl(ApplicationControl app) {
        this.app = app;
    }
    
    @Override
    public void create() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void update() {
        // escape, space or timeout skips intro
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.SPACE) || timer > INTRO_END_TIME) {
            app.setActiveControl(app.titleMenuControl);
            return;
        }
        // set alpha
        if (timer < KEEP_DARK_TIME) {
            alpha = 0.0f;
        } else if (timer < FADE_IN_TIME) {
            alpha = (timer - KEEP_DARK_TIME) / (FADE_IN_TIME - KEEP_DARK_TIME);
        } else if (timer < FADE_OUT_TIME) {
            alpha = 1.0f - (timer - FADE_IN_TIME) / (FADE_OUT_TIME - FADE_IN_TIME);
        } else {
            alpha = 0.0f;
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        // render TODO: draw rectangle with alpha
        
    }

    @Override
    public void activate() {
        timer = 0.0f;
        alpha = 0.0f;
    }

    @Override
    public void deactivate() {
    }

    // timings
    private static final float KEEP_DARK_TIME = 0.5f;
    private static final float FADE_IN_TIME = 2.5f;
    private static final float FADE_OUT_TIME = 4.5f;
    private static final float INTRO_END_TIME = 5.0f;
    
    private float timer;
    private float alpha;
    
    
    private final ApplicationControl app;
}
