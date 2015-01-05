package com.test.gravahal.domain;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class Player {
    
    private static final int PIT_COUNT = 6;
    private static final int ROCKS_PER_PIT = 6;
    
    @JsonProperty
    private int id;
    
    @JsonProperty
    private int gravahalField;
    
    @JsonProperty
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
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("player's Gravahal", gravahalField)
                .add("pits", Arrays.toString(pits) )
            .toString();
    }
    
}
