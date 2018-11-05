import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Centipede {

    private int length;
    private int speed;
    private int coolDown;
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private Direction direction;
    //private LinkedList<CentipedeNode> nodes;
    private CentipedeNode head;

    public Centipede(int length, int speed, int x, int y) {

        this.length = length;
        this.speed = speed;
        this.direction = Direction.LEFT;
        this.coolDown = 0;

        head = new CentipedeNode(x,y);
        CentipedeNode now = head;
        for(int i=1;i < length;i++) {
           now.next = new CentipedeNode(x+40*i,y);
           now = now.next;
        }
    }

    public Centipede(CentipedeNode head, int speed, Direction direction) {
        this.head = head;
        this.speed = speed;
        this.direction = direction;
    }


    private class CentipedeNode extends Sprite {

        private int health;
        private boolean isHead;
        public CentipedeNode next;
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
            if(health == 1) loadImage("src/resources/robo_red_small.png");
            if(health == 0) {
                visible = false;
            }
        }
    }

    public boolean checkIfHit(Rectangle bound, List<Centipede> centipedes) {
        CentipedeNode prev = null;
        CentipedeNode now = head;
        while (now != null) {
            Rectangle nodeRect = now.getBounds();
            if (nodeRect.intersects(bound)) {
                now.hit();
                if (!now.isVisible()) {
                    //split the centipede
                    if (prev == null && now.next == null) centipedes.remove(this);
                    if (now.next != null) centipedes.add(new Centipede(now.next,speed,direction));
                    if (prev != null) prev.next = null;
                }
                return true;
            }
            prev = now;
            now = now.next;
        }
        return false;
    }

    public void move(List<Mushroom> mushrooms, int mushroomMapM, int mushroomMapN){

        boolean[][] hasMushroom = new boolean[mushroomMapM][mushroomMapN];

        for(Mushroom mushroom: mushrooms) {
            hasMushroom[mushroom.getY()/40][mushroom.getX()/40] = true;
        }

        if(coolDown > 0) {
            coolDown -= speed;
            return;
        }
        coolDown = 100;

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

        CentipedeNode now = head.next;
        int lastX = head.getX();
        int lastY = head.getY();
        while(now != null){
            int tempX = now.getX();
            int tempY = now.getY();
            now.setLocation(lastX,lastY);
            lastX = tempX;
            lastY = tempY;
            now = now.next;
        }
        head.setLocation(nextX,nextY);
    }

    public void draw(Graphics2D g, JPanel observer) {
        CentipedeNode now = head;
        while(now!=null) {
            g.drawImage(now.getImage(), now.getX(), now.getY(), observer);
            now=now.next;
        }
    }
}
