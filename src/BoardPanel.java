import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

/* This panel represents the game board (grid) 
 * It also responds to game related events
 * The overridden paintcompnent() is called whenever the board
 * or the pieces needs to be updated 
 */
public class BoardPanel extends JPanel implements ActionListener,KeyListener  {

    private Player player;
    private Monster monster;
    private Grid grid;
    private Graphics gr;
    private Game game;
    private final int CELLWIDTH = 40;
    private final int CELLHEIGHT = 40;
    private final int LMARGIN = 100;
    private final int TMARGIN = 100;

    public BoardPanel(Grid g, Player p, Monster m) {
        player = p;
        grid = g;
        monster = m;
        gr = this.getGraphics();
        addKeyListener(this);
        setFocusable(true);
    }

    /* responds to various button clicked messages */
    public void actionPerformed(ActionEvent e) {
        System.out.println("called");
        if (((JButton) e.getSource()).getText().compareTo("up") == 0)
            player.setDirection('U');
        else if (((JButton) e.getSource()).getText().compareTo("down") == 0)
            player.setDirection('D');
        else if (((JButton) e.getSource()).getText().compareTo("left") == 0)
            player.setDirection('L');
        else if (((JButton) e.getSource()).getText().compareTo("right") == 0)
            player.setDirection('R');
        else if (((JButton) e.getSource()).getText().compareTo("start") == 0)
            player.setReady(true);
        else if (((JButton) e.getSource()).getText().compareTo("trap") == 0)
            player.putTrap();
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

        }

        if (grid.getCurrentTrap()!=null){
            cell= grid.getCurrentTrap().getCell();
            ImageIcon icon = new ImageIcon("./trap.png");
            icon.paintIcon(this, gr, xCor(cell.col) +CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 15);
//            gr.setColor(Color.BLUE);
//            gr.fillOval(xCor(cell.col)+CELLWIDTH/8, yCor(cell.row)+CELLWIDTH/8, CELLWIDTH*3/4, CELLHEIGHT*3/4);
//            gr.setColor(Color.white);
//            gr.drawString("T",xCor(cell.col)+CELLWIDTH/3, yCor(cell.row)+2*CELLWIDTH/3);
        }

        cell = player.getCell();
        ImageIcon icon = new ImageIcon("./player.png");
        icon.paintIcon(this, gr, xCor(cell.col) +CELLWIDTH / 5, yCor(cell.row) + CELLWIDTH / 15);


//        cell = player.getCell();
//        gr.setColor(Color.red);
//        gr.fillOval(xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 8, CELLWIDTH * 3 / 4, CELLHEIGHT * 3 / 4);
//        gr.setColor(Color.white);
//        gr.drawString("P", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);

        if (monster.viewable()) {
            cell = monster.getCell();
            icon = new ImageIcon("./monster.png");
            icon.paintIcon(this, gr, xCor(cell.col) +CELLWIDTH / 5, yCor(cell.row) + CELLWIDTH / 15);

//            gr.setColor(Color.black);
//            gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
//            gr.setColor(Color.white);
//            gr.drawString("M", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        System.out.println("keyTyped: "+e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("keyPressed: "+e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setDirection('L');
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDirection('R');
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDirection('D');
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.setDirection('U');
        }
        else if (e.getKeyCode() == KeyEvent.VK_T) {
            player.putTrap();
        }

//        System.out.println("keyReleased: "+e);
    }
}