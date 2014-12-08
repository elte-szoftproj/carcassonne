package hu.elte.szoftproj.carcassonne.persistence.client;

import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.*;


public interface LobbyRestClient {
    MultiGameDto getWaitingGameList();

    MultiGameDto getActiveGameList();

    SingleGameDto createGame(GameCreateActionDto gcd);

    SingleGameDto joinGameWithAi(GameJoinActionDto gcd);

    SingleGameDto joinGame(GameJoinActionDto gcd);

    SingleGameDto startGame(GameIdDto gameId);
}
