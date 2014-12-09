package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.Game;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.Rotation;
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

        CarcassonneGame g =serviceProvider.getGameService().getGameById("Player1", gameId);
        serviceProvider.getGameService().placeTile(
                g,
                g.getPlayerByName("Player1"),
                g.getDeck().get().peekNext(),
                Rotation.R180,
                -1,
                0
                );

        setScreen(new GameScreen(
                this,
                serviceProvider.getGameService(),
                "Player1",
                gameId
        ));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
