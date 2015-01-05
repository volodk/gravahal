package com.test.gravahal.repository;

import com.test.gravahal.domain.Player;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:23:16 PM 

public interface PlayerRepository extends AbstractCRUDRepository<Player>{
    
    int getNextPlayerId();
    
}
