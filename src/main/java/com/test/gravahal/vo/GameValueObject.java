package com.test.gravahal.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.gravahal.domain.Game;
import com.test.gravahal.domain.Player;

public class GameValueObject {
    
    private Game game;
    private Player first, second;
    
    public GameValueObject(Game game, Player first, Player second) {
        this.game = game;
        this.first = first;
        this.second = second;
    }
    
    @JsonProperty("board")
    public Game getGame() {
        return game;
    }
    
    @JsonProperty("player-1")
    public Player getFirst() {
        return first;
    }
    
    @JsonProperty("player-2")
    public Player getSecond() {
        return second;
    }
}
