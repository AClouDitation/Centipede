import java.awt.*;

public class Spider extends Sprite {

    int dx, dy;
    int health;

    public Spider(int x, int y, int dx, int dy) {
        super(x,y);
        initSpider(dx, dy);
    }

    private void initSpider(int dx, int dy){
        loadImage("src/resources/spider_red.png");
        getImageDimensions();
        this.dx = dx;
        this.dy = dy;
        health = 2;
    }

    public void move() {
        if(x + dx + width > Main.FRAME_WIDTH || x + dx <= 0) {
            visible = false;
        }

        if(y + dy + width > Main.FRAME_HEIGHT || y + dy <= 0) {
            dy = -dy;
        }

        x += dx;
        y += dy;
    }

    public int checkIfHit(Rectangle rect) {
        if(rect.intersects(getBounds())) {
            System.out.println("Hit Spider!!!");
            health--;
            if(health == 0) {
                visible = false;
                return 600;
            }
            return 100;
        }
        return 0;
    }
}
