import javax.swing.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/* This class is the main System level class which creates all the objects 
 * representing the game logic (model) and the panel for user interaction. 
 * It also implements the main game loop 
 */

public class Game extends JFrame {

    // labels
    private JLabel mLabel = new JLabel("Time Remaining : " + Settings.getTimeAllowed());

    JPanel pnMain;
    JTabbedPane tbpControl;

    JPanel pnDashboard;
    JButton btLeft;
    JButton btUp;
    JButton btRight;
    JButton btDown;
    JButton btSave;
    JButton btStart;
    JButton btPause;
    JLabel lbLabel14;
    JLabel lbLabel15;
    JLabel lbTime;
    JLabel lbEnergy;
    JLabel lbStatus;

    JPanel pnSettings;
    JLabel lbLabel1;
    JLabel lbLabel2;
    JLabel lbLabel3;
    JLabel lbLabel4;
    JTextField tfSpeed;
    JTextField tfTime;
    JTextField tfInitialEnergy;
    JTextField tfStepEnergy;
    JTextField tfCoinEnergy;
    JTextField tfTrapEnergy;
    JTextField tfTrapEffectDuration;
    JTextField tfTrapLifetime;
    JLabel lbLabel6;
    JLabel lbLabel7;
    JLabel lbLabel8;
    JLabel lbLabel9;
    JLabel lbLabel10;
    JLabel lbLabel11;
    JCheckBox cbPlayerSkip;
    JCheckBox cbPlayerTrap;
    JCheckBox cbMonsterLeap;
    JCheckBox cbMonsterHide;
    JButton btSaveSettings;

    JPanel pnResults;
    JTextArea taScoreResults;

    // class variables
    private GameAudioPlayer audio;
    private Grid grid;
    private Player player;
    private Trap trap;
    private Monster monster;
    private BoardPanel bp;
    private boolean pause;
    private boolean restart;


    public void setupControls() {
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

        lbLabel14 = new JLabel("Time");
        gbcDashboard.gridx = 3;
        gbcDashboard.gridy = 0;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbcDashboard.insets = new Insets(0, 20, 0, 0);
        gbDashboard.setConstraints(lbLabel14, gbcDashboard);
        pnDashboard.add(lbLabel14);

        lbLabel15 = new JLabel("Energy");
        gbcDashboard.gridx = 3;
        gbcDashboard.gridy = 1;
        gbcDashboard.gridwidth = 1;
        gbcDashboard.gridheight = 1;
        gbcDashboard.fill = GridBagConstraints.BOTH;
        gbcDashboard.weightx = 1;
        gbcDashboard.weighty = 1;
        gbcDashboard.anchor = GridBagConstraints.NORTH;
        gbcDashboard.insets = new Insets(0, 20, 0, 0);
        gbDashboard.setConstraints(lbLabel15, gbcDashboard);
        pnDashboard.add(lbLabel15);

        lbTime = new JLabel("");
        lbTime.setText("" + Settings.getTimeAllowed());
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

        lbStatus = new JLabel("Welcome Kassem");
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

        lbLabel1 = new JLabel("Speed");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbLabel1, gbcSettings);
        pnSettings.add(lbLabel1);

