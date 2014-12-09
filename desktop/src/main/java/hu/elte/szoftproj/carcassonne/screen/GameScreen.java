package hu.elte.szoftproj.carcassonne.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.service.Deck;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.ui.BoardCanvas;
import hu.elte.szoftproj.carcassonne.ui.GameStatusCanvas;
import hu.elte.szoftproj.carcassonne.ui.GameTextureProvider;

import java.util.HashMap;
import java.util.Optional;

public class GameScreen implements Screen, GameTextureProvider, CurrentGameInterface {


    private Game game;

    private final GameService gameService;

    private final String currentPlayerName;

    private final String gameId;

    private final HashMap<String, TextureRegion> tileTextures;
    private final SpriteBatch spriteBatch;

    private CarcassonneGame currentGame;

    private Optional<Rotation> currentTileRotation;

    private BoardCanvas boardCanvas;
    private GameStatusCanvas gameStatusCanvas;

    private MyInputProcessor inputProcessor;
    private Optional<String> currentFollowerSelection;

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
        this.currentPlayerName = playerName;
        this.gameId = gameId;

        this.boardCanvas = new BoardCanvas(100, 100, 600, 600, this, this);
        this.gameStatusCanvas = new GameStatusCanvas(0, 100, 600, 0, this);

        this.tileTextures = new HashMap<>();
        this.spriteBatch = new SpriteBatch();

        this.inputProcessor = new MyInputProcessor();

        this.currentTileRotation = Optional.empty();
        this.currentFollowerSelection = Optional.empty();
    }

    protected void updateLogic() {
        currentGame = gameService.getGameById(currentPlayerName, gameId);

        if (!currentGame.isFinished()) {
            CurrentPlayer currentPlayer = currentGame.getCurrentPlayer().get();
            if (isCurrentPlayer()) {
                if (currentPlayer.getAction().equals(GameAction.PLACE_TILE) && !currentTileRotation.isPresent()) {
                    currentTileRotation = Optional.of(Rotation.R0);
                }
                if (currentPlayer.getAction().equals(GameAction.PLACE_FOLLOWER) && !currentFollowerSelection.isPresent()) {

                }
            }
        }
    }

    @Override
    public void render(float v) {
        updateLogic();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();

        // display some info at the bottom

        boardCanvas.draw(spriteBatch, currentGame);
        gameStatusCanvas.draw(spriteBatch, currentGame);
        spriteBatch.end();
    }

    @Override
    public CarcassonneGame getCurrentGame() {
        return currentGame;
    }

    @Override
    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public boolean isCurrentPlayer() {
        if (!currentGame.isFinished()) {
            CurrentPlayer currentPlayer = currentGame.getCurrentPlayer().get();
            return (currentPlayer.getPlayer().getName().equals(getCurrentPlayerName()));
        }

        return false;
    }

    @Override
    public Optional<Rotation> getCurrentTileRotation() {
        if (isCurrentPlayer()) {
            return currentTileRotation;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Tile> getCurrentTile() {
        if (currentGame.getDeck().isPresent() && isCurrentPlayer()) {
            Deck d = currentGame.getDeck().get();

            if (d.peekNext() != null ){
                return Optional.of(d.peekNext());
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<String> getCurrentFollowerSelection() {
        return currentFollowerSelection;
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputProcessor);
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

    class MyInputProcessor implements InputProcessor {

        @Override
        public boolean keyDown(int i) {
            switch(i) {
                case Input.Keys.UP: boardCanvas.cameraUp(); break;
                case Input.Keys.DOWN: boardCanvas.cameraDown(); break;
                case Input.Keys.LEFT: boardCanvas.cameraLeft(); break;
                case Input.Keys.RIGHT: boardCanvas.cameraRight(); break;
                case Input.Keys.PAGE_UP: if (currentTileRotation.isPresent()) { currentTileRotation = Optional.of(currentTileRotation.get().prev()); } break;
                case Input.Keys.PAGE_DOWN: if (currentTileRotation.isPresent()) { currentTileRotation = Optional.of(currentTileRotation.get().next()); } break;
            }

            return false;
        }

        @Override
        public boolean keyUp(int i) {
            return false;
        }

        @Override
        public boolean keyTyped(char c) {
            return false;
        }

        @Override
        public boolean touchDown(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchUp(int i, int i1, int i2, int i3) {
            return false;
        }

        @Override
        public boolean touchDragged(int i, int i1, int i2) {
            return false;
        }

        @Override
        public boolean mouseMoved(int i, int i1) {
            return false;
        }

        @Override
        public boolean scrolled(int i) {
            return false;
        }
    }
}
