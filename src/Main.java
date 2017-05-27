import javax.swing.*;
import java.awt.*;

/**
 * Created by kassem on 6/5/17.
 */
public class Main {

    /**
     * Show a registration dialog
     *
     * @return true if user has registered
     */
    private static boolean showRegister() {
        /**
         * Main panel to hold labels and textfields
         */
        JPanel loginPanel = new JPanel(new BorderLayout(5, 1));

        /**
         * Labels panel
         */
        JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        labelsPanel.add(new JLabel("Full Name:", SwingConstants.LEFT));
        labelsPanel.add(new JLabel("Address: ", SwingConstants.LEFT));
        labelsPanel.add(new JLabel("Username: ", SwingConstants.LEFT));
        labelsPanel.add(new JLabel("Password: ", SwingConstants.LEFT));
        loginPanel.add(labelsPanel, BorderLayout.WEST);

        /**
         * Text fields panel
         */
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        // Full name
        JTextField name = new JTextField(15);
        fieldsPanel.add(name);
        // Address
        JTextField address = new JTextField(15);
        fieldsPanel.add(address);
        // Username
        JTextField username = new JTextField(15);
        fieldsPanel.add(username);
        // Password
        JPasswordField password = new JPasswordField(15);
        fieldsPanel.add(password);

        loginPanel.add(fieldsPanel, BorderLayout.CENTER);

        /**
         * Message buttons
         * 0 Register
         * 1 Cancel
         */
        Object[] options = new String[]{"Register", "Cancel"};

        int option = JOptionPane.showOptionDialog(null, loginPanel, "User Registration", JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, null);
        if (option == 1)
            return false; // cancel

        /**
         * check if fields are empty or not
         */
        if (name.getText().isEmpty() || address.getText().isEmpty() || username.getText().isEmpty() || password.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "All fields are required", "incomplete fields", JOptionPane.OK_OPTION);
            return false;
        }

        try {
            User.register(name.getText(), address.getText(), username.getText(), new String(password.getPassword()));
            JOptionPane.showMessageDialog(null, "User Registered Successfully", null, JOptionPane.INFORMATION_MESSAGE);
            return true; // registered
        } catch (UsernameAlreadyExistsException e) {
            JOptionPane.showMessageDialog(null, "Username is already taken", "User Exists", JOptionPane.ERROR_MESSAGE);
            return false; // username already exists
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Unknown Error", JOptionPane.ERROR_MESSAGE);
            return false; // unknown error
        }
    }


    private static boolean loadGameMenu(User u) {

        try {
            u.loadGame();
            int option = JOptionPane.showOptionDialog(null, "Do you want to load a saved game?", "Load game", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);
            return option == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        int option = JOptionPane.showOptionDialog(null, "You don't have any saved game :(" +
                        "\n\nHint: You can always load a saved game after you login", "Load game", JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, new String[]{"ok"}, null);
        return false;
    }

    /**
     * user login and register message
     *
     * @return the logged in or registered user
     */
    private static User showLogin() {
        /**
         * Main panel to hold labels and text fields
         */
        JPanel loginPanel = new JPanel(new BorderLayout(5, 1));

        /**
         * Labels panel
         */
        JPanel labelsPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        labelsPanel.add(new JLabel("Username:", SwingConstants.LEFT));
        labelsPanel.add(new JLabel("Password: ", SwingConstants.LEFT));
        loginPanel.add(labelsPanel, BorderLayout.WEST);

        /**
         * Text fields panel
         */
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField(10);
        fieldsPanel.add(username);
        JPasswordField password = new JPasswordField(10);
        fieldsPanel.add(password);
        loginPanel.add(fieldsPanel, BorderLayout.CENTER);

        /**
         * Message buttons
         * 0 Login
         * 1 Register
         * 2 Cancel
         */
        Object[] options = new String[]{"Login", "Register", "Cancel"};

        int option = JOptionPane.showOptionDialog(null, loginPanel, "User login", JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, "Login");

        /**
         * handling pressed button
         */
        if (option == 2)
            System.exit(0); // cancel
        else if (option == 1) {
            showRegister(); // register
            return null;
        }

        String usr = username.getText(); // username
        String pass = new String(password.getPassword()); // password

        /**
         * Login user
         */
        try {
            return User.login(usr, pass);
        } catch (WrongPasswordException e) { // wrong password
            JOptionPane.showMessageDialog(null, "Wrong password", null, JOptionPane.ERROR_MESSAGE);
        } catch (UserNotFoundException e) { // user not found
            JOptionPane.showMessageDialog(null, "Unknown username", null, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { // unknown error
            JOptionPane.showMessageDialog(null, e.getMessage(), "Unknown error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static void main(String args[]) throws Exception {

        /**
         * login or register a user
         */
        User user;
        while ((user = showLogin()) == null) ;

        /**
         * Game status
         * - "new" for a new game
         * - "restart" to restart the game after each round
         * - "reload" to load a saved user game
         */
        String status = "new";

        if (loadGameMenu(user))
            status = "load";

        /**
         * Starting,restarting and loading the game
         */
        Game game = null;
        do {
            // start a new game
            if ((status.compareTo("new") == 0)) {
                game = new Game(user);
            }
            // restart game
            else if (status.compareTo("restart") == 0) {
                game.resetGameObjects();

            }
            // load user's saved game
            else if (status.compareTo("load") == 0) {
                game = user.loadGame();
                game.pauseGame(true);
            }
            game.displaySettingsInUI();
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
