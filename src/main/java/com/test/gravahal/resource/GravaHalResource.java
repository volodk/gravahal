package com.test.gravahal.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.test.gravahal.domain.Board;
import com.test.gravahal.domain.Game;
import com.test.gravahal.service.GravaHalService;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:55:47 PM 

@Path("/game/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GravaHalResource {
    
    private GravaHalService service;
    
    public GravaHalResource(GravaHalService service) {
        this.service = service;
    }
    
    @GET
    public Game getNewGameId(){
        int nextGameId = service.getNextGameId();
        return Game.of(nextGameId);
    }
    
    @GET
    @Path("/{id}/")
    public Board getBoardStatus(){
        return new Board();
    }
    
    @POST
    @Path("/{id}/")
    public Response startNewGame( @PathParam("id") int gameId ){
        return Response.ok().build();
    }
    
    @POST
    @Path("/{id}/{player-id}/")
    public Response joinGame( @PathParam("id") int gameId, @PathParam("player-id") int playerId ){
        return Response.ok().build();
    }
    
    @POST
    @Path("/{id}/pit/{selection}/")
    public Board makeMove(@PathParam("id") int gameId, @PathParam("selection") int pitNumber){
        return new Board();
    }
}
