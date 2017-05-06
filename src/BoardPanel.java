import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

/* This panel represents the game board (grid) 
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

    public BoardPanel(Grid g, Player p, Monster m, Trap t, Game gm) {
        update(g,p,m,t,gm);
    }

    public void update(Grid g, Player p, Monster m, Trap t, Game gm){
        player = p;
        grid = g;
        monster = m;
        trap = t;
        game = gm;
        removeKeyListener(this);
        addKeyListener(this);
        setFocusable(true);
    }


    /* responds to various button clicked messages */
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().compareTo("Up") == 0)
            player.setDirection('U');
        else if (((JButton) e.getSource()).getText().compareTo("Down") == 0)
            player.setDirection('D');
        else if (((JButton) e.getSource()).getText().compareTo("Left") == 0)
            game.loadGame();
//            player.setDirection('L');
        else if (((JButton) e.getSource()).getText().compareTo("Right") == 0)
            player.setDirection('R');
        else if (((JButton) e.getSource()).getText().compareTo("Start") == 0)
            player.setReady(true);
        else if (((JButton) e.getSource()).getText().compareTo("Trap") == 0)
            player.putTrap();
        else if (((JButton) e.getSource()).getText().compareTo("Save Settings") == 0)
            game.updateSettingsVariables();
        else if (((JButton) e.getSource()).getText().compareTo("Restart") == 0)
            game.restartGame();
        else if (((JButton) e.getSource()).getText().compareTo("Pause") == 0)
            game.pauseAnResumeGame();
        else if (((JButton) e.getSource()).getText().compareTo("Resume") == 0)
            game.pauseAnResumeGame();
        else if (((JButton) e.getSource()).getText().compareTo("Save") == 0)
            game.saveGame();
    }

    /* returns the x coordinate based on left margin and cell width */
    private int xCor(int col) {
        return LMARGIN + col * CELLWIDTH;
    }

    /* returns the y coordinate based on top margin and cell height */
    private int yCor(int row) {
        return TMARGIN + row * CELLHEIGHT;
    }

    /* Redraws the board and the pieces
     * Called initially and in response to repaint()
     */
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Cell cells[] = grid.getAllCells();
        Cell cell;
        for (int i = 0; i < cells.length; i++) {
            cell = cells[i];
            if (cell.col % 5 == 0 && cell.row % 5 == 0)
                gr.setColor(Color.cyan);
            else
                gr.setColor(Color.white);
            gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
            gr.setColor(Color.black);
            gr.drawRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
            if (!cell.nougat.isConsumed()) {
//                ImageIcon icon = new ImageIcon("assets/coin.png");
//                icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 15);
                gr.setColor(Color.YELLOW);
                gr.fillOval(xCor(cell.col) + CELLWIDTH / 4, yCor(cell.row) + CELLWIDTH / 4, CELLWIDTH * 1 / 2, CELLHEIGHT * 1 / 2);
            }

        }

        if (trap.isSet()) {
            cell = trap.getCell();
//            ImageIcon icon = new ImageIcon("assets/trap.png");
//            icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 15);
            gr.setColor(Color.BLUE);
            gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
            gr.setColor(Color.white);
            gr.drawString("T",xCor(cell.col)+CELLWIDTH/3, yCor(cell.row)+2*CELLWIDTH/3);
        }

        cell = player.getCell();
//        ImageIcon icon = new ImageIcon("assets/player.png");
//        icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 5, yCor(cell.row) + CELLWIDTH / 15);


        cell = player.getCell();
        gr.setColor(Color.red);
        gr.fillOval(xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 8, CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4);
        gr.setColor(Color.white);
        gr.drawString("P", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);

        if (monster.viewable()) {
            cell = monster.getCell();
//            icon = new ImageIcon("assets/monster.png");
//            icon.paintIcon(this, gr, xCor(cell.col) + CELLWIDTH / 5, yCor(cell.row) + CELLWIDTH / 15);

            gr.setColor(Color.black);
            gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
            gr.setColor(Color.white);
            gr.drawString("M", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDirection('L');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDirection('R');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDirection('D');
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.setDirection('U');
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_T) {
            player.putTrap();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            game.pauseAnResumeGame();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.skip();
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            player.setReady(true);
        }
    }
}