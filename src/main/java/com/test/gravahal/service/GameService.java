package com.test.gravahal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.test.gravahal.domain.Game;
import com.test.gravahal.domain.Player;
import com.test.gravahal.repository.GameRepository;
import com.test.gravahal.repository.PlayerRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:47:27 PM 

@Singleton
public class GameService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    
    @Inject
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }
    
    public Integer getUniquePlayerId(){
        return playerRepository.getNextPlayerId();
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
        
        Player firstPlayer = playerRepository.save(new Player(firstPlayerId));
        Player secondPlayer = playerRepository.save(new Player(secondPlayerId));
        
        Game newGameBoard = new Game(gameId, firstPlayer, secondPlayer);
        newGameBoard.setActive(true);
        newGameBoard.setOrder(0);   // first player moves first
        
        return gameRepository.save(newGameBoard);
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
        if( opt.isPresent()){
            Game game = opt.get();
            
            // check all logical preconditions. If there were more then 4-5 conditions this code might be restructured with Chain-of-responsibility    
            
            if( !game.isActive() ){
                LOGGER.info("Game, id {} has been finished", gameId);
            } else if( game.isPitEmpty(playerId, pitNumber) ){
                LOGGER.warn("board: {}, player: {}, move: {} is invalid -> empty pit", game, playerId, pitNumber);
            }  else if( game.isWrongMoveOrder(playerId, pitNumber) ){
                LOGGER.warn("board: {}, player: {}, move: {} is invalid -> this is not your turn", game, playerId, pitNumber);
            } else {    // all conditions are satisfied, make a step 
                game.advance(playerId, pitNumber);
                gameRepository.update(game);
            }
            return Optional.of(game);
        } else {
            LOGGER.warn("Specified game, id: {} does not exist", gameId);
            return Optional.absent();
        }
    }
    
}
