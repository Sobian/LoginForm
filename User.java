import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class User implements Serializable {

    String login;
    String password;
    ArrayList<Integer> encPass;


    public User(String login, String password) throws IOException {
        this.login = login;
        try {
            this.password = encrPassMD5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> encPassToArrList(String login) {
        ArrayList<Integer> tempArr = new ArrayList<>();
        int lenCounter = 0;
        for (int i = 0; i < this.password.length(); i++) {
            if (lenCounter < login.length()) {
                int tempVal = getPassword().charAt(i) + getLogin().charAt(lenCounter);
                tempArr.add(tempVal);
                lenCounter++;
            }
            if (lenCounter > login.length()) {
                lenCounter = 0;
                int tempVal = getPassword().charAt(i) + getLogin().charAt(lenCounter);
                tempArr.add(tempVal);
                lenCounter++;
            }
        }
        System.out.println(tempArr);
        return tempArr;
    }

    private String decrPassToStr(String login) {
        StringBuilder tempPass = new StringBuilder();
        int lenCounter = 0;
        for (int i = 0; i < this.password.length(); i++) {
            if (lenCounter < login.length()) {
                int tempVal = getEncPass().get(i) - encPass.get(i);
                char tmpChar = (char) tempVal;
                tempPass.append(tmpChar);
                lenCounter++;
            }
            if (lenCounter > login.length()) {
                lenCounter = 0;
                int tempVal = getEncPass().get(i) - encPass.get(i);
                char tmpChar = (char) tempVal;
                tempPass.append(tmpChar);
                lenCounter++;
            }
        }
        return tempPass.toString();
    }

    public String encrPassMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        System.out.println("encrypted password: " + hexString);

        return hexString.toString();
    }

    public static String encPassMD5_check(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, md.digest()).toString(16);
    }


    public String getLogin() {
        return login;
    }


    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getEncPass() {
        return encPass;
    }


}
