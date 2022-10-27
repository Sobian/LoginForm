import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


//todo: przenies na zserializowane obiekty User

public class MainWindow extends JPanel {


    final static String usersPATH = "/Users/marcinsobianowski/Documents/Documents – MacBook Pro (Marcin)/korki/formularzLogowaniaV2/Users/";


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    String loginInput = "";
    String passInput = "";
    String signUpInput = "";
    String signUpPasswordInput = "";
    String signPassRepeat = "";

    static JFrame adminFrame = null;
    final String admin = "admin";
    final String pswdAdmin = "admin";

    JLabel notification;
    JLabel passLabel;
    JLabel signUp;
    JLabel loginLabel;
    JLabel passwordNewLabel;
    JLabel repPass;
    JLabel termsy;
    JTextField signupField;
    JTextField mail;
    JTextField login;
    JButton acceptButton;
    JButton signUpButton;
    JPasswordField password;
    JPasswordField passwordNewUser;
    JPasswordField passwordRepeat;
    JCheckBox checkBox;

    public MainWindow() {
        setLayout(null);
        labels();
        textFields();
        passwordFields();
        buttons();
        logInButtonListener();
        signUpButtonListener();
        checkBox();
    }

    public boolean findUser(String filename) {
        return listFiles(usersPATH).contains(filename);
    }

    public Set<String> listFiles(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }


    public void serializeUser(String usr, String pwd) throws IOException, NoSuchAlgorithmException {
        FileOutputStream fos = new FileOutputStream(usersPATH + usr);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User u = new User(usr, pwd);
        oos.writeObject(u);
        oos.close();
        fos.close();
    }


    public static User deserializeUser(String usr) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        FileInputStream fis = new FileInputStream(usersPATH + usr);
        ObjectInputStream ois = new ObjectInputStream(fis);
        User tmpUser = (User) ois.readObject();
        fis.close();
        ois.close();
        return tmpUser;
    }

    public void checkPassword() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        System.out.println("password is correct:" + deserializeUser("user1").getPassword().equals(User.encPassMD5_check(passInput)));
    }

    public void labels() {
        notification = new JLabel();
        notification.setForeground(Color.red);
        notification.setBounds(105, 450, 400, 20);
        add(notification);
        loginLabel = new JLabel("login:");
        loginLabel.setBounds(105, 30, 50, 20);
        add(loginLabel);
        passLabel = new JLabel("password:");
        passLabel.setBounds(105, 80, 100, 20);
        add(passLabel);
        signUp = new JLabel("Sign up");
        signUp.setBounds(105, 150, 100, 20);
        add(signUp);
        signupField = new JTextField("login:");
        signupField.setBounds(100, 180, 300, 20);
        add(signupField);
        passwordNewLabel = new JLabel("password:");
        passwordNewLabel.setBounds(105, 260, 100, 20);
        add(passwordNewLabel);
        repPass = new JLabel("repeat password:");
        repPass.setBounds(105, 300, 150, 20);
        add(repPass);
        termsy = new JLabel("I agree with terms and conditions of the service.");
        termsy.setBounds(50, 400, 400, 20);
        add(termsy);
    }

    public void textFields() {
        login = new JTextField();
        login.setBounds(100, 50, 300, 20);
        add(login);
        mail = new JTextField("e-mail:");
        mail.setBounds(100, 220, 300, 20);
        add(mail);
    }

    public void passwordFields() {
        password = new JPasswordField("");
        password.setBounds(100, 100, 300, 20);
        add(password);
        passwordNewUser = new JPasswordField("");
        passwordNewUser.setBounds(100, 280, 300, 20);
        add(passwordNewUser);
        passwordRepeat = new JPasswordField("");
        passwordRepeat.setBounds(100, 320, 300, 20);
        add(passwordRepeat);

    }

    public void buttons() {
        acceptButton = new JButton("Sign in");
        acceptButton.setBounds(200, 125, 100, 20);
        add(acceptButton);

        signUpButton = new JButton("Sign up");
        signUpButton.setBounds(200, 345, 100, 20);
        add(signUpButton);
    }

    public void checkBox() {
        checkBox = new JCheckBox();
        checkBox.setBounds(20, 400, 300, 20);
        add(checkBox);
    }

    public void logInButtonListener() {
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginInput = login.getText();
                passInput = new String(password.getPassword());


                try {
                    if (findUser(loginInput)) {
                        System.out.println("OK");
                        checkPassword();
                        notification.setText("OK");
                    }
                    if (loginInput.equals(admin) && passInput.equals(pswdAdmin)) {
                        admin();

                    } else if (!loginInput.equals(admin) && !passInput.equals(pswdAdmin) &&
                            findUser(loginInput)) {

                        user();
                    } else {
                        System.out.println("nieprawidlowe dane");
                        notification.setText("nieprawidlowe dane");
                    }
                } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void signUpButtonListener() {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                signUpInput = signupField.getText();
                signUpPasswordInput = new String(passwordNewUser.getPassword());
                signPassRepeat = new String(passwordRepeat.getPassword());
                try {
                    if (!findUser(signUpInput) && signUpPasswordInput.equals(signPassRepeat) && checkBox.isSelected()) {
                        try {
                            serializeUser(signUpInput, signUpPasswordInput);
                        } catch (NoSuchAlgorithmException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("istnieje juz takie konto");
                        notification.setText("istnieje juz takie konto");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public void admin() throws IOException, ClassNotFoundException {
        adminFrame = new JFrame();
        AdminPanel root = new AdminPanel();
        adminFrame.dispatchEvent(new WindowEvent(adminFrame, WindowEvent.WINDOW_CLOSING));
        adminFrame.add(root);
        adminFrame.setVisible(true);
        adminFrame.pack();
    }

    public void user() throws IOException {
        JFrame frame = new JFrame();
        MainWindow okno = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(okno);
        frame.setVisible(true);
        frame.pack();
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        MainWindow okno = new MainWindow();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(okno);
        frame.setVisible(true);
        frame.pack();

    }
}
