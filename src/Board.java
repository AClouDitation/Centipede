import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements ActionListener{

    private final int ICRAFT_X = 480;
    private final int ICRAFT_Y = 680;
    private final int DELAY = 10;

    private Character character;
    private List<Mushroom> mushrooms;
    private Timer timer;

    public Board () {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);

        character = new Character(ICRAFT_X, ICRAFT_Y);
        mushrooms = new ArrayList<>();
        //mushrooms.add(new Mushroom(100,100));
        generateMushrooms();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateMushrooms() {
        int m = Application.FRAME_HEIGHT/40;
        int n = Application.FRAME_WIDTH/40;

        Random rand = new Random();
        boolean hasMushroom[][] = new boolean[m][n];

        for(int i = 1;i < m - 10;i++) {
            if(i%2 == 0){
                for(int j = n-2;j > 0;j--) {
                    if(hasMushroom[i-1][j+1]) continue;
                    if (rand.nextInt(100) >= 70) {
                        hasMushroom[i][j] = true;
                    }
                }
            }
            else{
                for(int j = 1;j < n-1;j++) {
                    if(hasMushroom[i-1][j-1]) continue;
                    if (rand.nextInt(100) >= 70) {
                        hasMushroom[i][j] = true;
                    }
                }
            }
        }

        for(int i = 0;i < m;i++){
            for(int j = 0;j < n;j++){
                if(hasMushroom[i][j]) mushrooms.add(new Mushroom(j * 40 + 20, i * 40 + 20));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for(Mushroom mushroom: mushrooms){
            g2d.drawImage(mushroom.getImage(),
                    mushroom.getX(), mushroom.getY(), this);
        }

        List<Missile> missiles = character.getMissiles();
        for(Missile missile: missiles){
            g2d.drawImage(missile.getImage(),
                    missile.getX(), missile.getY(), this);
        }

        g2d.drawImage(character.getImage(),
                character.getX(), character.getY(), this);
    }

    private void updateCharacter() {
        List<Rectangle> bounds = new ArrayList<>();
        for(Mushroom mushroom: mushrooms){
            bounds.add(mushroom.getBounds());
        }
        character.move(bounds);
    }

    private void updateMissiles() {
        List<Missile> missiles = character.getMissiles();

        for (int i = 0;i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            if(missile.isVisible()) {
                missile.move(mushrooms);
            }
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
