import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Character extends Sprite {

    private int SPEED = 5;
    private int dx;
    private int dy;
    private List<Missile> missiles;

    public Character(int x, int y) {
        super(x, y);
        initCharacter();
    }

    private void initCharacter(){
        missiles = new ArrayList<>();
        loadImage("src/resources/robo_small.png");
        getImageDimensions();
        x -= width/2;
        y -= height/2;
    }

    public void move() {
        if(x + width < 600 && x >= 0) x += dx;
        if(y + height < 800 && y >= 0) y += dy;

        x = min(max(x,0),599-width);
        y = min(max(y,0),799-height);
        //System.out.println("" + width + " " + height + " " + x + " " + y);
    }

    public void fire() {
        missiles.add(new Missile(x + width / 2, y));
    }

    public List<Missile> getMissiles(){
        return missiles;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)   { dx = -SPEED; }
        if (key == KeyEvent.VK_RIGHT)  { dx =  SPEED; }
        if (key == KeyEvent.VK_UP)     { dy = -SPEED; }
        if (key == KeyEvent.VK_DOWN)   { dy =  SPEED; }
        if (key == KeyEvent.VK_SPACE)  { fire();  }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT)   { dx = 0; }
        if (key == KeyEvent.VK_RIGHT)  { dx = 0; }
        if (key == KeyEvent.VK_UP)     { dy = 0; }
        if (key == KeyEvent.VK_DOWN)   { dy = 0; }
    }
}

