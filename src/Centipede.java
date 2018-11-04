import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Centipede {

    private int length;
    private int speed;
    private int cooldown;
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private Direction direction;
    private List<CentipedeNode> nodes;

    public Centipede(int length, int speed, int x, int y) {

        this.length = length;
        this.speed = speed;
        this.direction = Direction.LEFT;
        this.cooldown = 0;

        nodes = new ArrayList<>();
        for(int i=0;i < length;i++) {
           nodes.add(new CentipedeNode(x+40*i,y));
        }
    }

    private class CentipedeNode extends Sprite {

        int health;
        boolean isHead;
        public CentipedeNode(int x, int y) {
            super(x, y);
            initCentipedeNode();
        }

        public void initCentipedeNode(){
            loadImage("src/resources/robo_small.png");
            getImageDimensions();

            health = 2;
            isHead = false;
        }

        public void setLocaton(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public void move(){
        if(cooldown > 0) {
            cooldown -= speed;
            return;
        }
        cooldown = 100;
        CentipedeNode head = nodes.get(0);
        CentipedeNode tail = nodes.get(nodes.size()-1);
        int nextX = head.getX();
        int nextY = head.getY();
        if(direction == Direction.DOWN) nextY += 40;
        if(direction == Direction.LEFT) nextX -= 40;
        if(direction == Direction.RIGHT)nextX += 40;

        System.out.println(""+nextX+" "+nextY);
        tail.setLocaton(nextX, nextY);
        nodes.add(0,tail);
        nodes.remove(nodes.size()-1);
    }

    public void draw(Graphics2D g, JPanel observer) {
        for(CentipedeNode node: nodes) {
            g.drawImage(node.getImage(),node.getX(), node.getY(), observer);
        }
    }
}
