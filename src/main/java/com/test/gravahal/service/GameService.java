package com.test.gravahal.service;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.test.gravahal.domain.Board;
import com.test.gravahal.repository.GameBoardRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:47:27 PM 

@Singleton
public class GameService {
    
    private final GameBoardRepository repository;
    
    @Inject
    public GameService(GameBoardRepository gameRepository) {
        this.repository = gameRepository;
    }
    
    public int getUniquePlayerId(){
        return repository.getNextPlayerId();
    }

    public boolean isGameOpen(int gameId) {
        return repository.getGameBoardById(gameId).isPresent();
    }

    public boolean isPlayerRegistered(int playerId) {
        return repository.getPlayerById(playerId).isPresent();
    }

    public int startNewGame(int initGamePlayerId) {
        Board newGameBoard = new Board(initGamePlayerId);
        return repository.saveAndGetId(newGameBoard);
    }

    public void join(int gameId, int playerId) {
        Optional<Board> opt = repository.getGameBoardById(gameId);
        if( opt.isPresent()){
            Board currentBoard = opt.get();
            currentBoard.join(playerId);
        } else {
            // TODO
        }
    }
    
    public Board getBoard(int gameId){
        Optional<Board> opt = repository.getGameBoardById(gameId);
        if( opt.isPresent()){
            return Board.copyOf( opt.get() );
        } else {
            // TODO
            throw new RuntimeException();
        }
    }

    public Board makeNextMove(int gameId, int playerId, int pitNumber) {
        Optional<Board> opt = repository.getGameBoardById(gameId);
        if( opt.isPresent()){
            Board currentBoard = opt.get();
            currentBoard.makeMove(playerId, pitNumber);
            return Board.copyOf(currentBoard);
        } else {
            // TODO
            throw new RuntimeException();
        }
    }
    
}
