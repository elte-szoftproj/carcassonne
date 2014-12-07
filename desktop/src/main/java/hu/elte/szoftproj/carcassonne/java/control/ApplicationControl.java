package hu.elte.szoftproj.carcassonne.java.control;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ApplicationControl implements ApplicationListener {

    // list of possible controls
    public final GameControl gameControl = new GameControl(this);
    public final IntroControl introControl = new IntroControl(this);
    public final TitleMenuControl titleMenuControl = new TitleMenuControl(this);
    public final PauseMenuControl pauseMenuControl = new PauseMenuControl(this);
    
    @Override
    public void create() {
        // create default camera
        camera = new OrthographicCamera(1280, 1024);
        // create batch
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        // create controls
        gameControl.create();
        introControl.create();
        titleMenuControl.create();
        pauseMenuControl.create();
        // TODO: add welcome screen
        // set active control
        activeControl = titleMenuControl;
        titleMenuControl.activate();
    }

    @Override
    public void resize(int h, int w) {
        // TODO: handle resize
    }

    @Override
    public void render() {
        // update active control
        activeControl.update();
        // render active control
        batch.begin();
        activeControl.render(batch);
        batch.end();
    }

    @Override
    public void pause() {
        if (activeControl == gameControl) {
            setActiveControl(pauseMenuControl);
        }
    }

    @Override
    public void resume() {
        if (activeControl == pauseMenuControl) {
            setActiveControl(gameControl);
        }
    }

    @Override
    public void dispose() {
        // dispose controls
        gameControl.dispose();
        introControl.dispose();
        titleMenuControl.dispose();
        pauseMenuControl.dispose();
    }
    
    public void setActiveControl(Control control) {
        activeControl.deactivate();
        activeControl = control;
        activeControl.activate();
    }
    
    // default camera
    Camera camera;
    // sprite batch for rendering
    SpriteBatch batch;
    
    // currently active control
    private Control activeControl;
}
