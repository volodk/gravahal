package com.test.gravahal.resource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
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

    @GET @Path("/new-player-id")
    public Response getPlayerId(){
        return Response.ok( service.getUniquePlayerId() ).build();
    }
    
    @POST @Path("/start/{player-id}")
    public Response startGame( @PathParam("player-id") int playerId ){
        if( service.isPlayerRegistered(playerId)){
            int gameId = service.startNewGame(playerId);
            return Response.ok(gameId).build();
        } else {
            return Response.serverError().entity("Player with id: " + playerId + " does not exist. Please register before play").build();
        }
    }
    
    @POST @Path("/join/{game-id}/{player-id}")
    public Response join(@PathParam("game-id") int gameId, @PathParam("player-id") int playerId ){
        if( service.isPlayerRegistered(playerId)){
            if( service.isGameOpen(gameId) ){ 
                service.join(gameId, playerId);
            } else {
                return Response.serverError().entity("Game with id: " + gameId + " is not open so far").build();
            }
        } else {
            return Response.serverError().entity("Player with id: " + playerId + " does not exist. Please register before play").build();
        }
        return Response.ok().build();
    }
    
    @GET @Path("/board/{game-id}")
    public Response getBoardStatus(@PathParam("game-id") int gameId){
        if( service.isGameOpen(gameId) ){ 
            return Response.ok(service.getBoard(gameId)).build();
        } else {
            return Response.serverError().entity("Game with id: " + gameId + " is not open so far").build();
        }
    }
    
    @POST @Path("/move/{game-id}/{player-id}/{pit-number}")
    public Response makeMove(   @PathParam("game-id") int gameId, 
                                @PathParam("player-id") int playerId,
                                @PathParam("pit-number") int pitNumber ){
        if( service.isPlayerRegistered(playerId)){
            if( service.isGameOpen(gameId) ){ 
                return Response.ok( service.makeNextMove(gameId, playerId, pitNumber) ).build();
            } else {
                return Response.serverError().entity("Game with id: " + gameId + " is not open so far").build();
            }
        } else {
            return Response.serverError().entity("Player with id: " + playerId + " does not exist. Please register before play").build();
        }
    }
}
