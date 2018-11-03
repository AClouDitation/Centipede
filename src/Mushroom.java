public class Mushroom extends Sprite {

    int life;
    public Mushroom(int x, int y){
        super(x,y);
        life = 3;
        initMushroom();
    }

    private void initMushroom() {
        loadImage("src/resources/mushroom_1.png");
        getImageDimensions();
        x -= width / 2;
        y -= height / 2;
    }

    public void hit() {
        life--;
        if(life == 2) loadImage("src/resources/mushroom_2.png");
        else if(life == 1) loadImage("src/resources/mushroom_3.png");
        else if(life == 0) visible = false;
    }

}
