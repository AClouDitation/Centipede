public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390; //temp
    private final int MISSILE_SPEED = 10;

    public Missile(int x, int y) {
        super(x, y);
        initMissile();
    }

    private void initMissile() {
        loadImage("src/resources/missile_temp.png");
        getImageDimensions();
        x -= width/2;
        y -= height/2;
    }

    public void move() {
        y -= MISSILE_SPEED;
        if (y < 0) {
            visible = false;
        }
    }
}
