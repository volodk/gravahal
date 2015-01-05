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
import com.test.gravahal.domain.Board;
import com.test.gravahal.service.GameService;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:55:47 PM 

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
public class GameEndpoint {
    
    private final GameService service;
    
    @Inject
    public GameEndpoint(GameService service) {
        this.service = service;
    }
    
    @POST @Path("/start")
    public Response startGame(){
        Board b = service.startNewGame();
        return Response.ok(b).build();
    }
    
    @GET @Path("/board/{game-id}")
    public Response getBoardStatus(@NotNull @PathParam("game-id") Integer gameId){
        Optional<Board> opt = service.getBoard(gameId);
        if( opt.isPresent() ){ 
            return Response.ok( opt.get() ).build();
        } else {
            return Response.status(Status.NOT_FOUND).entity("Game with id: " + gameId + " is not found").build();
        }
    }
    
    @POST @Path("/move/{game-id}/{player-id}/{pit-number}")
    public Response makeMove(
            @NotNull @PathParam("game-id") Integer gameId, 
            @NotNull @PathParam("player-id") Integer playerId,
            @NotNull @Min(1) @Max(6) @PathParam("pit-number") Integer pitNumber ){
        if( service.isPlayerRegistered(playerId) ){
            if( service.isGameOpen(gameId) ){ 
                return Response.ok( service.makeNextMove(gameId, playerId, pitNumber) ).build();
            } else {
                return Response.status(Status.NOT_FOUND).entity("Game with id: " + gameId + " is not found").build();
            }
        } else {
            return Response.status(Status.NOT_FOUND).entity("Player with id: " + playerId + " is not found").build();
        }
    }
}
