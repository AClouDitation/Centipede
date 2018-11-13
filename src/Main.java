import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static int FRAME_WIDTH = 960;
    public static int FRAME_HEIGHT = 720;
    public Main() {
        initUI();
    }

    private void initUI() {

        TopBar topBar = new TopBar();
        Board board = new Board(topBar);

        JSplitPane sl = new JSplitPane(SwingConstants.HORIZONTAL, topBar, board);
        sl.setDividerLocation(30);
        sl.setEnabled(false);
        sl.setDividerSize(0);
        add(sl);
        setSize(FRAME_WIDTH,FRAME_HEIGHT + Board.getMeshLength() + 30);
        setTitle("Centipede");
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}
