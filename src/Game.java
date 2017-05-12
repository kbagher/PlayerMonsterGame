import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;


/* This class is the main System level class which creates all the objects
 * representing the game logic (model) and the panel for user interaction.
 * It also implements the main game loop
 */

/**
 * The type Game.
 */
public class Game extends JFrame {
    /**
     * Main JPanel holding all controls and settings
     */
    JPanel pnMain;
    /**
     * The Tabbed Pane to hold the control,settings and results panels.
     */
    JTabbedPane tbpControl;

    /**
     * The dashboard panel
     */
    JPanel pnDashboard;
    /**
     * Move player left button
     */
    JButton btLeft;
    /**
     * Move player up button
     */
    JButton btUp;
    /**
     * Move player right button
     */
    JButton btRight;
    /**
     * Move player down button
     */
    JButton btDown;
    /**
     * Save game button
     */
    JButton btSave;
    /**
     * Start game button
     */
    JButton btStart;
    /**
     * Pause game buton
     */
    JButton btPause;
    /**
     * Time title
     */
    JLabel lbTimeTitle;
    /**
     * Energy title
     */
    JLabel lbEnergyTitle;
    /**
     * Time label to show the current time countdown
     */
    JLabel lbTime;
    /**
     * Energy label to show the player's current energy
     */
    JLabel lbEnergy;
    /**
     * Status label to show the current game status and any other messages to the user
     */
    JLabel lbStatus;

    /**
     * The Settings panel
     */
    JPanel pnSettings;
    /**
     * Game settings - speed title
     */
    JLabel lbSpeedTitle;
    /**
     * Game settings - time title
     */
    JLabel lbTimeSettingsTitle;
    /**
     * Game settings - initial energy title
     */
    JLabel lbinitialEnergySettingsTitle;
    /**
     * Game settings - step energy title
     */
    JLabel lbStepEnergySettingsTitle;
    /**
     * Game settings - game speed
     */
    JTextField tfSpeed;
    /**
     * Game settings - Game allowed time
     */
    JTextField tfTime;
    /**
     * Game settings - Initial player energy
     */
    JTextField tfInitialEnergy;
    /**
     * Game settings - Step energy
     */
    JTextField tfStepEnergy;
    /**
     * Game settings - Nougat energy
     */
    JTextField tfNougatEnergy;
    /**
     * Game settings - Trap required energy
     */
    JTextField tfTrapEnergy;
    /**
     * Game settings - Trap effect duration
     */
    JTextField tfTrapEffectDuration;
    /**
     * Game settings - Trap lifetime
     */
    JTextField tfTrapLifetime;
    /**
     * Game settings - coin energy title
     */
    JLabel lbCoinEnergySettingsTitle;
    /**
     * Game settings - trap energy title
     */
    JLabel lbTrapEnergySettingsTitle;
    /**
     * Game settings - trap effect duration title
     */
    JLabel lbTrapEffecDurationSettingsTitle;
    /**
     * Game settings - trap lifetime title
     */
    JLabel lbTrapLifetimeSettingsTitle;
    /**
     * Game settings - player skills title
     */
    JLabel lbPlayerSkillsSettingsTitle;
    /**
     * Game settings - monster skills title
     */
    JLabel lbMonsterSkillsSettingsTitle;
    /**
     * Game settings - player skip skill checkbox
     */
    JCheckBox cbPlayerSkip;
    /**
     * Game settings - player trap skill checkbox
     */
    JCheckBox cbPlayerTrap;
    /**
     * Game settings - monster leap skill checkbox
     */
    JCheckBox cbMonsterLeap;
    /**
     * Game settings - monster hide skill checkbox
     */
    JCheckBox cbMonsterHide;
    /**
     * Save settings button
     */
    JButton btSaveSettings;
    /**
     * The results panel
     */
    JPanel pnResults;
    /**
     * Score list
     */
    JTextArea taScoreResults;
    /**
     * Audio player for game music and sound fx
     */
    private GameAudioPlayer audio;
    /**
     * Game grid containing cells and graph nodes
     */
    private Grid grid;
    /**
     * Game player sprite
     */
    private Player player;
    /**
     * The current logged in user
     */
    private User user;
    /**
     * The trap sprite
     */
    private Trap trap;
    /**
     * Game monster sprite
     */
    private Monster monster;
    /**
     * Board panel to draw the game rooms,sprites and backgrounds
     */
    private BoardPanel bp;
    /**
     * Stores the users pause action
     */
    private boolean pause;
    /**
     * Stores the users restart action
     */
    private boolean restart;
    /**
     * Stores the users load game action
     */
    private boolean load;
    /**
     * Current time countdown
     */
    private int time;
    /**
     * Current game settings
     * <p>
     * This is accessible by all class to read
     * current game settings variables
     */
    public static Settings settings;

