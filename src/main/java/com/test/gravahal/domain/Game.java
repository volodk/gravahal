package com.test.gravahal.domain;

import java.util.Arrays;

import com.google.common.base.Objects;


// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:45:53 PM 

public class Game {

    private int id;
    private int order;
    private boolean active;
    private int winner = -1;
    private Player[] players;
    
    public Game(int gameId, Player firstPlayer, Player secondPlayer) {
        players = new Player[]{ firstPlayer, secondPlayer };
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean gameActive) {
        this.active = gameActive;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
    
    public boolean isPitEmpty(Integer playerId, Integer pitNumber) {
        int index = resolve(playerId);
        return players[index].countStones(pitNumber) == 0;
    }
    
    public boolean isWrongMoveOrder(Integer playerId, Integer pitNumber) {
        int index = resolve(playerId);
        return order != index;
    }

    public void advance(Integer playerId, Integer pitNumber) {
        int index = resolve(playerId);
        Player fp = players[index];
        
        int stones = fp.pickup(pitNumber);
        for(int i = pitNumber + 1; i <= 6; i++){
            fp.incrementPit(i);
            stones -= 1;
        }
        Player sp = players[1 - index];
        int i = 1;
        while( stones > 0 ){
            sp.incrementPit(i);
            stones -= 1;
        }
        
        order = 1 - order;  // allow the second player to play
    }
    
    public int getWinner(){
        return winner;
    }
    
    private int resolve(int playerId){
        return ( players[0].getId() == playerId ) ? 0 : 1;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("active", active)
                .add("who's next", order)
                .add("players", Arrays.toString(players) )
            .toString();
    }
    
}
