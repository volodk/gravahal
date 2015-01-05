package com.test.gravahal.domain;

import java.util.Arrays;

import com.google.common.base.Objects;

public class Player {
    
    private static final int PIT_COUNT = 6;
    private static final int ROCKS_PER_PIT = 6;
    
    private int id;
    private int gravahalField;
    private int[] pits = new int[PIT_COUNT];
    
    public Player() {
        Arrays.fill(pits, ROCKS_PER_PIT);
    }
    
    public Player(int id) {
        this.id = id;
        Arrays.fill(pits, ROCKS_PER_PIT);
    }

    public int getId() {
        return id;
    }

    public int getGravahalField() {
        return gravahalField;
    }

    public void setGravahalField(int gravahal) {
        this.gravahalField = gravahal;
    }

    public int[] getPits() {
        return pits;
    }

    public void setPits(int[] pits) {
        this.pits = pits;
    }
    
    int countStones(int pitNumber){
        return pits[pitNumber - 1];
    }
    
    int pickup(int pitNumber){
        int tmp = pits[pitNumber - 1];
        pits[pitNumber - 1] = 0;
        return tmp;
    }
    
    public void incrementPit(int pitNumber) {
        pits[pitNumber - 1] += 1;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("player's Gravahal", gravahalField)
                .add("pits", Arrays.toString(pits) )
            .toString();
    }
}