    /**
     * Setup the game control panel
     */
    private void setupControls() {
        pnMain = new JPanel();
        GridBagLayout gbMain = new GridBagLayout();
        GridBagConstraints gbcMain = new GridBagConstraints();
        pnMain.setLayout(gbMain);
        tbpControl = new JTabbedPane();

        pnDashboard = new JPanel();
        pnDashboard.setFocusable(false);
        GridBagLayout gbDashboard = new GridBagLayout();
        GridBagConstraints gbcDashboard = new GridBagConstraints();
        pnDashboard.setLayout(gbDashboard);

        btLeft = new JButton("Left");
        btLeft.setFocusable(false);
        btLeft.addActionListener(bp);
        gbcDashboard.gridx = 0;
        gbcDashboard.gridy = 1;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 0;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btLeft, gbcDashboard);
        pnDashboard.add(btLeft);

        btUp = new JButton("Up");
        btUp.setFocusable(false);
        btUp.addActionListener(bp);
        gbcDashboard.gridx = 1;
        gbcDashboard.gridy = 0;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btUp, gbcDashboard);
        pnDashboard.add(btUp);

        btRight = new JButton("Right");
        btRight.setFocusable(false);
        btRight.addActionListener(bp);
        gbcDashboard.gridx = 2;
        gbcDashboard.gridy = 1;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 0;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btRight, gbcDashboard);
        pnDashboard.add(btRight);

        btDown = new JButton("Down");
        btDown.setFocusable(false);
        btDown.addActionListener(bp);
        gbcDashboard.gridx = 1;
        gbcDashboard.gridy = 1;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 0;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btDown, gbcDashboard);
        pnDashboard.add(btDown);

        btSave = new JButton("Save");
        btSave.setFocusable(false);
        btSave.addActionListener(bp);
        gbcDashboard.gridx = 0;
        gbcDashboard.gridy = 2;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btSave, gbcDashboard);
        pnDashboard.add(btSave);

        btStart = new JButton("Start");
        btStart.setFocusable(false);
        btStart.addActionListener(bp);
        gbcDashboard.gridx = 1;
        gbcDashboard.gridy = 2;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btStart, gbcDashboard);
        pnDashboard.add(btStart);

        btPause = new JButton("Pause");
        btPause.setFocusable(false);
        btPause.addActionListener(bp);
        gbcDashboard.gridx = 2;
        gbcDashboard.gridy = 2;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 0;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(btPause, gbcDashboard);
        pnDashboard.add(btPause);

        lbTimeTitle = new JLabel("Time");
        gbcDashboard.gridx = 3;
        gbcDashboard.gridy = 0;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbcDashboard.insets = new Insets(0, 20, 0, 0);
        gbDashboard.setConstraints(lbTimeTitle, gbcDashboard);
        pnDashboard.add(lbTimeTitle);

        lbEnergyTitle = new JLabel("Energy");
        gbcDashboard.gridx = 3;
        gbcDashboard.gridy = 1;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbcDashboard.insets = new Insets(0, 20, 0, 0);
        gbDashboard.setConstraints(lbEnergyTitle, gbcDashboard);
        pnDashboard.add(lbEnergyTitle);

        lbTime = new JLabel("");
        lbTime.setText("" + Game.settings.timeAllowed);
        gbcDashboard.gridx = 4;
        gbcDashboard.gridy = 0;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(lbTime, gbcDashboard);
        pnDashboard.add(lbTime);

        lbEnergy = new JLabel("");
        gbcDashboard.gridx = 4;
        gbcDashboard.gridy = 1;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbDashboard.setConstraints(lbEnergy, gbcDashboard);
        pnDashboard.add(lbEnergy);

        lbStatus = new JLabel("Welcome " + user.getUsername());
        lbStatus.setForeground(new Color(114, 0, 0));
        gbcDashboard.gridx = 3;
        gbcDashboard.gridy = 2;
        gbcDashboard.gridwidth = 2;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbcDashboard.insets = new Insets(0, 20, 0, 0);
        gbDashboard.setConstraints(lbStatus, gbcDashboard);
        pnDashboard.add(lbStatus);
        tbpControl.addTab("Dashboard", pnDashboard);

        pnSettings = new JPanel();
        GridBagLayout gbSettings = new GridBagLayout();
        GridBagConstraints gbcSettings = new GridBagConstraints();
        pnSettings.setLayout(gbSettings);

        lbSpeedTitle = new JLabel("Speed");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbSpeedTitle, gbcSettings);
        pnSettings.add(lbSpeedTitle);

        lbTimeSettingsTitle = new JLabel("Time");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbTimeSettingsTitle, gbcSettings);
        pnSettings.add(lbTimeSettingsTitle);

        lbinitialEnergySettingsTitle = new JLabel("Initial energy");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbinitialEnergySettingsTitle, gbcSettings);
        pnSettings.add(lbinitialEnergySettingsTitle);

        lbStepEnergySettingsTitle = new JLabel("Step Energy");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbStepEnergySettingsTitle, gbcSettings);
        pnSettings.add(lbStepEnergySettingsTitle);

        tfSpeed = new JTextField();
        gbcSettings.gridx = 1;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfSpeed, gbcSettings);
        pnSettings.add(tfSpeed);

        tfTime = new JTextField();
        gbcSettings.gridx = 1;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfTime, gbcSettings);
        pnSettings.add(tfTime);

        tfInitialEnergy = new JTextField();
        gbcSettings.gridx = 1;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfInitialEnergy, gbcSettings);
        pnSettings.add(tfInitialEnergy);

        tfStepEnergy = new JTextField();
        gbcSettings.gridx = 1;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfStepEnergy, gbcSettings);
        pnSettings.add(tfStepEnergy);

        tfNougatEnergy = new JTextField();
        gbcSettings.gridx = 3;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfNougatEnergy, gbcSettings);
        pnSettings.add(tfNougatEnergy);

        tfTrapEnergy = new JTextField();
        gbcSettings.gridx = 3;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfTrapEnergy, gbcSettings);
        pnSettings.add(tfTrapEnergy);

        tfTrapEffectDuration = new JTextField();
        gbcSettings.gridx = 3;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfTrapEffectDuration, gbcSettings);
        pnSettings.add(tfTrapEffectDuration);

        tfTrapLifetime = new JTextField();
        gbcSettings.gridx = 3;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfTrapLifetime, gbcSettings);
        pnSettings.add(tfTrapLifetime);

        lbCoinEnergySettingsTitle = new JLabel("Coins energy");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbCoinEnergySettingsTitle, gbcSettings);
        pnSettings.add(lbCoinEnergySettingsTitle);

        lbTrapEnergySettingsTitle = new JLabel("Trap energy");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbTrapEnergySettingsTitle, gbcSettings);
        pnSettings.add(lbTrapEnergySettingsTitle);

        lbTrapEffecDurationSettingsTitle = new JLabel("Trap effect duration");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbTrapEffecDurationSettingsTitle, gbcSettings);
        pnSettings.add(lbTrapEffecDurationSettingsTitle);

        lbTrapLifetimeSettingsTitle = new JLabel("Trap lifetime");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbTrapLifetimeSettingsTitle, gbcSettings);
        pnSettings.add(lbTrapLifetimeSettingsTitle);

        lbPlayerSkillsSettingsTitle = new JLabel("Player Skills");
        gbcSettings.gridx = 6;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 10);
        gbSettings.setConstraints(lbPlayerSkillsSettingsTitle, gbcSettings);
        pnSettings.add(lbPlayerSkillsSettingsTitle);

        lbMonsterSkillsSettingsTitle = new JLabel("Monster Skills");
        gbcSettings.gridx = 4;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbMonsterSkillsSettingsTitle, gbcSettings);
        pnSettings.add(lbMonsterSkillsSettingsTitle);

        cbPlayerSkip = new JCheckBox("Skip");
        gbcSettings.gridx = 6;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(cbPlayerSkip, gbcSettings);
        pnSettings.add(cbPlayerSkip);

        cbPlayerTrap = new JCheckBox("Trap");
        gbcSettings.gridx = 6;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(cbPlayerTrap, gbcSettings);
        pnSettings.add(cbPlayerTrap);

        cbMonsterLeap = new JCheckBox("Leap");
        gbcSettings.gridx = 4;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(cbMonsterLeap, gbcSettings);
        pnSettings.add(cbMonsterLeap);

        cbMonsterHide = new JCheckBox("Hide");
        gbcSettings.gridx = 4;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(cbMonsterHide, gbcSettings);
        pnSettings.add(cbMonsterHide);

        btSaveSettings = new JButton("Save Settings");
        btSaveSettings.setFocusable(false);
        btSaveSettings.addActionListener(bp);
        gbcSettings.gridx = 4;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 4;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(btSaveSettings, gbcSettings);
        pnSettings.add(btSaveSettings);
        tbpControl.addTab("Settings", pnSettings);

        pnResults = new JPanel();
        GridBagLayout gbResults = new GridBagLayout();
        GridBagConstraints gbcResults = new GridBagConstraints();
        pnResults.setLayout(gbResults);
        pnResults.setFocusable(false);

        taScoreResults = new JTextArea(2, 10);
        JScrollPane scpScoreResults = new JScrollPane(taScoreResults);
        gbcResults.gridx = 1;
        gbcResults.gridy = 0;
        gbcResults.gridwidth = 1;
        gbcResults.gridheight = 1;
        gbcResults.fill = GridBagConstraints.BOTH;
        gbcResults.weightx = 1;
        gbcResults.weighty = 1;
        gbcResults.anchor = GridBagConstraints.NORTH;
        gbResults.setConstraints(scpScoreResults, gbcResults);
        pnResults.add(scpScoreResults);
        tbpControl.addTab("Results", pnResults);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.gridwidth = 55;
        gbcMain.gridheight = 26;
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;
        gbcMain.anchor = GridBagConstraints.CENTER;
        gbMain.setConstraints(tbpControl, gbcMain);
        pnMain.add(tbpControl);
    }

    /**
     * Load the game settings from the user into the game settings' object and display it on screen
     */
    public void loadSettings() {
        /*
         load user's settings into game settings
         */
        Settings tmpSettings = user.loadSettings(); // check if null
        settings = tmpSettings == null ? settings : tmpSettings;

        /*
        display settings
        */
        tfSpeed.setText("" + Game.settings.gameSpeed);
        tfTime.setText("" + Game.settings.timeAllowed);
        tfInitialEnergy.setText("" + Game.settings.initialEnergy);
        tfStepEnergy.setText("" + Game.settings.stepEnergy);
        tfNougatEnergy.setText("" + Game.settings.nougatEnergy);
        tfTrapEnergy.setText("" + Game.settings.trapEnergy);
        tfTrapEffectDuration.setText("" + Game.settings.trapEffectDuration);
        tfTrapLifetime.setText("" + Game.settings.trapDuration);
        lbTime.setText("" + Game.settings.timeAllowed);
        lbEnergy.setText("" + Game.settings.initialEnergy);

        cbPlayerSkip.setSelected(Game.settings.pSkills.contains(PlayerSkills.PlayerSkillsType.SKIP));
        cbPlayerTrap.setSelected(Game.settings.pSkills.contains(PlayerSkills.PlayerSkillsType.TRAP));
        cbMonsterLeap.setSelected(Game.settings.mSkills.contains(MonsterSkills.MonsterSkillsType.LEAP));
        cbMonsterHide.setSelected(Game.settings.mSkills.contains(MonsterSkills.MonsterSkillsType.INVISIBLE));

        player.replaceSkills(Game.settings.pSkills);
        monster.replaceSkills(Game.settings.mSkills);
    }


    /**
     * Update settings variables.
     */
    public void saveSettings() {
        Game.settings.gameSpeed = (Integer.parseInt(tfSpeed.getText()));
        Game.settings.timeAllowed = (Integer.parseInt(tfTime.getText()));
        Game.settings.initialEnergy = (Integer.parseInt(tfInitialEnergy.getText()));
        Game.settings.stepEnergy = (Integer.parseInt(tfStepEnergy.getText()));
        Game.settings.nougatEnergy = (Integer.parseInt(tfNougatEnergy.getText()));
        Game.settings.trapEnergy = (Integer.parseInt(tfTrapEnergy.getText()));
        Game.settings.trapEffectDuration = (Integer.parseInt(tfTrapEffectDuration.getText()));
        Game.settings.trapDuration = (Integer.parseInt(tfTrapLifetime.getText()));
        updateSkills();
        Game.settings.pSkills = new ArrayList<>(player.getSkills());
        Game.settings.mSkills = new ArrayList<>(monster.getSkills());
        user.saveSettings(settings);
    }

    /**
     * Update skills.
     */
    public void updateSkills() {
        player.removeSkills();
        monster.removeSkills();

        // player skipp skill
        if (cbPlayerSkip.isSelected())
            player.addSkill(PlayerSkills.PlayerSkillsType.SKIP);

        // player trap skill
        if (cbPlayerTrap.isSelected())
            player.addSkill(PlayerSkills.PlayerSkillsType.TRAP);

        // monster leap skill
        if (cbMonsterLeap.isSelected())
            monster.addSkill(Monster.MonsterSkillsType.LEAP);

        // monster invisible skill
        if (cbMonsterHide.isSelected())
            monster.addSkill(Monster.MonsterSkillsType.INVISIBLE);
    }

    /**
     * Starting a new game <br>
     */
    private void prepareToStartGame() {
        player.setEnergy(Game.settings.initialEnergy);
        btStart.setText("Restart");
        if (pause)
            btPause.setText("Resume");
        else
            btPause.setText("Pause");
    }

    /**
     * Instantiates a new Game.
     *
     * @param u the u
     * @throws Exception the exception
     */
