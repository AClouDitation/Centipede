import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Character {

    private int dx;
    private int dy;
    private int x = 40;
    private int y = 60;
    private int w;
    private int h;
    private Image image;

    public Character() {
        loadImage();
    }

    public void loadImage() {
        ImageIcon ii = new ImageIcon("src/resources/robo.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public Image getImage() {
        return image;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if      (key == KeyEvent.VK_LEFT)   { dx = -2; }
        else if (key == KeyEvent.VK_RIGHT)  { dx =  2; }
        else if (key == KeyEvent.VK_UP)     { dy = -2; }
        else if (key == KeyEvent.VK_DOWN)   { dy =  2; }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if      (key == KeyEvent.VK_LEFT)   { dx = 0; }
        else if (key == KeyEvent.VK_RIGHT)  { dx = 0; }
        else if (key == KeyEvent.VK_UP)     { dy = 0; }
        else if (key == KeyEvent.VK_DOWN)   { dy = 0; }
    }
}

