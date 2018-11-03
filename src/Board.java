import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel
    implements  ActionListener{

    private final int DELAY = 10;

    private Character character;
    private Timer timer;

    public Board () {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        addKeyListener(new TAdapter());
        System.out.println("KEY LISTENER ADDED");
        setBackground(Color.BLACK);

        character = new Character();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(character.getImage(),
                character.getX(), character.getY(), this);
    }

    private void step() {
        character.move();
        repaint(character.getX()-1, character.getY()-1,
                character.getWidth()+2, character.getHeight()+2);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("KEY RELEASED!");
            character.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("KEY PRESSED!");
            character.keyPressed(e);
        }
    }
}
