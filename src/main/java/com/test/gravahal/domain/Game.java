package com.test.gravahal.domain;

import com.google.common.base.Objects;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:45:53 PM 

public class Game {

    private int id;
    private int moveOwnerId;
    private int winner = -1, score = 0;
    private int firstPlayer, secondPlayer;
    private boolean active;
    
    private long version;   // for optimistic locking on real storage
    
    public Game(int gameId, int firstPlayer, int secondPlayer) {
        this.id = gameId;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
    
    public int getId() {
        return id;
    }
    
    public int getFirstPlayerId() {
        return firstPlayer;
    }
    
    public int getSecondPlayerId() {
        return secondPlayer;
    }

    public void recordNextMoveOwnerId() {
        moveOwnerId = firstPlayer ^ secondPlayer ^ moveOwnerId;
    }
    
    public int getCurrentMoveOwnerId(){
        return moveOwnerId;
    }
    
    public void setInitialMoveOwnerId(int playerId) {
        moveOwnerId = playerId;
    }

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean gameActive) {
        this.active = gameActive;
    }
    
    public void setWinner(int playerId, int score){
        this.winner = playerId;
        this.score = score;
    }
    
    public int getWinner(){
        return winner;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean isValidPlayer(int playerId){
        return firstPlayer == playerId || secondPlayer == playerId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("active", active)
                .add("who's next", moveOwnerId)
                .add("player 1", firstPlayer )
                .add("player 2", secondPlayer )
            .toString();
    }
    
}