/* This constructor creates the main model objects and the panel used for UI.
     * It throws an exception if an attempt is made to place the player or the
     * monster in an invalid location.
     */
    public Game(User u) throws Exception {
        pause = false;
        restart = false;
        load = false;
        user = u;
        settings = new Settings();
        grid = new Grid();
        trap = new Trap(grid);
        player = new Player(grid, trap, 0, 0, Game.settings.initialEnergy);
        monster = new Monster(grid, player, trap, 5, 5);
        bp = new BoardPanel(grid, player, monster, trap, this);

        // Create a separate panel and add all the buttons
        setupControls();
        add(bp, BorderLayout.CENTER);
        add(pnMain, BorderLayout.SOUTH);
    }


    /**
     * Reset game objects.
     *
     * @throws Exception the exception
     */
    public void resetGameObjects() throws Exception {
        pause = false;
        restart = false;
        load = false;
        grid=null;
        grid = new Grid();
        trap=null;
        trap = new Trap(grid);
        player=null;
        player = new Player(grid, trap, 0, 0, Game.settings.initialEnergy);
        monster=null;
        monster = new Monster(grid, player, trap, 5, 5);
        bp.update(grid, player, monster, trap, this);
        btStart.setText("Start");
        lbStatus.setText("Welcome " + user.getUsername());
        bp.repaint();
    }

    /**
     * Delay.
     *
     * @param time the time
     */
