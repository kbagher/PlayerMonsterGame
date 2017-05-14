import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
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
    private JPanel pnMain;

    /**
     * Start game button
     */
    private JButton btStart;
    /**
     * Pause game button
     */
    private JButton btPause;
    /**
     * Time label to show the current time countdown
     */
    private JLabel lbTime;
    /**
     * Energy label to show the player's current energy
     */
    private JLabel lbEnergy;
    /**
     * Status label to show the current game status and any other messages to the user
     */
    private JLabel lbStatus;

    /**
     * Game settings - game speed
     */
    private JTextField tfSpeed;
    /**
     * Game settings - Game allowed time
     */
    private JTextField tfTime;
    /**
     * Game settings - Initial player energy
     */
    private JTextField tfInitialEnergy;
    /**
     * Game settings - Step energy
     */
    private JTextField tfStepEnergy;
    /**
     * Game settings - Nougat energy
     */
    private JTextField tfNougatEnergy;
    /**
     * Game settings - Trap required energy
     */
    private JTextField tfTrapEnergy;
    /**
     * Game settings - Trap effect duration
     */
    private JTextField tfTrapEffectDuration;
    /**
     * Game settings - Trap lifetime
     */
    private JTextField tfTrapLifetime;
    /**
     * Game settings - player skip skill checkbox
     */
    private JCheckBox cbPlayerSkip;
    /**
     * Game settings - player trap skill checkbox
     */
    private JCheckBox cbPlayerTrap;
    /**
     * Game settings - monster leap skill checkbox
     */
    private JCheckBox cbMonsterLeap;
    /**
     * Game settings - monster hide skill checkbox
     */
    private JCheckBox cbMonsterHide;
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
        /*
      The Tabbed Pane to hold the control,settings and results panels.
     */
        JTabbedPane tbpControl = new JTabbedPane();

        /*
      The dashboard panel
     */
        JPanel pnDashboard = new JPanel();
        pnDashboard.setFocusable(false);
        GridBagLayout gbDashboard = new GridBagLayout();
        GridBagConstraints gbcDashboard = new GridBagConstraints();
        pnDashboard.setLayout(gbDashboard);

        /*
      Move player left button
     */
        JButton btLeft = new JButton("Left");
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

        /*
      Move player up button
     */
        JButton btUp = new JButton("Up");
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

        /*
      Move player right button
     */
        JButton btRight = new JButton("Right");
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

        /*
        Move player down button
        */
        JButton btDown = new JButton("Down");
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

        /*
      Save game button
     */
        JButton btSave = new JButton("Save");
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

        /*
      Time title
     */
        JLabel lbTimeTitle = new JLabel("Time");
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

        /*
      Energy title
     */
        JLabel lbEnergyTitle = new JLabel("Energy");
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

        lbStatus = new JLabel("Welcome " + user.getName());
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

        /*
      The Settings panel
     */
        JPanel pnSettings = new JPanel();
        GridBagLayout gbSettings = new GridBagLayout();
        GridBagConstraints gbcSettings = new GridBagConstraints();
        pnSettings.setLayout(gbSettings);

        /*
      Game settings - speed title
     */
        JLabel lbSpeedTitle = new JLabel("Speed");
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

        /*
      Game settings - time title
     */
        JLabel lbTimeSettingsTitle = new JLabel("Time");
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

        /*
      Game settings - initial energy title
     */
        JLabel lbInitialEnergySettingsTitle = new JLabel("Initial energy");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbInitialEnergySettingsTitle, gbcSettings);
        pnSettings.add(lbInitialEnergySettingsTitle);

        /*
      Game settings - step energy title
     */
        JLabel lbStepEnergySettingsTitle = new JLabel("Step Energy");
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

        /*
      Game settings - coin energy title
     */
        JLabel lbCoinEnergySettingsTitle = new JLabel("Coins energy");
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

        /*
      Game settings - trap energy title
     */
        JLabel lbTrapEnergySettingsTitle = new JLabel("Trap energy");
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

        /*
      Game settings - trap effect duration title
     */
        JLabel lbTrapEffectDurationSettingsTitle = new JLabel("Trap effect duration");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbTrapEffectDurationSettingsTitle, gbcSettings);
        pnSettings.add(lbTrapEffectDurationSettingsTitle);

        /*
      Game settings - trap lifetime title
     */
        JLabel lbTrapLifetimeSettingsTitle = new JLabel("Trap lifetime");
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

        /*
      Game settings - player skills title
     */
        JLabel lbPlayerSkillsSettingsTitle = new JLabel("Player Skills");
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

        /*
      Game settings - monster skills title
     */
        JLabel lbMonsterSkillsSettingsTitle = new JLabel("Monster Skills");
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


        /*
         * Grid Structure
         */
        JButton btGridStructure = new JButton("Grid Structure");
        btGridStructure.setFocusable(false);
        btGridStructure.addActionListener(bp);
        gbcSettings.gridx = 4;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(btGridStructure, gbcSettings);
        pnSettings.add(btGridStructure);
        tbpControl.addTab("Settings", pnSettings);

        /*
         * Save settings button
         */
        JButton btSaveSettings = new JButton("Save Settings");
        btSaveSettings.setFocusable(false);
        btSaveSettings.addActionListener(bp);
        gbcSettings.gridx = 6;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(btSaveSettings, gbcSettings);
        pnSettings.add(btSaveSettings);
        tbpControl.addTab("Settings", pnSettings);

        /*
      The results panel
     */
        JPanel pnResults = new JPanel();
        GridBagLayout gbResults = new GridBagLayout();
        GridBagConstraints gbcResults = new GridBagConstraints();
        pnResults.setLayout(gbResults);
        pnResults.setFocusable(false);

        /*
      Score list
     */
        JTextArea taScoreResults = new JTextArea(2, 10);
        taScoreResults.setEditable(false);
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
     * load user's settings into game settings object
     */
    private void loadGameSettings() {
        try {
            settings = user.loadSettings();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Load the game settings from the user into the game settings' object and display it on screen
     */
    public void displaySettingsInUI() {

        // reload settings to reflect any possible changes
        loadGameSettings();

        /*
         * display settings in GUI
         */

        // Text fields
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
        // Skills checkboxes
        cbPlayerSkip.setSelected(Game.settings.pSkills.contains(PlayerSkills.PlayerSkillsType.SKIP));
        cbPlayerTrap.setSelected(Game.settings.pSkills.contains(PlayerSkills.PlayerSkillsType.TRAP));
        cbMonsterLeap.setSelected(Game.settings.mSkills.contains(MonsterSkills.MonsterSkillsType.LEAP));
        cbMonsterHide.setSelected(Game.settings.mSkills.contains(MonsterSkills.MonsterSkillsType.INVISIBLE));

        /*
         * Update Skills settings in player and monster objects
         */
        player.replaceSkills(Game.settings.pSkills);
        monster.replaceSkills(Game.settings.mSkills);
    }


    /**
     * Save user settings.
     */
    public void saveSettings() {
        /*
         * Read settings from GUI and store it in the game settings object
         */
        Game.settings.gameSpeed = (Integer.parseInt(tfSpeed.getText()));
        Game.settings.timeAllowed = (Integer.parseInt(tfTime.getText()));
        Game.settings.initialEnergy = (Integer.parseInt(tfInitialEnergy.getText()));
        Game.settings.stepEnergy = (Integer.parseInt(tfStepEnergy.getText()));
        Game.settings.nougatEnergy = (Integer.parseInt(tfNougatEnergy.getText()));
        Game.settings.trapEnergy = (Integer.parseInt(tfTrapEnergy.getText()));
        Game.settings.trapEffectDuration = (Integer.parseInt(tfTrapEffectDuration.getText()));
        Game.settings.trapDuration = (Integer.parseInt(tfTrapLifetime.getText()));
        /*
         * Read Player and monster skills from GUI
         * and store into player and monster objects
         */
        try {
            updateSkillsFromGUI();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Update player and monster skills in settings
         */
        Game.settings.pSkills = new ArrayList<>(player.getSkills());
        Game.settings.mSkills = new ArrayList<>(monster.getSkills());

        /*
         * Save settings in database
         */
        try {
            user.saveSettings(settings);
            JOptionPane.showMessageDialog(this, "Settings Updated", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Update Player and Monster skills from GUI
     */
    private void updateSkillsFromGUI() throws Exception {
        player.removeAllSkills();
        monster.removeAllSkills();

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
     * Update game needed variables before each round
     */
    private void prepareToStartGame() {
        time = 0;
        player.setDirection(' ');
        player.setEnergy(Game.settings.initialEnergy);
        btStart.setText("Restart");
        if (pause)
            btPause.setText("Resume");
        else
            btPause.setText("Pause");
    }

    /**
     * This constructor creates the main model objects and the panel used for UI.
     * It throws an exception if an attempt is made to place the player or the
     * monster in an invalid location.
     *
     * @param u the logged in user
     *
     * @throws Exception the exception
     */
    public Game(User u) throws Exception {
        pause = false;
        restart = false;
        load = false;
        user = u;
        loadGameSettings();
        grid = new Grid(settings.gridStructure);
        trap = new Trap(grid);
        player = new Player(grid, trap, 0, 0, Game.settings.initialEnergy);
        monster = new Monster(grid, player, trap, 0, settings.gridStructure.getSize() - 1);
        bp = new BoardPanel(grid, player, monster, trap, this);

        // Create a separate panel and add all the buttons
        setupControls();
        add(bp, BorderLayout.CENTER);
        add(pnMain, BorderLayout.SOUTH);
    }

    private void loadResults() {

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
        grid = null;
        loadGameSettings();
        grid = new Grid(settings.gridStructure);
        trap = null;
        trap = new Trap(grid);
        player = null;
        player = new Player(grid, trap, 0, 0, Game.settings.initialEnergy);
        monster = null;
        monster = new Monster(grid, player, trap, 0, settings.gridStructure.getSize() - 1);
        bp.update(grid, player, monster, trap, this);
        btStart.setText("Start");
        btPause.setText("Pause");
        lbStatus.setText("Welcome " + user.getName());
        bp.repaint();
    }

    /**
     * Delay by specified time in ms
     *
     * @param time the time in milliseconds
     */
    private void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Change grid configuration.
     * <p>
     * This method will help the user in building a Grid structure
     * object, save it in his settings and restarting the game.
     */
    public void changeGrid() {

        pauseGame(true);

        GridStructure gs = null;
        String size = JOptionPane.showInputDialog(this, "Enter grid size bigger than 3.\n\nExample: 8");

        if (size == null || size.isEmpty()) return;

        try {
            gs = new GridStructure(Integer.parseInt(size));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String columns = JOptionPane.showInputDialog(this, "Enter Columns indexes separated by comma." +
                "\nKeep Space between columns." +
                "\nIndex starts from 0 to " + (gs.getSize() - 1) + ".\n\nExample: 3,5");

        if (columns == null || columns.isEmpty()) return;

        try {
            String[] cols = columns.split(",");
            for (int x = 0; x < cols.length; x++) {
                gs.addColumn(Integer.parseInt(cols[x]));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rows = JOptionPane.showInputDialog(this, "Enter Rows indexes separated by comma." +
                "\nKeep Space between rows." +
                "\nIndex starts from 0 to " + (gs.getSize() - 1) + ".\n\nExample: 3,5");

        if (rows == null || rows.isEmpty()) return;

        try {
            String[] ros = rows.split(",");
            for (int x = 0; x < ros.length; x++) {
                gs.addRow(Integer.parseInt(ros[x]));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        settings.gridStructure = gs;
        saveSettings();
        player.setReady(true);
        restart = true;
        pauseGame(false);
    }

    public void saveOrLoad() {
        // pause the game
        pauseGame(true);

        /*
         * display save or load message
         */
        Object[] options = new String[]{"Save Game", "Load Game", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, "Please select an option", "Save & Load", JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, null);
        switch (option) {
            case 0: // save
                saveGame();
                break;
            case 1: // load
                loadGame();
                break;
            default:
                break;
        }

    }

    /**
     * Save the current game.
     */
    public void saveGame() {
        pauseGame(true);
        try {
            user.saveGame(this, settings);
            JOptionPane.showMessageDialog(null, "Game Saved\n" +
                    "You can load the game the next time you login", null, JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while trying to save the game.\n\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void pauseGame(boolean pa) {
        pause = pa;
        if (pa)
            btPause.setText("Resume");
        else
            btPause.setText("Pause");
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
            audio.playAudio("background.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the game's status message
     */
    private void updateStatusMessage() {
        String message;
        if (time < Game.settings.timeAllowed) { // players has been eaten up
            message = "Player Lost";
            user.increaseLoss();
            try {
                audio.playAudio("lost.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // players won
            message = "Player Won";
            user.increaseWins();
            try {
                audio.playAudio("win.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        lbStatus.setText(message);
    }

    /**
     * This method waits until play is ready (until start button is pressed)
     * after which it updates the moves in turn until time runs out (player won)
     * or player is eaten up (player lost).
     * <p>
     * it will return the game status which will need to be performed, this include:
     * - Restart
     * - Load
     *
     * @return game status
     */
    public String play() {

        while (!player.isReady())
            delay(100);

        // loading a game
        if (load) {
            audio.stopAudio();
            return "load";
        }

        // reset game variables
        prepareToStartGame();

        do {
            while (pause)
                delay(1000);

            // update trap timers if active
            trap.update();

            Cell newPlayerCell = player.move();
            if (newPlayerCell == monster.getCell() && !trap.isTrapped(monster.getCell()))
                break;

            // reset to no direction
//            player.setDirection(' ');

            Cell newMonsterCell = monster.move();
            if (newMonsterCell == player.getCell() && !trap.isTrapped(monster.getCell()))
                break;

            // update time and time label
            time++;
            lbTime.setText("" + (Game.settings.timeAllowed - time));

            // update player's energy label
            lbEnergy.setText("" + player.getEnergy());

            // delay and repaint
            delay(Game.settings.gameSpeed);
            bp.repaint();
        } while (time < Game.settings.timeAllowed && !restart && !load);

        audio.stopAudio();

        /*
         * update game status, player loss and wins
         * if the player is not loading a saved game
         */
        if (!load || !restart)
            updateStatusMessage();

        delay(2500);

        if (load)
            return "load"; // load a saved game
        return "restart"; // restart the game
    }
}