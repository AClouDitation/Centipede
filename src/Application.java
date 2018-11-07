import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public static int FRAME_WIDTH = 960;
    public static int FRAME_HEIGHT = 720;
    public Application() {
        initUI();
    }

    private void initUI() {

        Board board = new Board();
        TopBar topBar = new TopBar();

        JSplitPane sl = new JSplitPane(SwingConstants.HORIZONTAL, topBar, board);
        sl.setDividerLocation(30);
        sl.setEnabled(false);
        sl.setDividerSize(0);
        add(sl);
        setSize(FRAME_WIDTH,FRAME_HEIGHT + Board.getMeshLength() + 40);
        setTitle("Centipede");
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
