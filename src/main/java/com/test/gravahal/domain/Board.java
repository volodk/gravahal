package com.test.gravahal.domain;


// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:45:53 PM 

public final class Board {
    
    private static final int FIRST_PLAYER_INDEX = 0;
    private static final int SECOND_PLAYER_INDEX = 1;

    // internal state
    private Player[] players = new Player[2];
    private int order;
    private int gameId;
    
    public Board(int playerId) {
        
        players[FIRST_PLAYER_INDEX] = new Player();
        players[SECOND_PLAYER_INDEX] = new Player();
        
        players[FIRST_PLAYER_INDEX].setPlayerId(playerId);
        order = FIRST_PLAYER_INDEX;
    }
    
    // copying constructor
    public Board(Board other){
        players[FIRST_PLAYER_INDEX] = new Player(other.players[FIRST_PLAYER_INDEX]);
        players[SECOND_PLAYER_INDEX] = new Player(other.players[SECOND_PLAYER_INDEX]);
    }
    
    public void makeMove(int playerId, int pitNumber){
        
    }
    
    public void join(int playerId){
        
    }
    
    public static Board copyOf(Board other){
        return new Board(other); 
    }
    
}
