public class Mushroom extends Sprite {

    public Mushroom(int x, int y){
        super(x,y);
        initMushroom();
    }

    private void initMushroom() {
        loadImage("src/resources/mushroom.png");
        getImageDimensions();
        x -= width / 2;
        y -= height / 2;
    }


}
