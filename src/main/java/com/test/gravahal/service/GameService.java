package com.test.gravahal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.test.gravahal.domain.Game;
import com.test.gravahal.domain.Player;
import com.test.gravahal.infrastructure.MutexPool;
import com.test.gravahal.repository.GameRepository;
import com.test.gravahal.repository.PlayerRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:47:27 PM 

@Singleton
public class GameService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameFlowService gameFlow;
    
    @Inject
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, GameFlowService gameFlowService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gameFlow = gameFlowService;
    }

    public boolean isGameOpen(Integer gameId) {
        Preconditions.checkNotNull(gameId, "Game id cannot be Null");
        return gameRepository.get(gameId).isPresent();
    }

    public boolean isPlayerRegistered(Integer playerId) {
        Preconditions.checkNotNull(playerId, "Player id cannot be Null");
        return playerRepository.get(playerId).isPresent();
    }

    public Game startNewGame() {
        int gameId = gameRepository.getNextGameId();
        int firstPlayerId = playerRepository.getNextPlayerId();
        int secondPlayerId = playerRepository.getNextPlayerId();
        
        Game game = new Game(gameId, firstPlayerId, secondPlayerId);
        game.setActive(true);
        game.setInitialMoveOwnerId(firstPlayerId);   // first player moves first

        playerRepository.save(new Player(firstPlayerId));
        playerRepository.save(new Player(secondPlayerId));
        
        return gameRepository.save(game);
    }
    
    public Player getAssociatedPlayer(Integer playerId){
        Preconditions.checkNotNull(playerId, "Player id cannot be Null");
        return playerRepository.get(playerId).get();
    }
    
    public Optional<Game> getBoard(Integer gameId){
        Preconditions.checkNotNull(gameId, "Game id cannot be Null");
        return gameRepository.get(gameId);
    }

    public Optional<Game> makeNextMove(Integer gameId, Integer playerId, Integer pitNumber) {
        Preconditions.checkNotNull(gameId, "Game id cannot be Null");
        Preconditions.checkNotNull(playerId, "Player id cannot be Null");
        Preconditions.checkNotNull(pitNumber, "Pit selection cannot be Null");
        
        Optional<Game> opt = gameRepository.get(gameId);
        if( opt.isPresent() ){
            Game game = opt.get();
            Player p1 = playerRepository.get(game.getFirstPlayerId()).get();
            Player p2 = playerRepository.get(game.getSecondPlayerId()).get();
            
            if( game.isValidPlayer(playerId) ){
                
                // serialize request from the same player, other requests are not content on the lock
                synchronized( MutexPool.mutex(playerId) ){  
                    gameFlow.advance(game, p1, p2, playerId, pitNumber);
                    gameRepository.update(game);
                    playerRepository.update(p1);
                    playerRepository.update(p2);
                }
                
            } else {
                LOGGER.warn("Specified player, id: {} have no relation to game, id {}", playerId, gameId);
            }
            return Optional.of(game);
        } else {
            LOGGER.warn("Specified game, id: {} does not exist", gameId);
            return Optional.absent();
        }
    }
    
}
