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
    private CentipedeNode head;

    public Centipede(int length, int speed, int x, int y) {

        this.length = length;
        this.speed = speed;
        this.direction = Direction.LEFT;
        this.coolDown = 0;

        head = new CentipedeNode(x,y);
        head.setHead();
        CentipedeNode now = head;
        for(int i=1;i < length;i++) {
           now.next = new CentipedeNode(x+30*i,y);
           now = now.next;
        }
    }

    public Centipede(CentipedeNode head, int speed, Direction direction) {
        this.head = head;
        this.head.setHead();
        this.speed = speed;
        this.direction = direction;
    }


    private class CentipedeNode extends Sprite {

        private int health;
        private boolean isHead;
        private Direction direction;
        public CentipedeNode next;
        public CentipedeNode(int x, int y) {
            super(x, y);
            initCentipedeNode();
        }

        public void initCentipedeNode() {
            loadImage("src/resources/centipede_temp.png");
            getImageDimensions();
            health = 2;
            isHead = false;
            setDirection(Direction.LEFT);
        }

        public void setLocation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Direction getDirection() {return direction;}

        public void setDirection(Direction direction) {
            this.direction = direction;
            if (isHead) {
                if (this.direction == Direction.UP) loadImage("src/resources/centipede_head_up.png");
                if (this.direction == Direction.DOWN) loadImage("src/resources/centipede_head_down.png");
                if (this.direction == Direction.LEFT) loadImage("src/resources/centipede_head_left.png");
                if (this.direction == Direction.RIGHT) loadImage("src/resources/centipede_head_right.png");
            }
        }

        public void hit() {
            health--;
            if(health == 0) {
                visible = false;
            }
        }

        public void setHead() {isHead = true;}
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
                    if (prev == null && now.next == null) centipedes.remove(this);  // single node centipede
                    else if (prev == null) {                                           // attacking head
                        head = head.next;
                        head.setHead();
                    }
                    else if (now.next == null) prev.next = null;                       // attacking tail
                    else {                                                             // attacking middle, split centipede
                        Direction next_direction = direction == Direction.RIGHT ? Direction.LEFT : Direction.RIGHT;
                        centipedes.add(new Centipede(now.next, speed, next_direction));
                        prev.next = null;
                    }
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
            hasMushroom[mushroom.getY()/Board.getMeshLength()][mushroom.getX()/Board.getMeshLength()] = true;
        }

        if(coolDown > 0) {
            coolDown -= speed;
            return;
        }
        coolDown = 100;

        int nextX = head.getX();
        int nextY = head.getY();
        if(direction == Direction.LEFT){
            if(nextX/Board.getMeshLength()-1 < 0 ||
                    hasMushroom[nextY/Board.getMeshLength()][nextX/Board.getMeshLength()-1]) {
                nextY += Board.getMeshLength();
                nextY %= Application.FRAME_HEIGHT;
                direction = Direction.RIGHT;
            }
            else nextX -= Board.getMeshLength();
        }
        else if(direction == Direction.RIGHT){
            if(nextX/Board.getMeshLength()+1 >= Application.FRAME_WIDTH/Board.getMeshLength() ||
                    hasMushroom[nextY/Board.getMeshLength()][nextX/Board.getMeshLength()+1]) {
                nextY += Board.getMeshLength();
                nextY %= Application.FRAME_HEIGHT;
                direction = Direction.LEFT;
            }
            else nextX += Board.getMeshLength();
        }

        CentipedeNode now = head.next;
        int lastX = head.getX();
        int lastY = head.getY();
        Direction lastDir = head.getDirection();
        while(now != null){
            int tempX = now.getX();
            int tempY = now.getY();
            Direction tempDir = now.getDirection();
            now.setLocation(lastX,lastY);
            now.setDirection(lastDir);
            lastX = tempX;
            lastY = tempY;
            lastDir = tempDir;
            now = now.next;
        }
        head.setLocation(nextX,nextY);
        head.setDirection(direction);
    }

    public void draw(Graphics2D g, JPanel observer) {
        CentipedeNode now = head;
        while(now!=null) {
            g.drawImage(now.getImage(), now.getX(), now.getY(), observer);
            now=now.next;
        }
    }

    public boolean intersect(Rectangle rect) {
        CentipedeNode now = head;
        while(now != null) {
            if(now.getBounds().intersects(rect)) return true;
            now = now.next;
        }
        return false;
    }
}