// method to delay by specified time in ms
    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save game.
     */
    public void saveGame() {
        if (!pause)
            pause = true;
        if (user.saveGame(this, settings))
            System.out.println("Saved");
    }

    /**
     * Pause an resume game.
     */
    public void pauseAnResumeGame() {
        if (!pause) {
            pause = true;
            btPause.setText("Resume");
        } else {
            pause = false;
            btPause.setText("Pause");
        }
    }

    /**
     * Restart game.
     */
    public void restartGame() {
        restart = true;
    }

    /**
     * Load game.
     */
    public void loadGame() {
        player.setReady(true);
        load = true;
    }

    /**
     * Play background music.
     */
    public void playBackgroundMusic() {
        try {
            audio = new GameAudioPlayer();
            audio.playLoopAudio("background.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop background music.
     */
    public void stopBackgroundMusic() {
        try {
            audio.stopAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateStatusMessage(String message) {
        if (time < Game.settings.timeAllowed) {
            // players has been eaten up
            message = "Player Lost";
            user.increaseLoss();
            try {
                audio.stopAudio();
                audio.playAudio("lost.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            message = "Player Won";
            user.increaseWins();
            try {
                audio.stopAudio();
                audio.playAudio("win.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lbStatus.setText(message);
    }

    /**
     * Play string.
     *
     * @return the string
     */
/* This method waits until play is ready (until start button is pressed)
     * after which it updates the moves in turn until time runs out (player won)
     * or player is eaten up (player lost).
     */
    public String play() {

        System.out.println(Game.settings.gameSpeed);
        time = 0;
        String message = "";
        player.setDirection(' '); // set to no direction

        while (!player.isReady())
            delay(100);


        prepareToStartGame();

        do {
            while (pause)
                delay(1000);

            trap.update();

            Cell newPlayerCell = player.move();
            if (newPlayerCell == monster.getCell() && !trap.isTrapped(monster.getCell()))
                break;

            // reset to no direction
//            player.setDirection(' ');

            Cell newMonsterCell = monster.move();
            if (newMonsterCell == player.getCell() && !trap.isTrapped(monster.getCell()))
                break;

            // update time and repaint
            time++;
            lbTime.setText("" + (Game.settings.timeAllowed - time));
            lbEnergy.setText("" + player.getEnergy());
            delay(Game.settings.gameSpeed);
            bp.repaint();
        } while (time < Game.settings.timeAllowed && !restart && !load);

        if (!load)
            updateStatusMessage(message);

        delay(2500);

        if (load)
            return "load";
        return "restart";
    }
}