import javax.swing.*;
import java.awt.*;

/**
 * Created by Лиза on 01.03.2018.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Pop3Client");
        frame.setContentPane(new Window().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
