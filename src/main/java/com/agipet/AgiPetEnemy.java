package com.agipet;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

public class AgiPetEnemy {

    @Getter
    @Setter
    private int health;
    @Getter
    private String name;
    @Getter
    private String spriteName;
    private int defense;
    private int rDefense;
    private int mDefense;


    public AgiPetEnemy(Enemy enemy, int hpMod) {
        this.name = enemy.name;
        this.spriteName = enemy.spriteName;
        this.health = enemy.health * hpMod;
        this.defense = enemy.defense;
        this.rDefense = enemy.rDefense;
        this.mDefense = enemy.mDefense;
    }

    public BufferedImage getSprite() {
        return ImageUtil.loadImageResource(getClass(), this.spriteName);
    }
}
