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

    public int move(List<Mushroom> mushrooms, List<Centipede> centipedes, Spider spider) {

        y -= MISSILE_SPEED;
        if (y < 0) {
            visible = false;
            return 0;
        }

        for(int i = 0;i < mushrooms.size(); i++) {
            Mushroom mushroom = mushrooms.get(i);
            Rectangle mushroomBound = mushroom.getBounds();
            Rectangle missileBound = getBounds();

            if(missileBound.intersects(mushroomBound)) {
               mushroom.hit();
               visible = false;
               if(!mushroom.isVisible()) {
                   mushrooms.remove(i);
                   return 5;
               }
               return 1;
            }
        }

        for(Centipede centipede:centipedes){
            int scoreGained = centipede.checkIfHit(getBounds(), centipedes);
            if(scoreGained > 0) {
                visible = false;
                return scoreGained;
            }
        }

        if(spider != null) {
            System.out.println("checking if hit spider");
            int scoreGained = spider.checkIfHit(getBounds());
            if (scoreGained > 0) {
                visible = false;
            }
            return scoreGained;
        }

        return 0;
    }
}
