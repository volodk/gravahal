package com.test.gravahal.resource;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.test.gravahal.domain.Game;
import com.test.gravahal.domain.Player;
import com.test.gravahal.service.GameService;
import com.test.gravahal.vo.GameValueObject;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:55:47 PM 

@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class GameEndpoint {
    
    private final GameService service;
    
    @Inject
    public GameEndpoint(GameService service) {
        this.service = service;
    }
    
    @POST @Path("/game")
    public Response startGame(){
        Game g = service.startNewGame();
        Player associatedPlayer1 = service.getAssociatedPlayer(g.getFirstPlayerId());
        Player associatedPlayer2 = service.getAssociatedPlayer(g.getSecondPlayerId());
        return Response.ok(
                new GameValueObject(g, associatedPlayer1, associatedPlayer2)).build();
    }
    
    @GET @Path("/game/{game-id}")
    public Response getGameStatus(@NotNull @PathParam("game-id") Integer gameId){
        Optional<Game> opt = service.getBoard(gameId);
        if( opt.isPresent() ){ 
            Game g = opt.get();
            Player associatedPlayer1 = service
                    .getAssociatedPlayer(g.getFirstPlayerId());
            Player associatedPlayer2 = service
                    .getAssociatedPlayer(g.getSecondPlayerId());
            return Response.ok(new GameValueObject(g, associatedPlayer1, associatedPlayer2)).build();
        } else {
            return Response.status(Status.NOT_FOUND).entity("Game with id: [" + gameId + "] is not found").build();
        }
    }
    
    @POST @Path("/game/{game-id}/move/{player-id}/{pit-number}")
    public Response makeMove(
            @NotNull @PathParam("game-id") Integer gameId, 
            @NotNull @PathParam("player-id") Integer playerId,
            @NotNull @Min(1) @Max(6) @PathParam("pit-number") Integer pitNumber ){
        if( service.isPlayerRegistered(playerId) ){
            if( service.isGameOpen(gameId) ){ 
                Optional<Game> opt = service.makeNextMove(gameId, playerId, pitNumber);
                if( opt.isPresent() ){
                    Game g = opt.get();
                    Player associatedPlayer1 = service
                            .getAssociatedPlayer(g.getFirstPlayerId());
                    Player associatedPlayer2 = service
                            .getAssociatedPlayer(g.getSecondPlayerId());
                    return Response.ok(new GameValueObject(g, associatedPlayer1, associatedPlayer2)).build();
                } else {
                    return Response.notModified().entity("Game with id: [" + gameId + "] was not modified").build();
                }
            } else {
                return Response.status(Status.NOT_FOUND).entity("Game with id: [" + gameId + "] is not found").build();
            }
        } else {
            return Response.status(Status.NOT_FOUND).entity("Player with id: [" + playerId + "] is not found").build();
        }
    }
}
