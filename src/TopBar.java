import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {

    private ScoreBar scoreBar;
    public TopBar() {
        initTopBar();
    }

    private void initTopBar() {
        setLayout(null);
        setBackground(Color.BLACK);

        scoreBar = new ScoreBar(0,0);
        add(scoreBar);
    }
}
