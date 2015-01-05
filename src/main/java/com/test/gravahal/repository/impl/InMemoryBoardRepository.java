package com.test.gravahal.repository.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import com.test.gravahal.domain.Board;
import com.test.gravahal.repository.BoardRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 4:07:47 PM 

@Singleton
public class InMemoryBoardRepository implements BoardRepository {
    
    private AtomicInteger sequence = new AtomicInteger();
    private ConcurrentMap<Integer, Board> table = new ConcurrentHashMap<>();
    
    @Override
    public int getNextGameId() {
        return sequence.incrementAndGet();
    }

    @Override
    public Board save(Board entity) {
        Preconditions.checkNotNull(entity);
        int id = entity.getId();
        table.putIfAbsent(id, entity);
        return entity;
    }

    @Override
    public void update(Board entity) {
        Preconditions.checkNotNull(entity);
        int id = entity.getId();
        table.replace(id, entity);
    }

    @Override
    public void delete(Board entity) {
        Preconditions.checkNotNull(entity);
        int id = entity.getId();
        table.remove(id);
    }

    @Override
    public Optional<Board> get(int id) {
        if( table.containsKey(id) )
            return Optional.of( table.get(id) );
        return Optional.absent();
    }

}
