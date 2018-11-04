import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public static int FRAME_WIDTH = 960;
    public static int FRAME_HEIGHT = 720;
    public Application() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setSize(FRAME_WIDTH,FRAME_HEIGHT + 30);
        setTitle("Application");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}
