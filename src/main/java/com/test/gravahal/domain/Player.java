package com.test.gravahal.domain;

import java.util.Arrays;

public final class Player {
    
    private static final int PIT_COUNT = 6;
    private static final int ROCKS_PER_PIT = 6;
    
    private int playerId;
    private int gravahal;
    private int[] pits = new int[PIT_COUNT];
    
    public Player() {
        Arrays.fill(pits, ROCKS_PER_PIT);
    }
    
    public Player(Player other) {
        this.playerId = other.playerId;
        this.gravahal = other.gravahal;
        this.pits = Arrays.copyOf(other.pits, PIT_COUNT);
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getGravahal() {
        return gravahal;
    }

    public void setGravahal(int gravahal) {
        this.gravahal = gravahal;
    }

    public int[] getPits() {
        return pits;
    }

    public void setPits(int[] pits) {
        this.pits = pits;
    }
    
}
