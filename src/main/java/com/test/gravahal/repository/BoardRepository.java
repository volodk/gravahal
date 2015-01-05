package com.test.gravahal.repository;

import com.test.gravahal.domain.Board;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:50:46 PM 

public interface BoardRepository extends AbstractCRUDRepository<Board> {
    int getNextGameId();
}
