package com.agipet;

import com.formdev.flatlaf.util.ScaledImageIcon;
import com.google.inject.Inject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Client;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.account.SessionManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.info.InfoPanel;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
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

    private JLabel startXp;
    private JLabel gainedXp;
    private JLabel laps;
    private JLabel activeStyle;
    private JLabel enemyHealth;
    private int combatStyle;
    private JButton pet;
    private ScaledImageIcon petImage;
    private JButton itemSlotMelee;
    private JButton itemSlotRanged;
    private JButton itemSlotMage;
    private JButton itemSlotHelmet;
    private AgiPetPlayer player;
    void init(AgiPetPlayer p, AgiPetEnemy e)
    {
        this.player = p;
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
        activeStyle = new JLabel("Combat Style: Melee");
        activeStyle.setFont(smallFont);
        enemyHealth = new JLabel("Enemy Health: 0");
        enemyHealth.setFont(smallFont);

        infoPanel.add(startXp);
        infoPanel.add(gainedXp);
        infoPanel.add(laps);
        infoPanel.add(activeStyle);
        infoPanel.add(enemyHealth);
        add(infoPanel, BorderLayout.NORTH);
        infoPanel.add(Box.createGlue());

        JPanel petPanel = new JPanel();
        petPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        petPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        petPanel.setLayout(new GridLayout(0, 1));
        petImage = new ScaledImageIcon(new ImageIcon(e.getSprite()), 100, 100);
        pet = new JButton(petImage);
        petPanel.add(pet);
        add(petPanel, BorderLayout.CENTER);
        infoPanel.add(Box.createGlue());

        JPanel itemPanel = new JPanel();
        itemPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        itemPanel.setLayout(new GridLayout(0, 4));
        itemSlotMelee = new JButton();
        itemSlotMelee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setCombatStyle(0);
                update(player);
            }
        });
        player.getImage(itemManager,0).addTo(itemSlotMelee);
        itemPanel.add(itemSlotMelee);
        // Ranged Weapon Slot
        itemSlotRanged = new JButton();
        player.getImage(itemManager,2).addTo(itemSlotRanged);
        itemPanel.add(itemSlotRanged);
        itemSlotRanged.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setCombatStyle(1);
                update(player);
            }
        });
        // Mage Weapon Slot
        itemSlotMage = new JButton();
        itemSlotMage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setCombatStyle(2);
                update(player);
            }
        });
        player.getImage(itemManager,1).addTo(itemSlotMage);
        itemPanel.add(itemSlotMage);
        // Helmet Slot
        itemSlotHelmet = new JButton();
        player.getImage(itemManager,3).addTo(itemSlotHelmet);
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
    public void update(AgiPetEnemy e) {
        enemyHealth.setText(e.getName() + " Health: " + e.getHealth());
    }

    public void update(AgiPetPlayer p) {
        String style;
        int c = p.getCombatStyle();
        if (c == 0) {
            style = "Melee";
        } else if (c == 1) {
            style = "Ranged";
        } else {
            style = "Mage";
        }
        activeStyle.setText("Active Style: " + style);
    }

    private static String htmlLabel(String key, String value)
    {
        return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + value + "</span></body></html>";
    }
}