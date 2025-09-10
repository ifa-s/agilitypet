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
    @Getter
    @Setter
    int combatStyle;

    public AsyncBufferedImage getImage(ItemManager itemManager, int slot) {
        return itemManager.getImage(equipment[slot].getItemID(),1,false);
    }

    public void combat(AgiPetEnemy e) {
        int hit = e.getHit(this.getAttack(this.combatStyle), this.combatStyle);
    }

    private int getAttack(int i) {
        // Combat styles 0 = melee 1 = ranged 2 = mage
        if (i == 0) {
            return this.equipment[0].getStrength() + this.equipment[3].getStrength();
        } else if (i == 1) {
            return this.equipment[1].getRStrength() + this.equipment[3].getRStrength();
        } else if (i == 2) {
            return this.equipment[1].getMStrength() + this.equipment[3].getMStrength();
        }
        return 1; // Barehand
    }
}
