import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import sun.security.util.Password;

/**
 * Created by Лиза on 20.03.2018.
 */
public class Window {
    public JButton signInButton;
    public JPanel panel1;
    private JTextField usernameEnter;
    private JButton getAmountOfMessagesButton;
    private JTextField numOfMessToRead;
    private JButton getConcreteMessageButton;
    private JTextField numOfDeleMessage;
    private JButton deleteMessageButton;
    private JButton exitButton;
    public JTextArea memo;
    private JPasswordField passwordEnter;
    private JButton clearButton;
    private JTextField firstF;
    private JButton getTopButton;
    private JTextField secondF;

    Pop3Client client = new Pop3Client();
    String text = "";

    public Window() {
        signInButton.addActionListener(e -> SignIn());
        getAmountOfMessagesButton.addActionListener(e -> AmountOfMessages());
        getConcreteMessageButton.addActionListener(e -> ConcreteMessage());
        clearButton.addActionListener(e -> Clear());
        deleteMessageButton.addActionListener(e -> DeleteMessage());
        exitButton.addActionListener(e -> Exit());
        getTopButton.addActionListener(e -> GetTop());
    }

    private void GetTop() {
        String first = firstF.getText();
        String second = secondF.getText();
        try {
            text += "ToClient: ";

            String text1 = "";
            for ( int i = 0; i < Integer.parseInt(second); i++) {
                text1 += client.getTop(first, second) + "\n";
            }
            text += text1;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        memo.setText(text);
        SignIn();
    }


    private void Exit() {
        try {
            text += "ToClient: " + client.quit() + "\n";
            client.disconnect();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        memo.setText(text);
    }

    private void DeleteMessage() {
        String del = numOfDeleMessage.getText();
        try {
            text += "ToClient: " + client.deleteMessage(del) + "\n";
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        memo.setText(text);
    }

    private void Clear() {
        new Window();
        memo.setText("");
        text = "";
    }

    private void ConcreteMessage() {
        String num = numOfMessToRead.getText();
        try {
            text += "ToClient: " + client.getTextOfMessage(Integer.parseInt(num)) + "\n";
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        memo.setText(text);
    }

    private void AmountOfMessages() {
        try {
            text += "ToClient: " + client.getNumberOfNewMessages() + "\n";
            memo.setText(text);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void SignIn() {
        String username = usernameEnter.getText();
        String password = String.valueOf(passwordEnter.getPassword());
        try {
            client.connect("pop.mail.ru");
            text += "ToClient: " + client.login(username, password) + "\n";
            memo.setText(text);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
