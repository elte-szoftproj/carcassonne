package hu.elte.szoftproj.carcassonne.persistence.server.rest;

import hu.elte.szoftproj.carcassonne.domain.Follower;
import hu.elte.szoftproj.carcassonne.domain.Game;
import hu.elte.szoftproj.carcassonne.domain.Player;
import hu.elte.szoftproj.carcassonne.persistence.GameConverter;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.ActionDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.FollowerDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.GameDetailDto;
import hu.elte.szoftproj.carcassonne.persistence.dto.game.PlaceDto;
import hu.elte.szoftproj.carcassonne.service.impl.GameServiceImplWDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/game")
public class GameResource {

    @Autowired
    GameConverter converter;

    @Autowired
    GameServiceImplWDao gameService;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("get")
    public GameDetailDto getGameById(ActionDto params) {
        try {
            return converter.fromDao(params.getPlayerName(), gameService.getGameById(params.getPlayerName(), params.getGameId()));
        } catch (IllegalArgumentException e) {
            return new GameDetailDto("ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new GameDetailDto("ERROR_UNKNOWN");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("place/tile")
    public GameDetailDto placeTile(PlaceDto placing) {
        try {
            Game g = gameService.getGameById(placing.getPlayerName(), placing.getGameId());
            return converter.fromDao(placing.getPlayerName(), gameService.placeTile(
                    g,
                    g.getPlayerByName(placing.getPlayerName()),
                    g.getDeck().get().peekNext(),
                    placing.getRot(),
                    placing.getY(),
                    placing.getX()
            ));
        } catch (IllegalArgumentException e) {
            return new GameDetailDto("ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new GameDetailDto("ERROR_UNKNOWN");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("place/follower")
    public GameDetailDto placeFollower(FollowerDto placing) {
        try {
            Game g = gameService.getGameById(placing.getPlayerName(), placing.getGameId());
            Player p = g.getPlayerByName(placing.getPlayerName());
            Follower f = null;
            try {
                f = g.getBoard().get().notUsedFollowers(p.getFollowersOfType(placing.getFollowerType())).get(0);
            } catch(Exception e) {
                throw new IllegalArgumentException("NO_SUCH_FOLLOWER");
            }
            return converter.fromDao(placing.getPlayerName(), gameService.placeFollower(
                    g,
                    g.getPlayerByName(placing.getPlayerName()),
                    f,
                    placing.getY(),
                    placing.getX(),
                    placing.getDy(),
                    placing.getDx()
            ));
        } catch (IllegalArgumentException e) {
            return new GameDetailDto("ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new GameDetailDto("ERROR_UNKNOWN");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("skip/follower")
    public GameDetailDto skipFollower(ActionDto placing) {
        try {
            Game g = gameService.getGameById(placing.getPlayerName(), placing.getGameId());
            Player p = g.getPlayerByName(placing.getPlayerName());
            return converter.fromDao(placing.getPlayerName(), gameService.dontPlaceFollower(g,
                    g.getPlayerByName(placing.getPlayerName())
            ));
        } catch (IllegalArgumentException e) {
            return new GameDetailDto("ERROR_"+e.getMessage());
        } catch (Exception e) {
            return new GameDetailDto("ERROR_UNKNOWN");
        }
    }

}
