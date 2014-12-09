package hu.elte.szoftproj.carcassonne.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.ui.BoardCanvas;
import hu.elte.szoftproj.carcassonne.ui.GameTextureProvider;

import java.util.HashMap;

public class GameScreen implements Screen, GameTextureProvider {

    private Game game;

    private final GameService gameService;

    private final String playerName;

    private final String gameId;

    private final HashMap<String, TextureRegion> tileTextures;
    private final SpriteBatch spriteBatch;

    private CarcassonneGame currentGame;

    private BoardCanvas boardCanvas;

    @Override
    public TextureRegion getTextureFor(String name) {
        if(!tileTextures.containsKey(name)) {
            tileTextures.put(name, new TextureRegion(new Texture(Gdx.files.internal(name+".png")), 0, 0, 90, 90));
        }
        return tileTextures.get(name);
    }

    public GameScreen(Game game, GameService gameService, String playerName, String gameId) {
        this.game = game;
        this.gameService = gameService;
        this.playerName = playerName;
        this.gameId = gameId;
        this.boardCanvas = new BoardCanvas(100, 100, 600, 600, this);

        this.tileTextures = new HashMap<>();
        this.spriteBatch = new SpriteBatch();
    }

    protected void updateLogic() {
        currentGame = gameService.getGameById(playerName, gameId);
    }

    @Override
    public void render(float v) {
        updateLogic();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        boardCanvas.draw(spriteBatch, currentGame);
        spriteBatch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        for(TextureRegion t: tileTextures.values()) {
            t.getTexture().dispose();
        }
        tileTextures.clear();
    }
}
