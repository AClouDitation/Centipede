import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Board extends JPanel
    implements  ActionListener{

    private final int ICRAFT_X = 300;
    private final int ICRAFT_Y = 400;
    private final int DELAY = 10;

    private Character character;
    private Timer timer;

    public Board () {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);

        character = new Character(ICRAFT_X, ICRAFT_Y);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(character.getImage(),
                character.getX(), character.getY(), this);

        List<Missile> missiles = character.getMissiles();
        for(Missile missile: missiles){
            g2d.drawImage(missile.getImage(),
                    missile.getX(), missile.getY(), this);
        }
    }

    private void updateCharacter() {
        character.move();
    }

    private void updateMissiles() {
        List<Missile> missiles = character.getMissiles();

        for (int i = 0;i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if(missile.isVisible()) { missile.move();}
            else {missiles.remove(i);}
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateCharacter();
        updateMissiles();
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            character.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            character.keyPressed(e);
        }
    }
}
