package com.agipet;

import com.google.inject.Inject;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Client;
import net.runelite.client.account.SessionManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

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


   /* static
    {
        ARROW_RIGHT_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "/util/arrow_right.png"));
        GITHUB_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "github_icon.png"));
        DISCORD_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "discord_icon.png"));
        PATREON_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "patreon_icon.png"));
        WIKI_ICON = new ImageIcon(ImageUtil.loadImageResource(InfoPanel.class, "wiki_icon.png"));
    } */ // Static for images, TODO use for pet icons?

    private JLabel startXp;
    private JLabel gainedXp;
    private JLabel laps;
    void init()
    {
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        infoPanel.setLayout(new GridLayout(0, 1));

        final Font smallFont = FontManager.getRunescapeSmallFont();

        startXp = new JLabel("Start XP: 0");
        startXp.setFont(smallFont);

        gainedXp = new JLabel("Gained XP: 0");
        gainedXp.setFont(smallFont);

        laps = new JLabel("Laps: 0");
        laps.setFont(smallFont);

        //revision.setText(htmlLabel("Oldschool revision: ", engineVer));


        infoPanel.add(startXp);
        infoPanel.add(gainedXp);
        infoPanel.add(laps);
        //infoPanel.add(Box.createGlue());

        /*
        actionsContainer = new JPanel();
        actionsContainer.setBorder(new EmptyBorder(10, 0, 0, 0));
        actionsContainer.setLayout(new GridLayout(0, 1, 0, 10));

        actionsContainer.add(buildLinkPanel(GITHUB_ICON, "Report an issue or", "make a suggestion", githubLink));
        actionsContainer.add(buildLinkPanel(DISCORD_ICON, "Talk to us on our", "Discord server", discordInvite));
        actionsContainer.add(buildLinkPanel(PATREON_ICON, "Become a patron to", "help support RuneLite", patreonLink));
        actionsContainer.add(buildLinkPanel(WIKI_ICON, "Information about", "RuneLite and plugins", wikiLink));


        add(actionsContainer, BorderLayout.CENTER);
        */
        add(infoPanel, BorderLayout.NORTH);
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