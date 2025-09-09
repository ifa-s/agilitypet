package com.agipet;

import lombok.Getter;
import lombok.Setter;

public class AgiPetItem {
    @Getter
    private final int slot;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int strength;
    @Getter
    @Setter
    private int rStrength;
    @Getter
    @Setter
    private int mStrength;
    @Getter
    @Setter
    private int defense;
    @Getter
    @Setter
    private int mDefense;
    @Getter
    private final int itemID;


    public AgiPetItem(int slot, int itemID, String name, int strength, int rStrength, int mStrength, int defense, int mDefense) {
        this.slot = slot;
        this.itemID = itemID;
        this.name = name;
        this.strength = strength;
        this.rStrength = rStrength;
        this.mStrength = mStrength;
        this.defense = defense;
        this.mDefense = mDefense;
    }

    @Override
    public String toString() {
        return slot +
                "," +
                itemID +
                "," +
                name +
                "," +
                strength +
                "," +
                rStrength +
                "," +
                mStrength +
                "," +
                defense +
                "," +
                mDefense;
    }
}
