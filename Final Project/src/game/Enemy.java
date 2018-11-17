package game;



import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;


public class Enemy extends Sprites{
    private int fireDelay = 30;
    Image enemy = new Image("images\\NormalEnemy.png");
    public Enemy(){
        setxVel(-1);
        setyVel(-1);
    }

    public Enemy (double x,double y, double xVel,double yVel){
        setX(x);
        setY(y);
        setxVel(xVel);
        setyVel(yVel);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(enemy,getX(),getY());
    }
    @Override
    public void tick(){
        setX(getX() + getxVel());
        setY(getY() + getyVel());

        setAngle(Math.toDegrees(Math.atan2((GameWindow.player.getY()+30.0 - (this.getY()+16.0)),(GameWindow.player.getX()+20.0 - (this.getX() + 16.0)))));
        fireDelay--;
        isFiring(fireDelay);

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                collision();
            }
        });

        if(getX() < 0 || getX() > 740)
            setxVel(-getxVel());
        if(getY() < 0 || getY() > 540)
            setyVel(-getyVel());
    }

    protected EnemyProjectile shootProjectile(){
        return new EnemyProjectile(this.getX()+24.0,this.getY()+24.0,this.getAngle());
    }

    private void isFiring(int delay){
        if (delay < 0){
            GameWindow.eBullets.add(shootProjectile());
            fireDelay = 100;
        }
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
                GUI.score += 50;
                GameWindow.enemies.remove(this);
            }
        }
    }

}
