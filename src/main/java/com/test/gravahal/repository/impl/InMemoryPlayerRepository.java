package com.test.gravahal.repository.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.test.gravahal.domain.Player;
import com.test.gravahal.repository.PlayerRepository;

// Volodymyr_Krasnikov1 <vkrasnikov@gmail.com> 3:34:50 PM 

public class InMemoryPlayerRepository implements PlayerRepository{
    
    private final AtomicInteger sequence = new AtomicInteger();
    private ConcurrentMap<Integer, Player> table = new ConcurrentHashMap<>();
    
    @Override
    public int getNextPlayerId() {
        return sequence.incrementAndGet();
    }

    @Override
    public Player save(Player entity) {
        Preconditions.checkNotNull(entity);
        int id = entity.getId();
        table.putIfAbsent(id, entity);
        return entity;
    }

    @Override
    public void update(Player entity) {
        Preconditions.checkNotNull(entity);
        int id = entity.getId();
        table.replace(id, entity);
    }

    @Override
    public void delete(Player entity) {
        Preconditions.checkNotNull(entity);
        int id = entity.getId();
        table.remove(id);
    }

    @Override
    public Optional<Player> get(int id) {
        if( table.containsKey(id) )
            return Optional.of( table.get(id) );
        return Optional.absent();
    }

}
