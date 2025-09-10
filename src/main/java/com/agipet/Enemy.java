package com.agipet;

public enum Enemy {
    MUSPAH("Phantom Muspah", "/Muspah.png", 850, 200, 50, 450),
    SCURRIUS("Scurrius", "/Scurrius.png", 500, 20, 20, 10),
    SOL("Sol Heredit", "/Sol.png", 1500, 5, 825, 750);

    public final String name;
    public final String spriteName;
    public final int health;
    public final int defense;
    public final int rDefense;
    public final int mDefense;
    private Enemy(String name, String spriteName, int health, int defense, int rDefense, int mDefense) {
        this.name = name;
        this.spriteName = spriteName;
        this.health = health;
        this.defense = defense;
        this.rDefense = rDefense;
        this.mDefense = mDefense;
    }
}
