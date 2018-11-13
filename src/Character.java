import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Character extends Sprite implements KeyListener {

    private int SPEED = 5;
    private List<Missile> missiles;
    private int missileCoolDown = 0;
    private boolean[] keys = new boolean[120];
    Clip shootSound;

    public Character(int x, int y) {
        super(x, y);
        initCharacter();
    }

    private void initCharacter(){
        missiles = new ArrayList<>();
        loadImage("src/resources/robo_small.png");
        try {
            AudioInputStream audioIn;
            audioIn = AudioSystem.getAudioInputStream(new File("src/resources/shootSound.wav"));
            shootSound = AudioSystem.getClip();
            shootSound.open(audioIn);
        }
        catch (Exception ex) {
            // should not happen
        }
        getImageDimensions();
        x -= width/2;
        y -= height/2;
    }

    public void move(List<Centipede> centipedes, Spider spider) {
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
        if(dx != 0 && x + dx + width < Main.FRAME_WIDTH && x + dx >= 0) {
            x += dx;
        }

        if(dy != 0 && y + dy + height < Main.FRAME_HEIGHT && y + dy >= 0) {
            y += dy;
        }

        for(Centipede centipede:centipedes){
            if(centipede.intersect(getBounds())){
                visible = false;
            }
        }

        if(spider != null && spider.getBounds().intersects(getBounds())) {
            visible = false;
        }
        //System.out.println("" + width + " " + height + " " + x + " " + y);
    }

    public void fire() {
        if (shootSound.isRunning()) shootSound.stop();
        shootSound.setMicrosecondPosition(0);
        shootSound.start();
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

    public void deleteMissiles() {
        missiles = new ArrayList<>();
    }

}

