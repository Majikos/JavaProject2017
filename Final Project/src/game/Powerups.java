package game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Powerups extends Sprites{
    private double type;
    Image atk = new Image("images\\attackUP.png");
    Image score = new Image("images\\scoreUP.png");
    Image current;

    public Powerups(double type,double x, double y){
        this.type = type;
        setImage(type);
        setX(x);
        setY(y);
    }

    private void setImage(double type){
        if(type > 0)
            current = atk;
        else if(type <= 0)
            current = score;
        else
            current = score;
    }

    public double getType(){
        return type;
    }
    @Override
    public void tick() {
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(current,getX(),getY());
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle(getX(),getY(),24,24);
    }
}
