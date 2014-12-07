package hu.elte.szoftproj.carcassonne.persistence.server.rest;


import com.google.common.collect.ImmutableList;
import hu.elte.szoftproj.carcassonne.domain.Game;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.persistence.LobbyDao;
import hu.elte.szoftproj.carcassonne.persistence.dto.lobby.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;

@Component
@Path("/lobby")
public class LobbyResource {

    @Autowired
    LobbyDao dao;

    @GET
     @Produces({MediaType.APPLICATION_JSON})
     @Path("list/waiting")
     public MultiGameDto getWaitingGameList() {
        try {
            LinkedList<GameDto> retList = new LinkedList<>();

            for (Game g : dao.listWaitingGames()) {
                retList.push(gameToDto(g));
            }

            return new MultiGameDto(retList, "OK");
        } catch (Exception e) {
            return new MultiGameDto(ImmutableList.of(), "ERROR_UNKNOWN");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("list/active")
    public MultiGameDto getActiveGameList() {
        try {
            LinkedList<GameDto> retList = new LinkedList<>();

            for (Game g : dao.listActiveGames()) {
                retList.push(gameToDto(g));
            }

            return new MultiGameDto(retList, "OK");
        } catch (Exception e) {
            return new MultiGameDto(ImmutableList.of(), "ERROR_UNKNOWN");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("create")
    public SingleGameDto createGame(GameCreateActionDto gcd) {
        try {
            return new SingleGameDto(gameToDto(dao.createNewGame(gcd.getPlayerName(), gcd.getBoardName())), "OK");
        } catch (IllegalArgumentException e) {
            return new SingleGameDto(null, "ERROR_NO_SUCH_BOARD");
        } catch (Exception e) {
            return new SingleGameDto(null, "ERROR_UNKNOWN");
        }
    }

    @POST
     @Produces({MediaType.APPLICATION_JSON})
     @Consumes({MediaType.APPLICATION_JSON})
     @Path("join/ai")
     public SingleGameDto joinGameWithAi(GameJoinActionDto gcd) {
        try {
            return new SingleGameDto(gameToDto(dao.joinGame(gcd.getGameId(), gcd.getPlayerName(), true)), "OK");
        } catch (IllegalArgumentException e) {
            // TODO: response is based on a dao string, that's bad!
            return new SingleGameDto(null, "ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new SingleGameDto(null, "ERROR_UNKNOWN");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("join")
    public SingleGameDto joinGame(GameJoinActionDto gcd) {
        try {
            return new SingleGameDto(gameToDto(dao.joinGame(gcd.getGameId(), gcd.getPlayerName(), false)), "OK");
        } catch (IllegalArgumentException e) {
            // TODO: response is based on a dao string, that's bad!
            return new SingleGameDto(null, "ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new SingleGameDto(null, "ERROR_UNKNOWN");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("start")
    public SingleGameDto startGame(GameIdDto gameId) {
        try {
            return new SingleGameDto(gameToDto(dao.startGame(gameId.getGameId())), "OK");
        } catch (IllegalArgumentException e) {
            // TODO: response is based on a dao string, that's bad!
            return new SingleGameDto(null, "ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new SingleGameDto(null, "ERROR_UNKNOWN");
        }
    }

    public GameDto gameToDto(Game g) {
        ImmutableList.Builder<PlayerDto> builder = new ImmutableList.Builder<>();

        for (Player p: g.getPlayers()) {
            builder.add(new PlayerDto(p.getName(), p.getType()));
        }

        return new GameDto(g.getId(), builder.build(), g.getStatus());
    }
}
