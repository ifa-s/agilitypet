package com.agipet;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.StatChanged;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.xptracker.XpTrackerPlugin;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import static net.runelite.api.Skill.AGILITY;
@PluginDependency(XpTrackerPlugin.class)

@Slf4j
@PluginDescriptor(
	name = "Agility Pet",
    description = "Adds a game sidebar to make agility training more interesting",
    tags = {"agility", "pet"}
)
public class AgiPetPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private AgiPetConfig config;

    @Inject
    private Notifier notifier;

    @Getter
    private int agilityLevel;

    @Getter
    private int agilityXp;

    private int lastAgilityXp;

    @Getter
    private AgiPetPlayer player;

    @Inject
    private XpTrackerService xpTrackerService;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private AgiPetTracker tracker;

    @Inject
    private ClientToolbar clientToolbar;

    private AgiPetPanel panel;
    private NavigationButton navButton;
    private AgiPetEnemy enemy;
    private boolean loggedin;
    private AgiPetFile data;
	@Override
	protected void startUp() throws Exception
	{
        loggedin = false;
        // TODO Read write from file
        data = new AgiPetFile(client);
        // README https://github.com/Mrnice98/BossingInfo/blob/master/src/main/java/com/killsperhour/FileReadWriter.java
        // Add panel to window
        player = createPlayer();
        panel = injector.getInstance(AgiPetPanel.class);
        enemy = getNewEnemy();
        panel.init(player, enemy);

        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.jpg");

        navButton = NavigationButton.builder()
                .tooltip("Agility Pet")
                .icon(icon)
                .priority(100)
                .panel(panel)
                .build();

        clientToolbar.addNavigation(navButton);

	}

	@Override
	protected void shutDown() throws Exception {
        data.update(tracker.getStartXp(), tracker.getTotalLaps(), tracker.getXpGained(), player);
    }

    @Subscribe
    public void onStatChanged(StatChanged statChanged) throws IOException {
        if (statChanged.getSkill() != AGILITY)
        {
            return;
        }

        if (!loggedin) {
            loggedin = true;
            agilityLevel = client.getRealSkillLevel(Skill.AGILITY);
            agilityXp = client.getSkillExperience(Skill.AGILITY);
            tracker = new AgiPetTracker();
            int[] load = data.readData();
            if (load[0] != -1) {
                tracker.setStartXp(load[0]);
            } else {
                tracker.setStartXp(agilityXp);
            }
            if (load[1] != -1) {
                tracker.setTotalLaps(load[1]);
            }
            panel.update(tracker);
        }



        // Determine how much EXP was actually gained
        agilityXp = statChanged.getXp();
        int skillGained = agilityXp - lastAgilityXp;
        lastAgilityXp = agilityXp;

        log.debug("Gained {} xp at {}", skillGained, client.getLocalPlayer().getWorldLocation());

        // Get course
        Courses course = Courses.getCourse(client.getLocalPlayer().getWorldLocation().getRegionID());
        if (course == null
                /* || !config.showLapCount() */
                || Arrays.stream(course.getCourseEndWorldPoints()).noneMatch(wp -> wp.equals(client.getLocalPlayer().getWorldLocation())))
        {
            tracker.updateGained(client, xpTrackerService);
            panel.update(tracker);
            data.update(tracker.getStartXp(), tracker.getTotalLaps(), tracker.getXpGained(), player);
            return;
        }

        track(course);
        panel.update(tracker);
        data.update(tracker.getStartXp(), tracker.getTotalLaps(), tracker.getXpGained(), player);
    }

    private void track(Courses course) {
        tracker.addLap(client, xpTrackerService);
        client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Lap count: " + tracker.getTotalLaps(), null);
    }
    private void track() {
        tracker.updateGained(client, xpTrackerService);
    }

    private AgiPetPlayer createPlayer() {
        AgiPetItem meWep = new AgiPetItem(0, ItemID.SCYTHE_OF_VITUR, "Scythe of Vitur", 1, 0, 0, 1, 0);
        AgiPetItem maWep = new AgiPetItem(1,ItemID.TUMEKENS_SHADOW, "Tumeken's Shadow", 0, 0,1, 0, 1);
        AgiPetItem raWep = new AgiPetItem(2,ItemID.TWISTED_BOW, "Twisted Bow", 0, 1, 0, 1, 0);
        AgiPetItem helm = new AgiPetItem(3,ItemID.TORVA_HELM, "Torva Helm", 1, 0, 0, 1, 0);
        AgiPetPlayer player = new AgiPetPlayer();
        AgiPetItem[] testeq = {meWep, maWep, raWep, helm};
        player.setEquipment(testeq);
        return player;
    }

    private AgiPetEnemy getNewEnemy() {
        return new AgiPetEnemy(Enemy.SOL, 1);
    }

	@Provides
    AgiPetConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AgiPetConfig.class);
	}
    @Subscribe
    public void onSessionOpen(SessionOpen sessionOpen) {
        if (!loggedin) {
            loggedin = true;

        }

    }
}
