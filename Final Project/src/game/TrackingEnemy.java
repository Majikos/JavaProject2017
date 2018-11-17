package game;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public class TrackingEnemy extends Enemy {
    public TrackingEnemy(double x,double y){
        setX(x);
        setY(y);
    }
    Image tEnemy = new Image("images\\TrackingEnemy.png");

    @Override
    public void tick() {
        setAngle(Math.toDegrees(Math.atan2((GameWindow.player.getY() + 20.0 - (this.getY() + 16.0)),
                (GameWindow.player.getX() + 20.0 - (this.getX() + 16.0)))));
        calcVel();

        setX(getX() + getxVel());
        setY(getY() + getyVel());

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                collision();
            }
        });
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(tEnemy,getX(),getY());
    }

    private void calcVel(){
        setxVel((2.5 * Wave.difficulty) * Math.cos(Math.toRadians(getAngle())));
        setyVel((2.5 * Wave.difficulty) * Math.sin(Math.toRadians(getAngle())));
    }

    @Override
    public Rectangle getBound(){
        return new Rectangle(getX(),getY(),46,46);
    }

    private void collision(){
        for(int i=0;i<GameWindow.bullets.size();i++){
            Projectile temp = GameWindow.bullets.get(i);
            if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                Sound.eDead.play();
                GUI.score += 150;
                GameWindow.enemies.remove(this);
            }
        }
    }
}
