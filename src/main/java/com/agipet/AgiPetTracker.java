package com.agipet;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.plugins.xptracker.XpTrackerConfig;
import net.runelite.client.plugins.xptracker.XpTrackerService;

@Getter
@Setter
class AgiPetTracker {
    private Courses course; // Not final as this works in multiple courses
    private int totalLaps;
    @Getter
    private int xpGained;
    @Setter
    private int startXp;

    void updateGained(Client client, XpTrackerService xpTrackerService) {
        final int currentXp = client.getSkillExperience(Skill.AGILITY);
        this.xpGained = currentXp - this.startXp;
    }
    void addLap(Client client, XpTrackerService xpTrackerService) {
        ++totalLaps;
        this.updateGained(client, xpTrackerService);
    }
}
