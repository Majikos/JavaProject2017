package game;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public class EnemyProjectile extends Projectile{
    Image eBullet = new Image("images\\enemyBullet.png");
    public EnemyProjectile(double x,double y,double angle){
       super(x,y,angle);
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
                collision();
            }
        });
    }

    @Override
    public void render(GraphicsContext gc){
        if(getX() > -25 && getX() < 780 && getY() > -25 && getY() < 600)
            gc.drawImage(eBullet,getX(),getY());
    }

    private void calcVel(){
        setxVel(3 * Math.cos(Math.toRadians(getAngle())));
        setyVel(3 * Math.sin(Math.toRadians(getAngle())));
    }

    @Override
    public Rectangle getBound(){
        return new Rectangle(getX(),getY(),20,20);
    }

    private void collision(){
        for(int i=0;i<GameWindow.bullets.size();i++){
            Projectile temp = GameWindow.bullets.get(i);
            if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                GameWindow.eBullets.remove(this);
            }
        }
    }

    private void outOfBounds(){
        if(getX() < -50 || getX() > 800)
            GameWindow.eBullets.remove(this);
        else if(getY() < -50 || getY() > 600)
            GameWindow.eBullets.remove(this);
    }
}
