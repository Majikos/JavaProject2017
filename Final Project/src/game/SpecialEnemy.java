package game;


import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class SpecialEnemy extends Enemy {
    Image sEnemy = new Image("images\\SpecialEnemy.png");
    private int fireDelay = 20;
    private int health = 2;
    public SpecialEnemy (double x,double y){
        setX(x);
        setY(y);
        calcVel();
    }

    private void calcVel(){
            setxVel((1 * Wave.difficulty) * Math.cos(Math.toRadians(getAngle())));
            setyVel((1 * Wave.difficulty) * Math.sin(Math.toRadians(getAngle())));
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(sEnemy,getX(),getY());
    }

    @Override
    public void tick(){
        setAngle(Math.toDegrees(Math.atan2((GameWindow.player.getY()+20.0 - (this.getY()+16.0)),
                (GameWindow.player.getX()+20.0 - (this.getX() + 16.0)))));
        calcVel();

        setX(getX() + getxVel());
        setY(getY() + getyVel());

        fireDelay--;
        isFiring(fireDelay);

        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                collision();
            }
        });

    }

    protected EnemyProjectile shootProjectile(){
        return new EnemyProjectile(this.getX()+24.0,this.getY()+24.0,this.getAngle());
    }

    private void isFiring(int delay){
        if (delay < 0){
            GameWindow.eBullets.add(shootProjectile());
            fireDelay = 12;
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
                health--;
                GameWindow.bullets.remove(temp);
                if(health <= 0) {
                    Sound.eDead.play();
                    GUI.score += 200;
                    GameWindow.enemies.remove(this);
                }
            }
        }
    }
}
