package com.test.gravahal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.test.gravahal.domain.Game;
import com.test.gravahal.domain.Player;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 2:03:13 PM 

public class GameFlowService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GameFlowService.class);

    public void advance(Game game, Player p1, Player p2, Integer actingPlayerId, Integer pitNumber) {
        Preconditions.checkNotNull(game, "Game param cannot be Null");
        
        if( game.isActive()){
            if( isPitNonEmpty(p1, p2, actingPlayerId, pitNumber) ){
                if( isValidMoveOrdering(game, actingPlayerId) ){
                    simulateMove(game, p1, p2, actingPlayerId, pitNumber);
                    checkForWinPosition(game, choosePlayer(actingPlayerId, p1, p2) );
                } else {
                    LOGGER.warn("Game: {}, player: {}, move: {} is invalid -> this is not your turn", game, actingPlayerId,
                            pitNumber);
                }
            } else {
                LOGGER.warn("Game: {}, player: {}, move: {} is invalid -> empty pit", game, actingPlayerId, pitNumber);
            }
        } else {
            LOGGER.info("Game, id {} has been finished", game.getId());
        }
        
    }
    
    private void checkForWinPosition(Game game, Player player) {
        if( player.isWinner() ){
            game.setWinner( player.getId(), player.getGravahalField() );
        }
    }

    private void simulateMove(final Game game, final Player p1, final Player p2, final int actingPlayerId, final int pitNumber){
        // simulation
        Player p = choosePlayer(actingPlayerId, p1, p2);
        int remainder = p.pickup(pitNumber);
        boolean fillGravaHal = true;
        while ( remainder > 0 ){
            remainder = p.spreadStones(remainder, fillGravaHal, pitNumber + 1);
             
            p = chooseOpposite(p1, p2, p); // spread remaining stones among opposite GravaHal
            
            fillGravaHal = !fillGravaHal;   // cannot mark opposite palyer's gravahal
        }
        
        game.recordNextMoveOwnerId(); // change a state: set a next turn
    }

    private boolean isValidMoveOrdering(Game game, Integer actingPlayerId) {
        return game.getCurrentMoveOwnerId() == actingPlayerId;
    }
    
    private Player choosePlayer(int actingPlayerId, Player p1, Player p2){
        return p1.getId() == actingPlayerId ? p1 : p2; 
    }
    
    private Player chooseOpposite(Player p1, Player p2, Player current){
        return p1 == current ? p2 : p1;
    }

    private boolean isPitNonEmpty(Player p1, Player p2, Integer actingPlayerId, Integer pitNumber) {
       return choosePlayer(actingPlayerId, p1, p2).isPitNonEmpty(pitNumber);
    }

}
