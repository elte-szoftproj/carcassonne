package hu.elte.szoftproj.carcassonne;

import com.badlogic.gdx.Game;
import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.screen.GameScreen;

import java.util.List;
import java.util.Scanner;

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

        System.out.println(type);

        if (type.equals("server")) {
            try {
                serviceProvider.getServer().start();
                serviceProvider.getGameService().getRemote().setClient(serviceProvider.getClientFactory().getGameClient("http://localhost:8080"));
                serviceProvider.getLobbyService().getRemote().setClient(serviceProvider.getClientFactory().getLobbyClient("http://localhost:8080"));
                serviceProvider.getGameService().switchToRemote();
                serviceProvider.getLobbyService().switchToRemote();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (type.equals("client")) {
            serviceProvider.getGameService().getRemote().setClient(serviceProvider.getClientFactory().getGameClient(address));
            serviceProvider.getLobbyService().getRemote().setClient(serviceProvider.getClientFactory().getLobbyClient(address));
            serviceProvider.getGameService().switchToRemote();
            serviceProvider.getLobbyService().switchToRemote();
        }

        System.out.println(0+" " + serviceProvider.getLobbyService() + " " + players.get(0));
        if (!type.equals("client")) {
            gameId = serviceProvider.getLobbyService().createNewGame(players.get(0), "basic").getId();
        } else {
            gameId = serviceProvider.getLobbyService().listWaitingGames().get(0).getId();
            System.out.println("Connecting to game: " + gameId);
        }
        System.out.println(0+"");
        for (int i= type.equals("client") ? 0 : 1;i<players.size();i++) {
            System.out.println(i+"");
            serviceProvider.getLobbyService().joinGame(gameId, players.get(i), false);
        }
        if (type.equals("server")) {
            System.out.println("Waiting 10 sec for players...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!type.equals("client")) {
            serviceProvider.getLobbyService().startGame(gameId);
        } else {
            while (serviceProvider.getLobbyService().listWaitingGames().size() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

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
