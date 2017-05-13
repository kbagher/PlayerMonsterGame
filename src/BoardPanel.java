import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * This panel represents the game board (grid)
 * It also responds to game related events
 * The overridden paintcompnent() is called whenever the board
 * or the pieces needs to be updated 
 */
public class BoardPanel extends JPanel implements ActionListener, KeyListener {

    private Player player;
    private Monster monster;
    private Trap trap;
    private Grid grid;
    private Game game;
    private final int CELLWIDTH = 45;
    private final int CELLHEIGHT = 45;
    private final int LMARGIN = 50;
    private final int TMARGIN = 30;

    /**
     * Instantiates a new Board panel.
     *
     * @param g  the grid
     * @param p  the player
     * @param m  the monster
     * @param t  the trap
     * @param gm the game
     */
    public BoardPanel(Grid g, Player p, Monster m, Trap t, Game gm) {
        update(g, p, m, t, gm);
    }

    /**
     * Update board variables.
     *
     * This is used when a reset is required without
     * reloading the entire GUI
     *
     * @param g  the grid
     * @param p  the player
     * @param m  the monster
     * @param t  the trap
     * @param gm the game
     */
    public void update(Grid g, Player p, Monster m, Trap t, Game gm) {
        player = p;
        grid = g;
        monster = m;
        trap = t;
        game = gm;
        /*
         * remove old key listeners of any
         * and add the new one
         */
        removeKeyListener(this);
        addKeyListener(this);

        setFocusable(true);
    }


    /**
     * Responds to various button clicked messages
     *
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().compareTo("Up") == 0)
            player.setDirection('U');
        else if (((JButton) e.getSource()).getText().compareTo("Down") == 0)
            player.setDirection('D');
        else if (((JButton) e.getSource()).getText().compareTo("Left") == 0)
            player.setDirection('L');
        else if (((JButton) e.getSource()).getText().compareTo("Right") == 0)
            player.setDirection('R');
        else if (((JButton) e.getSource()).getText().compareTo("Start") == 0)
            player.setReady(true);
        else if (((JButton) e.getSource()).getText().compareTo("Trap") == 0)
            player.putTrap();
        else if (((JButton) e.getSource()).getText().compareTo("Save Settings") == 0)
            game.saveSettings();
        else if (((JButton) e.getSource()).getText().compareTo("Restart") == 0)
            game.restartGame();
        else if (((JButton) e.getSource()).getText().compareTo("Pause") == 0)
            game.pauseAnResumeGame();
        else if (((JButton) e.getSource()).getText().compareTo("Resume") == 0)
            game.pauseAnResumeGame();
        else if (((JButton) e.getSource()).getText().compareTo("Save/Load") == 0)
            game.saveOrLoad();
        else if (((JButton) e.getSource()).getText().compareTo("Grid Structure") == 0)
            game.changeGrid();
    }

    /**
     * Returns the x coordinate based on left margin and cell width
     *
     * @param col column
     * @return
     */
    private int xCor(int col) {
        return LMARGIN + col * CELLWIDTH;
    }

    /**
     * Returns the y coordinate based on top margin and cell height
     *
     * @param row row
     * @return
     */
    private int yCor(int row) {
        return TMARGIN + row * CELLHEIGHT;
    }

    /**
     * Redraws the board and the pieces
     * Called initially and in response to repaint()
     *
     * @param gr Graphics reference
     */
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        ArrayList<Cell> cells = grid.getAllCells();
        Cell cell;

        for (int i = 0; i < cells.size(); i++) {
            cell = cells.get(i);
            if (grid.containsColumn(cell.col) && grid.containsRow(cell.row))
                gr.setColor(Color.cyan);
            else
                gr.setColor(Color.white);

            gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
            gr.setColor(Color.black);
            gr.drawRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
            if (!cell.nougat.isConsumed()) {
                try {
                    ImageIcon icon = new ImageIcon("assets/coin.png");
                    icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 15);
                } catch (Exception e) {
                    gr.setColor(Color.YELLOW);
                    gr.fillOval(xCor(cell.col) + CELLWIDTH / 4, yCor(cell.row) + CELLWIDTH / 4, CELLWIDTH * 1 / 2, CELLHEIGHT * 1 / 2);
                }
            }
        }

        /*
         * Draw the trap
         */
        if (trap.isSet()) {
            cell = trap.getCell();
            try {
                ImageIcon icon = new ImageIcon("assets/trap.png");
                icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 15);
            } catch (Exception e) {
                gr.setColor(Color.BLUE);
                gr.fillOval(xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 8, CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4);
                gr.setColor(Color.white);
                gr.drawString("T", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
            }
        }


        cell = player.getCell();
        ImageIcon icon;

        /*
         * Draw the Player
         */
        try {
            icon = new ImageIcon("assets/player.png");
            icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 5, yCor(cell.row) + CELLWIDTH / 15);
        } catch (Exception e) {
            gr.setColor(Color.red);
            gr.fillOval(xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 8, CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4);
            gr.setColor(Color.white);
            gr.drawString("P", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
        }

        /*
         * Draw the Monster
         */
        if (monster.viewable()) { // monster is visible
            cell = monster.getCell();
            try {
                icon = new ImageIcon("assets/monster.png");
                icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 5, yCor(cell.row) + CELLWIDTH / 15);
            } catch (Exception e) {
                gr.setColor(Color.black);
                gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
                gr.setColor(Color.white);
                gr.drawString("M", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*
         * Handle player direction according to the pressed key
         */
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (player.getDirection() == 'L')
                player.setDirection(' ');
            else
                player.setDirection('L');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (player.getDirection() == 'R')
                player.setDirection(' ');
            else
                player.setDirection('R');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (player.getDirection() == 'D')
                player.setDirection(' ');
            else
                player.setDirection('D');
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (player.getDirection() == 'U')
                player.setDirection(' ');
            else
                player.setDirection('U');
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        /*
         * Handle other keyboard keys
         */

        // T Trap
        if (e.getKeyCode() == KeyEvent.VK_T) {
            player.putTrap();
        }
        // P Pause
        else if (e.getKeyCode() == KeyEvent.VK_P) {
            game.pauseAnResumeGame();
        }
        // Space Skip
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.skip();
        }
        // S Start the game
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.setReady(true);
        }
    }
}