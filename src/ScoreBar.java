import javax.swing.*;
import java.awt.*;

public class ScoreBar extends JLabel {

    public ScoreBar(int x, int y) {
        setText("<html><p color=\"#FFFFFF\"><font size=\"6\">000000</font></p></html>");
        setLocation(x,y);
        Dimension size = getPreferredSize();
        setBounds(x,y,size.width,size.height);
    }

    public void updateScore(int score) {
        setText(String.format("<html><p color=\"#FFFFFF\">font size=\"6\">%06d</font></p></html>",score));
    }
}
