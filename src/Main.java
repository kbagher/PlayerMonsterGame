import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by kassem on 6/5/17.
 */
public class Main {

    private static void showLogin(){
        JPanel loginPanel = new JPanel(new BorderLayout(5,1));

        JPanel labelsPanel = new JPanel(new GridLayout(0,1,2,2));
        labelsPanel.add(new JLabel("Username:", SwingConstants.LEFT));
        labelsPanel.add(new JLabel("Password: ", SwingConstants.LEFT));
        loginPanel.add(labelsPanel, BorderLayout.WEST);

        JPanel fieldsPanel = new JPanel(new GridLayout(0,1,2,2));
        JTextField username = new JTextField(10);
        fieldsPanel.add(username);
        JPasswordField password = new JPasswordField(10);
        fieldsPanel.add(password);
        loginPanel.add(fieldsPanel, BorderLayout.CENTER);

        Object[] options = new String[]{"Login/Register"};

        JOptionPane.showOptionDialog(null, loginPanel, "User login", JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, null);
        System.out.println("Username: " + username.getText());
        String passText = new String(password.getPassword());
        System.out.println("Password: " + passText);

    }

    private static Game getSavedGameObject() throws Exception {
        FileInputStream fis = new FileInputStream("mybean.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Game game = (Game) ois.readObject();
        ois.close();
        return game;
    }

    public static void main(String args[]) throws Exception {


        showLogin();

        String status = "";
        Game game=null;
        do {

            // load new game
            if ((status.compareTo("") == 0)){
                game= new Game();
            }
            else if (status.compareTo("restart") == 0){
                game.resetGameObjects();
            }
            else if (status.compareTo("load") == 0) {
                System.out.println("load");
                game = getSavedGameObject();
            }
            game.loadSettingsToView();
            game.updateSettingsVariables();
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
