package hu.elte.szoftproj.carcassonne.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.*;
import hu.elte.szoftproj.carcassonne.service.Deck;
import hu.elte.szoftproj.carcassonne.service.GameService;
import hu.elte.szoftproj.carcassonne.ui.BoardCanvas;
import hu.elte.szoftproj.carcassonne.ui.GameStatusCanvas;
import hu.elte.szoftproj.carcassonne.ui.GameTextureProvider;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.HashMap;
import java.util.Optional;

public class GameScreen implements Screen, GameTextureProvider, CurrentGameInterface {

    static final String[] POSSIBLE_FOLLOWERS = { "basic", "big" };

    private Game game;

    private final GameService gameService;

    private String currentPlayerName;

    private final String gameId;

    private final HashMap<String, TextureRegion> tileTextures;
    private final SpriteBatch spriteBatch;

    private CarcassonneGame currentGame;

    private Optional<Rotation> currentTileRotation;

    private BoardCanvas boardCanvas;
    private GameStatusCanvas gameStatusCanvas;

    private MyInputProcessor inputProcessor;
    private Optional<String> currentFollowerSelection;
    private Optional<Integer> currentFollowerId;


    private final ImmutableList<String> localPlayers;

    @Override
    public TextureRegion getTextureFor(String name) {
        if(!tileTextures.containsKey(name)) {
            tileTextures.put(name, new TextureRegion(new Texture(Gdx.files.internal(name+".png")), 0, 0, 90, 90));
        }
        return tileTextures.get(name);
    }

    public GameScreen(Game game, GameService gameService, ImmutableList<String> players, String gameId) {
        this.game = game;
        this.gameService = gameService;
        this.localPlayers = players;
        this.currentPlayerName = localPlayers.get(0);
        this.gameId = gameId;

        this.boardCanvas = new BoardCanvas(100, 100, 450, 450, this, this);
        this.gameStatusCanvas = new GameStatusCanvas(0, 100, 600, 0, this);

        this.tileTextures = new HashMap<>();
        this.spriteBatch = new SpriteBatch();

        this.inputProcessor = new MyInputProcessor();

        this.currentTileRotation = Optional.empty();
        this.currentFollowerSelection = Optional.empty();
        this.currentFollowerId = Optional.empty();
    }

    protected void updateLogic() {
        currentGame = gameService.getGameById(currentPlayerName, gameId);

        if (currentGame.getCurrentPlayer().isPresent()) {
            String playerName = currentGame.getCurrentPlayer().get().getPlayer().getName();
            if (localPlayers.contains(playerName) && !currentPlayerName.equals(playerName)) {
                currentPlayerName = playerName;

                currentGame = gameService.getGameById(currentPlayerName, gameId);
            }
        }

        if (!currentGame.isFinished()) {
            CurrentPlayer currentPlayer = currentGame.getCurrentPlayer().get();
            if (isCurrentPlayer()) {
                if (currentPlayer.getAction().equals(GameAction.PLACE_TILE)) {
                    if (!currentTileRotation.isPresent()) {
                        currentTileRotation = Optional.of(Rotation.R0);
                    }
                } else {
                    currentTileRotation = Optional.empty();
                }
                if (currentPlayer.getAction().equals(GameAction.PLACE_FOLLOWER)) {
                    if (!currentFollowerSelection.isPresent()) {
                        currentFollowerSelection = Optional.of("basic");
                        currentFollowerId = Optional.of(0);
                    }
                } else {
                    currentFollowerSelection = Optional.empty();
                    currentFollowerId = Optional.empty();
                }
            } else {
                currentFollowerSelection = Optional.empty();
                currentFollowerId = Optional.empty();
                currentTileRotation = Optional.empty();
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
        if (
                currentGame.getDeck().isPresent() &&
                        isCurrentPlayer() &&
                        getCurrentGame().getCurrentPlayer().get().getAction().equals(GameAction.PLACE_TILE)
        ) {
            Deck d = currentGame.getDeck().get();

            if (d.getRemainingPieceCount() != 0){
                return Optional.of(d.peekNext());
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean canPlaceFollowersNow() {
        return true
                && !currentGame.isFinished()
                && currentGame.getCurrentPlayer().get().getAction().equals(GameAction.PLACE_FOLLOWER)
                && isCurrentPlayer()
                ;
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

    @Override
    public Optional<Follower> getFollowerForType(String type) {
        ImmutableList<Follower> l = currentGame.getBoard().get().notUsedFollowers(currentGame.getCurrentPlayer().get().getPlayer().getFollowersOfType(type));
        if (l.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(l.get(0));
        }
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
                case Input.Keys.Q: {
                    if (currentFollowerSelection.isPresent() && !currentGame.getCurrentPlayer().get().getPlayer().getFollowersOfType("basic").isEmpty()) {
                        currentFollowerSelection = Optional.of("basic");
                    }
                    break;
                }
                case Input.Keys.W: {
                    if (currentFollowerSelection.isPresent() && !currentGame.getCurrentPlayer().get().getPlayer().getFollowersOfType("big").isEmpty()) {
                        currentFollowerSelection = Optional.of("big");
                    }
                    break;
                }
                case Input.Keys.P: {
                    if (isCurrentPlayer() && currentGame.canPlaceFollowerNow()) {
                        try {
                            currentGame = gameService.dontPlaceFollower(currentGame, currentGame.getPlayerByName(currentPlayerName));
                        } catch (Exception e) {

                        }
                    }
                }
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
        public boolean touchDown(int x, int y, int pointer, int button) {

            if (isCurrentPlayer() && currentGame.canPlaceTileNow()) {
                Coordinate atTile = boardCanvas.unmapTileSpace(x, Gdx.graphics.getHeight() - y);
                try {
                    currentGame = gameService.placeTile(currentGame, currentGame.getPlayerByName(currentPlayerName), getCurrentTile().get(), getCurrentTileRotation().get(), atTile.getY(), atTile.getX());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (isCurrentPlayer() && currentGame.canPlaceFollowerNow()) {
                Coordinate atTile = boardCanvas.unmapTileSpace(x, Gdx.graphics.getHeight() - y);
                Coordinate atInner = boardCanvas.unmapInnerSpace(x, Gdx.graphics.getHeight() - y);
                try {
                    Optional<Follower> f = getFollowerForType(currentFollowerSelection.get());
                    if (f.isPresent()) {
                        currentGame = gameService.placeFollower(currentGame, currentGame.getPlayerByName(currentPlayerName), f.get(), atTile.getY(), atTile.getX(), atInner.getY(), atInner.getX());
                    }
                } catch (Exception e) {

                }
            }

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
