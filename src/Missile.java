import java.awt.*;
import java.util.List;

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

    public void move(List<Mushroom> mushrooms, List<Centipede> centipedes) {
        y -= MISSILE_SPEED;
        if (y < 0) {
            visible = false;
            return;

        }
        for(int i = 0;i < mushrooms.size(); i++) {
            Mushroom mushroom = mushrooms.get(i);
            Rectangle mushroomBound = mushroom.getBounds();
            Rectangle missileBound = getBounds();

            if(missileBound.intersects(mushroomBound)) {
               mushroom.hit();
               if(!mushroom.isVisible()){
                    mushrooms.remove(i);
               }
               visible = false;
               break;
            }
        }


        for(Centipede centipede:centipedes){
            if(centipede.checkIfHit(getBounds())){
                visible = false;
                break;
            }
        }
    }
}
