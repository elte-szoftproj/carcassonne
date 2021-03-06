package hu.elte.szoftproj.carcassonne.service.impl.rest;

import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.CarcassonneGame;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.persistence.client.LobbyRestClient;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.*;
import hu.elte.szoftproj.carcassonne.service.DeckFactory;
import hu.elte.szoftproj.carcassonne.service.RemoteLobbyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class LobbyServiceImplRest implements RemoteLobbyService {

    LobbyRestClient client;

    @Autowired
    DeckFactory deckFactory;

    public LobbyServiceImplRest() {
    }

    @Override
    public void setClient(LobbyRestClient client) {
        this.client = client;
    }

    @Override
    public CarcassonneGame createNewGame(String initialPlayerName, String boardName) {
        SingleGameDto gdto = client.createGame(new GameCreateActionDto(initialPlayerName, boardName));

        if (!gdto.getStatus().equals("OK")) {
            throw new IllegalArgumentException(gdto.getStatus());
        }

        return convertToGameObject(gdto.getGame());
    }

    @Override
    public List<CarcassonneGame> listActiveGames() {
        MultiGameDto mgdto = client.getActiveGameList();

        if (!mgdto.getStatus().equals("OK")) {
            throw new IllegalArgumentException(mgdto.getStatus());
        }

        ImmutableList.Builder<CarcassonneGame> listBuilder = new ImmutableList.Builder<>();

        for(GameDto gd: mgdto.getGames()) {
            listBuilder.add(convertToGameObject(gd));
        }

        return listBuilder.build();
    }

    @Override
    public List<CarcassonneGame> listWaitingGames() {
        MultiGameDto mgdto = client.getWaitingGameList();

        if (!mgdto.getStatus().equals("OK")) {
            throw new IllegalArgumentException(mgdto.getStatus());
        }

        ImmutableList.Builder<CarcassonneGame> listBuilder = new ImmutableList.Builder<>();

        for(GameDto gd: mgdto.getGames()) {
            listBuilder.add(convertToGameObject(gd));
        }

        return listBuilder.build();
    }

    @Override
    public CarcassonneGame joinGame(String gameId, String player, boolean ai) {
        SingleGameDto gdto = null;
        if (ai) {
            gdto = client.joinGameWithAi(new GameJoinActionDto(player, gameId));
        } else {
            gdto = client.joinGame(new GameJoinActionDto(player, gameId));
        }

        if (!gdto.getStatus().equals("OK")) {
            throw new IllegalArgumentException(gdto.getStatus());
        }

        return convertToGameObject(gdto.getGame());
    }

    @Override
    public CarcassonneGame startGame(String gameId) {
        SingleGameDto gdto = client.startGame(new GameIdDto(gameId));

        if (!gdto.getStatus().equals("OK")) {
            throw new IllegalArgumentException(gdto.getStatus());
        }

        return convertToGameObject(gdto.getGame());
    }


    private CarcassonneGame convertToGameObject(GameDto gdto) {

        ImmutableList.Builder<Player> listBuilder = new ImmutableList.Builder<Player>();

        for(PlayerDto pd: gdto.getPlayerList()) {
            listBuilder.add(new Player(pd.getName()));
        }

        CarcassonneGame g = new CarcassonneGame(
                gdto.getId(),
                listBuilder.build(),
                Optional.empty(),
                Optional.empty(),
                gdto.getStatus(),
                Optional.of(deckFactory.getDeck(gdto.getDeckName()))
            );

        return g;
    }
}
