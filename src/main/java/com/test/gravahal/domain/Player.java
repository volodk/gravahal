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
    
    public int countStones(int pitNumber){
        return pits[pitNumber - 1];
    }
    
    public int pickup(int pitNumber){
        int tmp = pits[pitNumber - 1];
        pits[pitNumber - 1] = 0;
        return tmp;
    }
    
    public void incrementPit(int pitNumber) {
        pits[pitNumber - 1] += 1;
    }
    
    public boolean isPitNonEmpty(int pitNumber){
        return pits[pitNumber-1] > 0;
    }
    
    public boolean isWinner() {
        boolean winStatus = true;
        for(int i = 0; winStatus && i < PIT_COUNT; i++)
            winStatus = pits[i] == 0;
        return winStatus;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("player's Gravahal", gravahalField)
                .add("pits", Arrays.toString(pits) )
            .toString();
    }

    public int spreadStones(int count, boolean canFillGravaHal, int startingPit) {
        for( int i = startingPit-1; count > 0 && i < PIT_COUNT; i++ ){
            pits[i] += 1;
            count -= 1;
        }
        if( canFillGravaHal && count > 0 ){
            count -= 1;
        }
        return count;   // remaining stones to place 
    }
}
