package game;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;



public class Projectile extends Sprites{
    Image bullet = new Image("images\\bullet.png");
    public Projectile(double x,double y,double angle){
        this.setX(x);
        this.setY(y);
        this.setAngle(angle);
        calcVel();
    }

    @Override
    public void tick(){
        setX(getX() + getxVel());
        setY(getY() + getyVel());

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                outOfBounds();
            }
        });
    }

    @Override
    public void render(GraphicsContext gc){
        if(getX() > -50 && getX() < 800 || getY() > -50 && getY() < 600)
            gc.drawImage(bullet,getX(),getY());
    }

    private void calcVel(){
        setxVel(6 * Math.cos(Math.toRadians(getAngle())));
        setyVel(6 * Math.sin(Math.toRadians(getAngle())));
    }

    @Override
    public Rectangle getBound(){
        return new Rectangle(getX(),getY(),30,30);
    }

    private void outOfBounds(){
        if(getX() < -50 || getX() > 800)
            GameWindow.bullets.remove(this);
        else if(getY() < -50 || getY() > 600)
            GameWindow.bullets.remove(this);
    }
}
