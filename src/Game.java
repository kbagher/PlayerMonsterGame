import javax.swing.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* This class is the main System level class which creates all the objects 
 * representing the game logic (model) and the panel for user interaction. 
 * It also implements the main game loop 
 */

public class Game extends JFrame implements Runnable {

    private JButton trapbtn = new JButton("trap");
    private JButton up = new JButton("up");
    private JButton down = new JButton("down");
    private JButton left = new JButton("left");
    private JButton right = new JButton("right");
    private JButton start = new JButton("start");
    private JLabel mLabel = new JLabel("Time Remaining : " + Settings.TIME_ALLOWED);

    private GameAudioPlayer audio;
    private Grid grid;
    private Player player;
    private Trap trap;
    private Monster monster;
    private BoardPanel bp;
    private boolean pause;


    /* This constructor creates the main model objects and the panel used for UI.
     * It throws an exception if an attempt is made to place the player or the
     * monster in an invalid location.
     */
    public Game() throws Exception {
        pause = false;
        grid = new Grid();
        trap = new Trap(grid);
        player = new Player(grid, trap,0, 0,40);
        monster = new Monster(grid, player, trap,5, 5);
        monster.addSkill(Monster.MonsterSkillsType.INVISIBLE);
        monster.addSkill(Monster.MonsterSkillsType.LEAP);
        bp = new BoardPanel(grid, player, monster, trap,this);

        // Create a separate panel and add all the buttons
        JPanel panel = new JPanel();
        panel.add(trapbtn);
        panel.add(up);
        panel.add(down);
        panel.add(left);
        panel.add(right);
        panel.add(start);
        panel.add(mLabel);

        // add Action listeners to all button events
        trapbtn.addActionListener(bp);
        up.addActionListener(bp);
        down.addActionListener(bp);
        left.addActionListener(bp);
        right.addActionListener(bp);
        start.addActionListener(bp);

        up.setFocusable(false);
        down.setFocusable(false);
        left.setFocusable(false);
        right.setFocusable(false);
        start.setFocusable(false);
        trapbtn.setFocusable(false);

        // add panels to frame
        add(bp, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
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

    public void pauseAnResumeGame(){
        pause=!pause;
    }


    public void playBackgroundMusic(){
        try {
            audio= new GameAudioPlayer();
            audio.playAudio("background.wav");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stopBackgroundMusic(){
        try {
            audio.stopAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePanel(){

    }


    /* This method waits until play is ready (until start button is pressed)
     * after which it updates the moves in turn until time runs out (player won)
     * or player is eaten up (player lost).
     */
    public String play() {
        int time = 0;
        String message;
        player.setDirection(' '); // set to no direction


        while (!player.isReady())
            delay(100);
        do {

            if (pause){
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
            mLabel.setText("Time Remaining : " + (Settings.TIME_ALLOWED - time));
            delay(Settings.GAME_SPEED);
            bp.repaint();

        } while (time < Settings.TIME_ALLOWED);

        if (time < Settings.TIME_ALLOWED) {
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

        mLabel.setText(message);
        return message;
    }

    public static void main(String args[]) throws Exception {
        Game game = new Game();
        Thread t = new Thread(game);
        t.start();
        System.out.println("hello");
//        game.setTitle("Monster Game");
//        game.setSize(700, 700);
//        game.setLocationRelativeTo(null);  // center the frame
//        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        game.setVisible(true);
//        game.playBackgroundMusic();
//        game.play();

    }

    @Override
    public void run() {
        setTitle("Monster Game");
        setSize(700, 700);
        setLocationRelativeTo(null);  // center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        playBackgroundMusic();
        play();
    }
}
