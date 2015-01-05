package com.test.gravahal.repository;

import com.test.gravahal.domain.Game;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:50:46 PM 

public interface GameRepository extends AbstractCRUDRepository<Game> {
    int getNextGameId();
}
