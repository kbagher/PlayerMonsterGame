import javax.swing.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by kassem on 6/5/17.
 */
public class Main {

    private static Game getSavedGameObject() throws Exception {
        FileInputStream fis = new FileInputStream("mybean.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Game game = (Game) ois.readObject();
        ois.close();
        return game;
    }


    public static void main(String args[]) throws Exception {
        String status = "";
        Game game=null;
        do {
            if ((status.compareTo("") == 0)){
                game= new Game();
            }
            else if (status.compareTo("restart") == 0){
                game.loadSettingsToView();
                game.updateSettingsVariables();
                game.resetGameObjects();
            }
            else if (status.compareTo("load") == 0) {
                System.out.println("load");
                game = getSavedGameObject();
                game.loadSettingsToView();
                game.updateSettingsVariables();
            }
            game.setTitle("Monster Game");
            game.setSize(700, 750);
            game.setLocationRelativeTo(null);  // center the frame
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.setVisible(true);
            game.playBackgroundMusic();
            status = game.play();
        } while (true);
    }
}
