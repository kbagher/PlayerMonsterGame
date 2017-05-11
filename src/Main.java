import javax.swing.*;
import java.awt.*;

/**
 * Created by kassem on 6/5/17.
 */
public class Main {

    /**
     * user login and register message
     *
     * @return the logged in or registered user
     */
    private static User showLogin() {

        /*
        Main panel to hold labels and textfields
        */
        JPanel loginPanel = new JPanel(new BorderLayout(5, 1));

        /*
        Labels panel
        */
        JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        labelsPanel.add(new JLabel("Username:", SwingConstants.LEFT));
        labelsPanel.add(new JLabel("Password: ", SwingConstants.LEFT));
        loginPanel.add(labelsPanel, BorderLayout.WEST);

        /*
        Textfields panel
        */
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField(10);
        fieldsPanel.add(username);
        JPasswordField password = new JPasswordField(10);
        fieldsPanel.add(password);
        loginPanel.add(fieldsPanel, BorderLayout.CENTER);

        // message button options
        Object[] options = new String[]{"Login/Register"};

        JOptionPane.showOptionDialog(null, loginPanel, "User login", JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, null);

        String usr = username.getText();
        String pass = new String(password.getPassword());

        // retrieve the user object using provided data
        User user = User.login(usr, pass);

        // user does not exist
        if (user == null) {
            int answer = JOptionPane.showConfirmDialog(null, "Unknown user, do you want to register using the provided information?", "Unknow username", JOptionPane.YES_NO_OPTION);

            // register new user using the provided date
            if (answer == JOptionPane.YES_OPTION)
                if (Database.getInstance().register(usr, pass)) {
                    JOptionPane.showMessageDialog(null, "User created successfully :D", "Registerd", JOptionPane.INFORMATION_MESSAGE);
                    return user;
                } else {
                    JOptionPane.showMessageDialog(null, "Could not register :(", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
        }

        return user;
    }

    public static void main(String args[]) throws Exception {

        // login or register a user
        User user;
        while ((user = showLogin()) == null) ;

        // game status, new, restart and reload
        String status = "new";
        Game game = null;
        do {
            // start a new game
            if ((status.compareTo("new") == 0)) {
                game = new Game(user);
                game.loadSettings();
            }
            // restart game
            else if (status.compareTo("restart") == 0) {
                game.resetGameObjects();
                game.loadSettings();
            }
            // load user's saved game
            else if (status.compareTo("load") == 0) {
                System.out.println("load");
                game = user.loadGame();
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
