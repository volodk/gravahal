package com.test.gravahal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.test.gravahal.domain.Board;
import com.test.gravahal.domain.Player;
import com.test.gravahal.repository.BoardRepository;
import com.test.gravahal.repository.PlayerRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:47:27 PM 

@Singleton
public class GameService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    
    private final BoardRepository boardRepository;
    private final PlayerRepository playerRepository;
    
    @Inject
    public GameService(BoardRepository gameRepository, PlayerRepository playerRepository) {
        this.boardRepository = gameRepository;
        this.playerRepository = playerRepository;
    }
    
    public Integer getUniquePlayerId(){
        return playerRepository.getNextPlayerId();
    }

    public boolean isGameOpen(Integer gameId) {
        Preconditions.checkNotNull(gameId, "Game id cannot be Null");
        return boardRepository.get(gameId).isPresent();
    }

    public boolean isPlayerRegistered(Integer playerId) {
        Preconditions.checkNotNull(playerId, "Player id cannot be Null");
        return playerRepository.get(playerId).isPresent();
    }

    public Board startNewGame() {
        int gameId = boardRepository.getNextGameId();
        int firstPlayerId = playerRepository.getNextPlayerId();
        int secondPlayerId = playerRepository.getNextPlayerId();
        
        Player firstPlayer = playerRepository.save(new Player(firstPlayerId));
        Player secondPlayer = playerRepository.save(new Player(secondPlayerId));
        
        Board newGameBoard = new Board(gameId, firstPlayer, secondPlayer);
        return boardRepository.save(newGameBoard);
    }
    
    public Optional<Board> getBoard(Integer gameId){
        Preconditions.checkNotNull(gameId, "Game id cannot be Null");
        return boardRepository.get(gameId);
    }

    public Optional<Board> makeNextMove(Integer gameId, Integer playerId, Integer pitNumber) {
        Preconditions.checkNotNull(gameId, "Game id cannot be Null");
        Preconditions.checkNotNull(playerId, "Player id cannot be Null");
        Preconditions.checkNotNull(pitNumber, "Pit selection cannot be Null");
        Optional<Board> opt = boardRepository.get(gameId);
        if( opt.isPresent()){
            Board board = opt.get();
            if( Board.isGameActing(board) ){
                if ( Board.isValidMove(board, playerId, pitNumber) ){
                    Board.makeMove(board, playerId, pitNumber);
                    boardRepository.update(board);
                } else {
                    LOGGER.warn("Pit selection {} on board {} is invalid, player id: {}", pitNumber, board, playerId );
                }
            } else {
                LOGGER.info("Game, id {} has been finished", gameId);
            }
            return Optional.of(board);
        } else {
            LOGGER.warn("Specified game, id: "+ gameId + " does not exist");
            return Optional.absent();
        }
    }
    
}
