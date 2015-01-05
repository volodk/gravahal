package com.test.gravahal.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;


// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:45:53 PM 

public class Board {
    
    private static final int FIRST_PLAYER_INDEX = 0;
    private static final int SECOND_PLAYER_INDEX = 1;

    @JsonProperty
    private int id;
    
    @JsonProperty
    private int nextMovePlayerIndex;
    
    @JsonProperty
    private boolean active;
    
    @JsonProperty
    private Player[] players;
    
    public Board(int gameId, Player firstPlayer, Player secondPlayer) {
        players = new Player[]{
                firstPlayer, secondPlayer 
        };
        nextMovePlayerIndex = FIRST_PLAYER_INDEX;    // first player has a right of first move
        active = true;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
    
    /**
     * Code that operates with board states (a.k.a application logic)
     * @param board - current board
     * @param playerId - what pit is a player going to use 
     * @param pitNumber - what pit is a player going to use 
     * @return reports whether a board state change went OK
     */
    public static boolean makeMove(Board board, int playerId, int pitNumber){
        Preconditions.checkNotNull(board);
        return false;
    }
    
    /**
     * Code that operates with board states (a.k.a application logic)
     * @param board - current board
     * @param playerId - who's going to move
     * @param pitNumber - what pit is a player going to use 
     * @return checks if offered move is valid
     */
    public static boolean isValidMove(Board board, int playerId, int pitNumber){
        Preconditions.checkNotNull(board);
        return resolve(board, playerId).getPits()[pitNumber] > 0;
    }

    /**
     * Code that operates with board states (a.k.a application logic)
     * @param board - current board
     * @return reports whether a current game is in progress
     */
    public static boolean isGameActing(Board board) {
        Preconditions.checkNotNull(board);
        return board.active;
    }
    
    private static Player resolve(Board board, int playerId){
        int index = FIRST_PLAYER_INDEX;
        if( board.players[SECOND_PLAYER_INDEX].getId() == playerId ){
            index = SECOND_PLAYER_INDEX;
        }
        return board.players[index];
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("active", active)
                .add("who's next", nextMovePlayerIndex)
                .add("players", Arrays.toString(players) )
            .toString();
    }
    
}
