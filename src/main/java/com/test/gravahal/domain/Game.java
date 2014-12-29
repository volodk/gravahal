package com.test.gravahal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:48:27 PM 

public class Game {
    
    @JsonProperty("game-id")
    private int id;
    
    public Game(int gameId) {
        id = gameId;
    }
    
    public static Game of(int gameId){
        return new Game(gameId);
    }
    
}
