package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.Game;
import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.screen.GameScreen;

public class GameMain extends Game {

    CarcassonneServiceProvider serviceProvider;

    public GameMain(CarcassonneServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void create() {
        String gameId = serviceProvider.getLobbyService().createNewGame("Player1", "test").getId();
        serviceProvider.getLobbyService().joinGame(gameId, "Player2", false);
        serviceProvider.getLobbyService().startGame(gameId);

        ImmutableList.Builder<String> localPlayers = new ImmutableList.Builder<>();
        localPlayers.add("Player1");
        localPlayers.add("Player2");

        CarcassonneGame g =serviceProvider.getGameService().getGameById("Player1", gameId);

        setScreen(new GameScreen(
                this,
                serviceProvider.getGameService(),
                localPlayers.build(),
                gameId
        ));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