        lbLabel2 = new JLabel("Time");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbLabel2, gbcSettings);
        pnSettings.add(lbLabel2);

        lbLabel3 = new JLabel("Initial energy");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbLabel3, gbcSettings);
        pnSettings.add(lbLabel3);

        lbLabel4 = new JLabel("Step Energy");
        gbcSettings.gridx = 0;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(lbLabel4, gbcSettings);
        pnSettings.add(lbLabel4);

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

        tfCoinEnergy = new JTextField();
        gbcSettings.gridx = 3;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 0;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbSettings.setConstraints(tfCoinEnergy, gbcSettings);
        pnSettings.add(tfCoinEnergy);

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

        lbLabel6 = new JLabel("Coins energy");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbLabel6, gbcSettings);
        pnSettings.add(lbLabel6);

        lbLabel7 = new JLabel("Trap energy");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 1;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbLabel7, gbcSettings);
        pnSettings.add(lbLabel7);

        lbLabel8 = new JLabel("Trap effect duration");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 2;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbLabel8, gbcSettings);
        pnSettings.add(lbLabel8);

        lbLabel9 = new JLabel("Trap lifetime");
        gbcSettings.gridx = 2;
        gbcSettings.gridy = 3;
        gbcSettings.gridwidth = 1;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbLabel9, gbcSettings);
        pnSettings.add(lbLabel9);

        lbLabel10 = new JLabel("Player Skills");
        gbcSettings.gridx = 6;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 10);
        gbSettings.setConstraints(lbLabel10, gbcSettings);
        pnSettings.add(lbLabel10);

        lbLabel11 = new JLabel("Monster Skills");
        gbcSettings.gridx = 4;
        gbcSettings.gridy = 0;
        gbcSettings.gridwidth = 2;
        gbcSettings.gridheight = 1;
        gbcSettings.fill = GridBagConstraints.BOTH;
        gbcSettings.weightx = 1;
        gbcSettings.weighty = 1;
        gbcSettings.anchor = GridBagConstraints.NORTH;
        gbcSettings.insets = new Insets(0, 20, 0, 0);
        gbSettings.setConstraints(lbLabel11, gbcSettings);
        pnSettings.add(lbLabel11);

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

    public void loadSettingsToView() {
        tfSpeed.setText("" + Settings.getGameSpeed());
        tfTime.setText("" + Settings.getTimeAllowed());
        tfInitialEnergy.setText("" + Settings.getCaloriesInitialValue());
        tfStepEnergy.setText("" + Settings.getStepCalories());
        tfCoinEnergy.setText("" + Settings.getNougatCalories());
        tfTrapEnergy.setText("" + Settings.getTrapRequiredEnergy());
        tfTrapEffectDuration.setText("" + Settings.getTrapEffectDuration());
        tfTrapLifetime.setText("" + Settings.getTrapDuration());
        lbTime.setText("" + Settings.getTimeAllowed());
        lbEnergy.setText("" + Settings.getCaloriesInitialValue());

        cbPlayerSkip.setSelected(player.hasSkill(PlayerSkills.PlayerSkillsType.SKIP));
        cbPlayerTrap.setSelected(player.hasSkill(PlayerSkills.PlayerSkillsType.TRAP));
        cbMonsterLeap.setSelected(monster.hasSkill(MonsterSkills.MonsterSkillsType.LEAP));
        cbMonsterHide.setSelected(monster.hasSkill(MonsterSkills.MonsterSkillsType.INVISIBLE));
    }

    public void updateSettingsVariables() {
        Settings.setGameSpeed(Integer.parseInt(tfSpeed.getText()));
        Settings.setTimeAllowed(Integer.parseInt(tfTime.getText()));
        Settings.setCaloriesInitialValue(Integer.parseInt(tfInitialEnergy.getText()));
        Settings.setStepCalories(Integer.parseInt(tfStepEnergy.getText()));
        Settings.setNougatCalories(Integer.parseInt(tfCoinEnergy.getText()));
        Settings.setTrapRequiredEnergy(Integer.parseInt(tfTrapEnergy.getText()));
        Settings.setTrapEffectDuration(Integer.parseInt(tfTrapEffectDuration.getText()));
        Settings.setTrapDuration(Integer.parseInt(tfTrapLifetime.getText()));

        // player skipp skill
        if (cbPlayerSkip.isSelected())
            player.addSkill(PlayerSkills.PlayerSkillsType.SKIP);
        else
            player.removeSkill(PlayerSkills.PlayerSkillsType.SKIP);

        // player trap skill
        if (cbPlayerTrap.isSelected())
            player.addSkill(PlayerSkills.PlayerSkillsType.TRAP);
        else
            player.removeSkill(PlayerSkills.PlayerSkillsType.TRAP);

        // monster leap skill
        if (cbMonsterLeap.isSelected())
            monster.addSkill(Monster.MonsterSkillsType.LEAP);
        else
            monster.removeSkill(Monster.MonsterSkillsType.LEAP);

        // monster invisible skill
        if (cbMonsterHide.isSelected())
            monster.addSkill(Monster.MonsterSkillsType.INVISIBLE);
        else
            monster.removeSkill(Monster.MonsterSkillsType.INVISIBLE);
    }

    private void prepareToStartGame() {
        player.setCalories(Settings.getCaloriesInitialValue());
        btStart.setText("Restart");
    }

    /* This constructor creates the main model objects and the panel used for UI.
     * It throws an exception if an attempt is made to place the player or the
     * monster in an invalid location.
     */
    public Game() throws Exception {
        pause = false;
        restart = false;
        grid = new Grid();
        trap = new Trap(grid);
        player = new Player(grid, trap, 0, 0, Settings.getCaloriesInitialValue());
        monster = new Monster(grid, player, trap, 5, 5);
        bp = new BoardPanel(grid, player, monster, trap, this);

        // Create a separate panel and add all the buttons
        setupControls();
        loadSettingsToView();
        add(bp, BorderLayout.CENTER);
        add(pnMain, BorderLayout.SOUTH);
    }

    // method to delay by specified time in ms
    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void saveGame() {
        String serializedObject = "";

        // serialize the object
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(this);
            so.flush();
            serializedObject = bo.toString();
            System.out.println(serializedObject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void pauseAnResumeGame() {
        if (!pause){
            pause = !pause;
            btPause.setText("Resume");
        }
        else{
            pause = !pause;
            btPause.setText("Pause");
        }
    }
    public void restartGame() {
        System.out.println("restart");
        restart=true;
    }

    public void playBackgroundMusic() {
        try {
            audio = new GameAudioPlayer();
            audio.playAudio("background.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        try {
            audio.stopAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* This method waits until play is ready (until start button is pressed)
     * after which it updates the moves in turn until time runs out (player won)
     * or player is eaten up (player lost).
     */
    public void play() {
        int time = 0;
        String message;
        player.setDirection(' '); // set to no direction

        while (!player.isReady())
            delay(100);

        prepareToStartGame();

        do {
            if (pause) {
                delay(1000);
                continue;
            }

            trap.update();

            Cell newPlayerCell = player.move();
            if (newPlayerCell == monster.getCell())
                break;
            player.setDirection(' ');   // reset to no direction

            Cell newMonsterCell = monster.move();
            if (newMonsterCell == player.getCell())
                break;

            // update time and repaint
            time++;
            lbTime.setText("" + (Settings.getTimeAllowed() - time));
            lbEnergy.setText("" + player.getCalories());
            delay(Settings.getGameSpeed());
            bp.repaint();
        } while (time < Settings.getTimeAllowed() && !restart);

        if (time < Settings.getTimeAllowed()) {
            // players has been eaten up
            message = "Player Lost";
            try {
                audio.stopAudio();
                audio.playAudio("lost.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            message = "Player Won";
            try {
                audio.stopAudio();
                audio.playAudio("win.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        lbStatus.setText(message);

        do {
            delay(1000);
        } while (!restart);

    }

    public static void main(String args[]) throws Exception {
        boolean firstRun = true;

        do {
            Game game = new Game();
            game.player.setReady(!firstRun);
            game.setTitle("Monster Game");
            game.setSize(700, 700);
            game.setLocationRelativeTo(null);  // center the frame
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.setVisible(true);
            game.playBackgroundMusic();
            game.play();
            firstRun=false;
        } while (true);
    }
}