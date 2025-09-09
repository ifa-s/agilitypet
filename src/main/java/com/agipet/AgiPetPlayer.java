package com.agipet;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.game.ItemManager;
import net.runelite.client.util.AsyncBufferedImage;

public class AgiPetPlayer {
    @Getter
    @Setter
    AgiPetItem[] equipment;
    @Getter
    @Setter
    int health;

    public AsyncBufferedImage getImage(ItemManager itemManager, int slot) {
        return itemManager.getImage(equipment[slot].getItemID(),1,false);
    }
}
