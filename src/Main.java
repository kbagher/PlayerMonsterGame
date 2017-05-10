import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by kassem on 6/5/17.
 */
public class Main {

    private static User showLogin(){
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

        String usr = username.getText();
        String pass = new String(password.getPassword());

        // user does not exists
        User user = User.login(usr,pass);

        if (user==null){
            int answer = JOptionPane.showConfirmDialog(null,"Unknown user, do you want to register using the provided information?","Unknow username",JOptionPane.YES_NO_OPTION);
            if (answer==JOptionPane.YES_OPTION) // TODO: register user
            {
                if (Database.getInstance().register(usr,pass)){
                    JOptionPane.showMessageDialog(null,"User created successfully :D","Registerd", JOptionPane.INFORMATION_MESSAGE);
                    return user;
                }
                else{
                    JOptionPane.showMessageDialog(null,"Could not register :(","Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
            else
                return null;
        }
        return user;
    }

    public static void main(String args[]) throws Exception {

        // login or register a user
        User user;
        while ((user = showLogin()) ==null);

        String status = "";
        Game game=null;
        do {
            // load new game
            if ((status.compareTo("") == 0)){
                game= new Game(user);
            }
            else if (status.compareTo("restart") == 0){
                game.resetGameObjects();
            }
            else if (status.compareTo("load") == 0) {
                System.out.println("load");
                game = user.loadGame();
            }
            game.loadSettingsToView();
//            game.updateSettingsVariables();
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
