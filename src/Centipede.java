import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Centipede {

    private int speed;
    private int coolDown;

    public enum Direction {
        UP(-1), DOWN(1), LEFT(-1), RIGHT(1);
        private int numVal;

        Direction(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }
    }

    private Direction direction;
    private CentipedeNode head;

    public Centipede(int length, int speed, int x, int y) {

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
        head.direction = direction;
        head.setHead();
        this.head = head;
        this.speed = speed;
    }


    private class CentipedeNode extends Sprite {

        private int health;
        private boolean isHead;
        private Direction direction;
        private Direction nextDirection;

        private int dspeed = 5;
        private int downcount;
        private LinkedList<Direction> lastDirections;

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
            lastDirections = new LinkedList<>();
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
                if (this.direction == Direction.UP)    loadImage("src/resources/centipede_head_up.png");
                if (this.direction == Direction.DOWN)  loadImage("src/resources/centipede_head_down.png");
                if (this.direction == Direction.LEFT)  loadImage("src/resources/centipede_head_left.png");
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

        private void move(Direction dir) {

            int nextX = x;
            int nextY = y;
            if(dir == Direction.LEFT || dir == Direction.RIGHT) nextX += dir.getNumVal() * dspeed;
            if(dir == Direction.UP   || dir == Direction.DOWN)  nextY += dir.getNumVal() * dspeed;
            nextY %= Application.FRAME_HEIGHT;
            setDirection(dir);
            setLocation(nextX,nextY);

            Direction nextDir = Direction.LEFT;
            if(lastDirections.size() >= 6) nextDir = lastDirections.pop();
            if(next != null) next.move(nextDir);
            lastDirections.add(dir);
        }

        public void moveHead(boolean[][] hasMushroom) {

            int nextX = x;
            int nextY = y;
            if(direction == Direction.LEFT || direction == Direction.RIGHT) {
                nextX += direction.getNumVal() * dspeed;

                if(nextX < 0 || nextX+width >= Application.FRAME_WIDTH ||
                        hasMushroom[nextY/Board.getMeshLength()]
                                [nextX/Board.getMeshLength() + (direction == Direction.RIGHT?1:0)]) {

                    nextDirection = direction == Direction.LEFT?Direction.RIGHT:Direction.LEFT;
                    direction = Direction.DOWN;
                    downcount = 5;
                }
                move(direction);
            }
            else {
                move(direction);
                downcount--;
                if(downcount == 0) {
                    direction = nextDirection;
                }
            }

        }
    }

    // return the point gained
    // return 0 if not hit
    public int checkIfHit(Rectangle bound, List<Centipede> centipedes) {
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
                    return 5;
                }
                return 2;
            }
            prev = now;
            now = now.next;
        }
        return 0;
    }

    public void move(List<Mushroom> mushrooms, int mushroomMapM, int mushroomMapN){

        if(coolDown > 0) {
            coolDown -= speed;
            return;
        }
        coolDown = 10;


        boolean[][] hasMushroom = new boolean[mushroomMapM][mushroomMapN];

        for(Mushroom mushroom: mushrooms) {
            hasMushroom[mushroom.getY()/Board.getMeshLength()][mushroom.getX()/Board.getMeshLength()] = true;
        }

        head.moveHead(hasMushroom);
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
