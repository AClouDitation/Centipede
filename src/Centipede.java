import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Centipede {

    private int length;
    private int speed;
    private int coolDown;
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private Direction direction;
    private List<CentipedeNode> nodes;

    public Centipede(int length, int speed, int x, int y) {

        this.length = length;
        this.speed = speed;
        this.direction = Direction.LEFT;
        this.coolDown = 0;

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

        public void initCentipedeNode() {
            loadImage("src/resources/robo_small.png");
            getImageDimensions();

            health = 2;
            isHead = false;
        }

        public void setLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void hit() {
            health--;
            if(health == 0) {
                visible = false;
            }
            //split the centipede
        }
    }

    public boolean checkIfHit(Rectangle bound) {
        for(int i = 0;i < nodes.size();i++) {
            CentipedeNode node = nodes.get(i);
            Rectangle nodeRect = node.getBounds();
            if(nodeRect.intersects(bound)){
                node.hit();
                if(!node.isVisible()){
                    nodes.remove(i);
                }
                return true;
            }
        }
        return false;
    }

    public void move(boolean[][] hasMushroom){
        if(coolDown > 0) {
            coolDown -= speed;
            return;
        }
        coolDown = 100;
        CentipedeNode head = nodes.get(0);
        CentipedeNode tail = nodes.get(nodes.size()-1);
        int nextX = head.getX();
        int nextY = head.getY();
        System.out.println(""+nextY/40+" "+nextX/40);
        if(direction == Direction.LEFT){
            if(nextX/40-1 < 0 ||
                    hasMushroom[nextY/40][nextX/40-1]) {
                nextY += 40;
                direction = Direction.RIGHT;
            }
            else nextX -= 40;
        }
        else if(direction == Direction.RIGHT){
            if(nextX/40+1 >= Application.FRAME_WIDTH/40 ||
                    hasMushroom[nextY/40][nextX/40+1]) {
                nextY += 40;
                direction = Direction.LEFT;
            }
            else nextX += 40;
        }

        tail.setLocation(nextX, nextY);
        nodes.add(0,tail);
        nodes.remove(nodes.size()-1);
    }

    public void draw(Graphics2D g, JPanel observer) {
        for(CentipedeNode node: nodes) {
            g.drawImage(node.getImage(),node.getX(), node.getY(), observer);
        }
    }
}
