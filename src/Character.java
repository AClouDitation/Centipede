import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Character extends Sprite implements KeyListener {

    private int SPEED = 5;
    private List<Missile> missiles;
    private int missileCoolDown = 0;
    private boolean[] keys = new boolean[120];

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
        int dx=0, dy=0;
        if(keys[KeyEvent.VK_LEFT]) dx -= SPEED;
        if(keys[KeyEvent.VK_RIGHT])dx += SPEED;
        if(keys[KeyEvent.VK_UP])   dy -= SPEED;
        if(keys[KeyEvent.VK_DOWN]) dy += SPEED;
        if(keys[KeyEvent.VK_SPACE]) {
            if(missileCoolDown <= 0){
                missileCoolDown = 15;
                fire();
            }
        }

        if(missileCoolDown > 0) missileCoolDown -= 1;
        if(dx != 0 && x + dx + width < Application.FRAME_WIDTH && x + dx >= 0) {
            x += dx;
        }

        if(dy != 0 && y + dy + height < Application.FRAME_HEIGHT && y + dy >= 0) {
            y += dy;
        }

        //System.out.println("" + width + " " + height + " " + x + " " + y);
    }

    public void fire() {
        missiles.add(new Missile(x + width / 2, y));
    }

    public List<Missile> getMissiles(){
        return missiles;
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {}

}

