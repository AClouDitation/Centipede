import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {

    private int score = 0;
    private int lives = 3;

    private JLabel scoreBar;
    private JPanel liveBar;

    public TopBar() {
        initTopBar();
    }

    private void initTopBar() {
        setBackground(Color.BLACK);

        setLayout(new GridLayout(1,5));
        scoreBar = new JLabel();
        scoreBar.setHorizontalAlignment(SwingConstants.RIGHT);
        // for layout debuggiong purpose
        // scoreBar.setBorder(new LineBorder(Color.RED));
        liveBar = new JPanel();
        liveBar.setBackground(Color.BLACK);
        //liveBar.setBorder(new LineBorder(Color.BLUE));

        //add(scoreBar, Component.RIGHT_ALIGNMENT);
        add(liveBar,BorderLayout.EAST);
        add(scoreBar,BorderLayout.WEST);
        addScore(0);
        repaint();
    }

    public void addScore(int scoreGained) {
        score += scoreGained;
        scoreBar.setText(String.format("<html>" +
                "                   <p color=\"#FFFFFF\">" +
                "                       <font size=\"6\">%06d</font>" +
                "                   </p>" +
                "              </html>",
                score));
        repaint();
    }
}
