package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.Game;
import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.screen.GameScreen;

import java.util.List;

public class GameMain extends Game {

    CarcassonneServiceProvider serviceProvider;

    String type;
    String address;
    List<String> players;

    final String gameId;

    public GameMain(CarcassonneServiceProvider serviceProvider, String type, String address, List<String> players) {
        this.serviceProvider = serviceProvider;
        this.type = type;
        this.address = address;
        this.players = players;

        if (type.equals("server")) {

        }
        if (type.equals("client")) {

        }

        System.out.println(0+" " + serviceProvider.getLobbyService() + " " + players.get(0));
        gameId = serviceProvider.getLobbyService().createNewGame(players.get(0), "basic").getId();
        System.out.println(0+"");
        for (int i=1;i<players.size();i++) {
            System.out.println(i+"");
            serviceProvider.getLobbyService().joinGame(gameId, players.get(i), false);
        }
        serviceProvider.getLobbyService().startGame(gameId);
    }

    @Override
    public void create() {


        ImmutableList.Builder<String> localPlayers = new ImmutableList.Builder<>();
        localPlayers.addAll(players);


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
