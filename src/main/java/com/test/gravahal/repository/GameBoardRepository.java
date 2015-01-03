package com.test.gravahal.repository;

import com.google.common.base.Optional;
import com.test.gravahal.domain.Board;
import com.test.gravahal.domain.Player;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:50:46 PM 

public interface GameBoardRepository {
    
    int getNextPlayerId();
    
    int saveAndGetId(Board newGameBoard);

    Optional<Board> getGameBoardById(int gameId);

    Optional<Player> getPlayerById(int playerId);

}
