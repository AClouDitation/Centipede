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

    private static final int MESH_LENGTH = 30;
    public static int getMeshLength() { return MESH_LENGTH; }

    private final int ICRAFT_X = 480;
    private final int ICRAFT_Y = 680;
    private final int DELAY = 10;

    private Character character;
    private List<Centipede> centipedes;
    private List<Mushroom> mushrooms;
    private TopBar topBar;
    private Timer timer;

    private int mushroomMapM;
    private int mushroomMapN;

    public Board (TopBar topBar) {
        this.topBar = topBar;
        initBoard();
    }

    private void initBoard() {

        setFocusable(true);
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);

        character = new Character(ICRAFT_X, ICRAFT_Y);
        mushrooms = new ArrayList<>();
        centipedes = new ArrayList<>();
        centipedes.add(new Centipede(10, 100, Application.FRAME_WIDTH - 360,0));
        generateMushrooms();


        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void generateMushrooms() {
        boolean hasMushroom[][];
        mushroomMapM = Application.FRAME_HEIGHT/MESH_LENGTH;
        mushroomMapN = Application.FRAME_WIDTH/MESH_LENGTH;
        hasMushroom = new boolean[mushroomMapM][mushroomMapN];
        System.out.println(""+mushroomMapM+" "+mushroomMapN);

        Random rand = new Random();

        for(int i = 1;i < mushroomMapM-8;i++) {
            for(int j = 1;j < mushroomMapN-1;j++) {
                if(hasMushroom[i-1][j-1] || hasMushroom[i-1][j+1]) continue;
                if (rand.nextInt(100) >= 70) {
                    hasMushroom[i][j] = true;
                }
            }
        }

        for(int i = 0;i < mushroomMapM;i++){
            for(int j = 0;j < mushroomMapN;j++){
                if(hasMushroom[i][j]) mushrooms.add(new Mushroom(j*MESH_LENGTH,i*MESH_LENGTH));
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

        for(Centipede centipede:centipedes) {
            centipede.draw(g2d, this);
        }

        g2d.drawImage(character.getImage(),
                character.getX(), character.getY(), this);
    }

    private void updateCharacter() {
        character.move(centipedes);
        if(!character.isVisible()){
            System.out.println("you died");
            timer.stop(); // for now
        }
    }

    private void updateMissiles() {
        List<Missile> missiles = character.getMissiles();

        for (int i = 0;i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            int scoreGained = missile.move(mushrooms, centipedes);
            topBar.addScore(scoreGained);
            if(!missile.isVisible()) missiles.remove(i);
        }

    }

    private void updateCentipedes() {
        for(Centipede centipede: centipedes){
            centipede.move(mushrooms,mushroomMapM,mushroomMapN);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateCharacter();
        updateMissiles();
        updateCentipedes();
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
