import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TopBar extends JPanel {

    private int score = 0;

    private JLabel scoreBar;
    private JPanel lifeBar;
    private List<JLabel> lifeLabels;

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
        lifeBar = new JPanel();
        lifeBar.setBackground(Color.BLACK);
        lifeBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        lifeBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //liveBar.setBorder(new LineBorder(Color.BLUE));

        //add(scoreBar, Component.RIGHT_ALIGNMENT);
        add(lifeBar,BorderLayout.EAST);
        add(scoreBar,BorderLayout.WEST);
        addScore(0);

        // add three lives
        lifeLabels = new ArrayList<>();
        addLife();
        addLife();
        addLife();

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
        if(scoreGained>0) update(getGraphics());
    }

    public void addLife() {
        JLabel newLife = new JLabel();
        newLife.setIcon(new ImageIcon("src/resources/life.png"));
        lifeLabels.add(newLife);
        lifeBar.add(newLife);
        repaint();
    }

    public boolean removeLife() {
        if(lifeLabels.size() != 0) {
            lifeBar.remove(lifeLabels.get(lifeLabels.size() - 1));
            lifeLabels.remove(lifeLabels.size() - 1);
            repaint();
            return true;
        }
        else{
            // show you died..
            // restart?
            return false;
        }
    }
}
