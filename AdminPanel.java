import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.NoSuchAlgorithmException;

public class AdminPanel extends MainWindow {


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 600);
    }

    JTextField userNameToReset;
    JButton resetPasswordButton;
    //JTextField provideNewPassword;
    JPasswordField provideNewPassword;
    JButton setNewPassword;
    JButton setRadioPassword;
    JComboBox dropDown;
    String[] passwordLen = {"8", "9", "10", "11", "12"};
    JButton setRandomPassword;

    JRadioButton lowerCaseButton;
    JRadioButton upperCaseButton;
    JRadioButton digitsButton;
    JRadioButton specialCharButton;

    String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String strOfDigits = "0123456789";
    String specialCharacters = "!@#$%&*()-_+";


    public AdminPanel() throws IOException, ClassNotFoundException {
        removeAll();
        revalidate();
        setLayout(new FlowLayout());
        textAndButton();
        setRadioListener();
        setNewPasswordListener();
        setRandomPasswordListener();
    }

    public void textAndButton() {
        userNameToReset = new JTextField("username");
        //userNameToReset.setBounds(50, 50, 200, 20);
        userNameToReset.setSize(200, 20);

        provideNewPassword = new JPasswordField();
        //provideNewPassword.setBounds(50, 100, 200, 20);
        provideNewPassword.setSize(200, 20);

        setNewPassword = new JButton("set new password");
        //setNewPassword.setBounds(270, 100, 200, 20);
        setNewPassword.setSize(200, 20);

        dropDown = new JComboBox(passwordLen);
        //dropDown.setBounds(50, 300, 70, 50);
        dropDown.setSize(70, 100);

        setRandomPassword = new JButton("set random password");
        //setRandomPassword.setBounds(270, 300, 200, 20);
        setRandomPassword.setSize(200, 20);


        setRadioPassword = new JButton("set selected password");
        setRadioPassword.setSize(250, 20);

        lowerCaseButton = new JRadioButton("only lowercase letters");
        upperCaseButton = new JRadioButton("only uppercase letters");
        digitsButton = new JRadioButton("only digits");
        specialCharButton = new JRadioButton("only special characters");

        ButtonGroup group = new ButtonGroup();
        group.add(lowerCaseButton);
        group.add(upperCaseButton);
        group.add(digitsButton);
        group.add(specialCharButton);

        JPanel radioPanel = new JPanel(new GridLayout(5, 1));
        radioPanel.add(lowerCaseButton);
        radioPanel.add(upperCaseButton);
        radioPanel.add(digitsButton);
        radioPanel.add(specialCharButton);
        radioPanel.add(setRadioPassword);
        add(radioPanel);

        add(userNameToReset);
        add(provideNewPassword);
        add(setNewPassword);
        add(dropDown);
        add(setRandomPassword);

    }

    public static String generateRandomPassword(String lenght) {
        StringBuilder generatedPassword = new StringBuilder();
        for (int i = 0; i < Integer.parseInt(lenght); i++) {
            generatedPassword.append(returnRandomChar());
        }
        return generatedPassword.toString();
    }

    public static char returnRandomChar() {
        int min = 33;
        int max = 122;
        return (char) (Math.random() * (max - min) + min);
    }

    public String randomPassFromGivenString(int passLenght, String input) {
        int range = input.length();
        String pass = "";

        for (int i = 0; i < passLenght; i++) {
            int id = (int) (Math.random()*range);
            pass += input.charAt(id);
        }
        return pass;
    }

    public void setRadioListener() {
        setRadioPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lowerCaseButton.isSelected()) {
                    String user = userNameToReset.getText();
                    String chosenSizeStr = (String) dropDown.getItemAt(dropDown.getSelectedIndex());
                    int chosenSize = Integer.parseInt(chosenSizeStr);
                    String randomPassword = randomPassFromGivenString(chosenSize, lowerCaseLetters);
                    System.out.println(user + " , " + randomPassword);
                    try {
                        serializeUser(user, randomPassword);
                    } catch (IOException | NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }

                if (upperCaseButton.isSelected()) {
                    String user = userNameToReset.getText();
                    String chosenSizeStr = (String) dropDown.getItemAt(dropDown.getSelectedIndex());
                    int chosenSize = Integer.parseInt(chosenSizeStr);
                    String randomPassword = randomPassFromGivenString(chosenSize, upperCaseLetters);
                    System.out.println(user + " , " + randomPassword);
                    try {
                        serializeUser(user, randomPassword);
                    } catch (IOException | NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }

                if (digitsButton.isSelected()) {
                    String user = userNameToReset.getText();
                    String chosenSizeStr = (String) dropDown.getItemAt(dropDown.getSelectedIndex());
                    int chosenSize = Integer.parseInt(chosenSizeStr);
                    String randomPassword = randomPassFromGivenString(chosenSize, strOfDigits);
                    System.out.println(user + " , " + randomPassword);
                    try {
                        serializeUser(user, randomPassword);
                    } catch (IOException | NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }

                if (specialCharButton.isSelected()) {
                    String user = userNameToReset.getText();
                    String chosenSizeStr = (String) dropDown.getItemAt(dropDown.getSelectedIndex());
                    int chosenSize = Integer.parseInt(chosenSizeStr);
                    String randomPassword = randomPassFromGivenString(chosenSize, specialCharacters);
                    System.out.println(user + " , " + randomPassword);
                    try {
                        serializeUser(user, randomPassword);
                    } catch (IOException | NoSuchAlgorithmException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }


    public void setNewPasswordListener() {
        setNewPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(provideNewPassword.getPassword());
                String user = userNameToReset.getText();

                try {
                    serializeUser(user, password);
                } catch (IOException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setRandomPasswordListener() {
        setRandomPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userNameToReset.getText();
                String chosenSize = (String) dropDown.getItemAt(dropDown.getSelectedIndex());
                String randomPassword = generateRandomPassword(chosenSize);
                System.out.println(user + " , " + randomPassword);

                try {
                    serializeUser(user, randomPassword);
                } catch (IOException | NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
