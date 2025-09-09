package com.agipet;

import com.formdev.flatlaf.util.ScaledImageIcon;
import com.google.inject.Inject;

import java.awt.*;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.SpritePixels;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.SpriteID;
import net.runelite.client.account.SessionManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.info.InfoPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.game.ItemManager;

public class AgiPetPanel extends PluginPanel
{
    /*
    private static final ImageIcon ARROW_RIGHT_ICON;
    private static final ImageIcon GITHUB_ICON;
    private static final ImageIcon DISCORD_ICON;
    private static final ImageIcon PATREON_ICON;
    private static final ImageIcon WIKI_ICON;
*/
    private static final ImageIcon MUSPAH_PET;
    private final JLabel loggedLabel = new JLabel();
    private JPanel actionsContainer;

    @Inject
    private Client client;

    @Inject
    private EventBus eventBus;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private ScheduledExecutorService executor;

    @Inject
    private ItemManager itemManager;

   /* static
    {
        ARROW_RIGHT_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "/util/arrow_right.png"));
        GITHUB_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "github_icon.png"));
        DISCORD_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "discord_icon.png"));
        PATREON_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "patreon_icon.png"));
        WIKI_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "wiki_icon.png"));
    } */ // Static for images, TODO use for pet icons?

    static
    {
        MUSPAH_PET = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "/Muspah.png"));
    }
    private JLabel startXp;
    private JLabel gainedXp;
    private JLabel laps;
    private JButton pet;
    private ScaledImageIcon petImage;
    private JButton itemSlotMelee;
    private JButton itemSlotRanged;
    private JButton itemSlotMage;
    private JButton itemSlotHelmet;
    void init()
    {
        setLayout(new BorderLayout());
        // Sets far background type
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel infoPanel = new JPanel();
        // Set text info panel background
        infoPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.setLayout(new GridLayout(0, 1));

        final Font smallFont = FontManager.getRunescapeSmallFont();

        // Set placeholder JLabels
        startXp = new JLabel("Start XP: 0");
        startXp.setFont(smallFont);
        gainedXp = new JLabel("Gained XP: 0");
        gainedXp.setFont(smallFont);
        laps = new JLabel("Laps: 0");
        laps.setFont(smallFont);

        infoPanel.add(startXp);
        infoPanel.add(gainedXp);
        infoPanel.add(laps);
        add(infoPanel, BorderLayout.NORTH);
        infoPanel.add(Box.createGlue());

        JPanel petPanel = new JPanel();
        petPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        petPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        petPanel.setLayout(new GridLayout(0, 1));
        petImage = new ScaledImageIcon(MUSPAH_PET, 100, 100);
        pet = new JButton(petImage);
        petPanel.add(pet);
        add(petPanel, BorderLayout.CENTER);

        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        itemPanel.setLayout(new GridLayout(0, 4));
        int itemId;
        AsyncBufferedImage itemImg;
        // Melee Weapon Slot
        itemId = ItemID.BRONZE_2H_SWORD;
        itemImg = itemManager.getImage(itemId, 1, false);
        itemSlotMelee = new JButton();
        itemImg.addTo(itemSlotMelee);
        itemPanel.add(itemSlotMelee);
        // Ranged Weapon Slot
        itemId = ItemID.CROSSBOW;
        itemImg = itemManager.getImage(itemId, 1, false);
        itemSlotRanged = new JButton();
        itemImg.addTo(itemSlotRanged);
        itemPanel.add(itemSlotRanged);
        // Mage Weapon Slot
        itemId = ItemID.IBANSTAFF;
        itemImg = itemManager.getImage(itemId, 1, false);
        itemSlotMage = new JButton();
        itemImg.addTo(itemSlotMage);
        itemPanel.add(itemSlotMage);
        // Helmet Slot
        itemId = ItemID.NEITIZNOT_FACEGUARD;
        itemImg = itemManager.getImage(itemId, 1, false);
        itemSlotHelmet = new JButton();
        itemImg.addTo(itemSlotHelmet);
        itemPanel.add(itemSlotHelmet);
        // Add items to panel
        add(itemPanel,BorderLayout.SOUTH);
        eventBus.register(this);
    }

    void deinit() {
        eventBus.unregister(this);
    }
    public void update(AgiPetTracker t) {
        laps.setText("Laps: " + t.getTotalLaps());
        startXp.setText("Start XP: " + t.getStartXp());
        gainedXp.setText("Gained XP: " + t.getXpGained());
    }
    private static String htmlLabel(String key, String value)
    {
        return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + value + "</span></body></html>";
    }
}