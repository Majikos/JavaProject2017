package game;



import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;



import java.util.HashSet;


public class Player extends Sprites {
    protected static HashSet<String> keys = new HashSet<>();
    private Image player = new Image("images\\player.png");
    private  ImageView playerIV = new ImageView(player);

    protected static boolean fireStatus = false;
    protected static int fireDelay = 0;
    protected static int powerupTime = 0;

    public Player(){
        setX(340.0);
        setY(240.0);
        setAngle(0.0);
    }

    @Override
    public void tick(){
        setX(getX() + getxVel());
        setY(getY() + getyVel());
        if (powerupTime >=0)
            powerupTime--;
        if(fireStatus && powerupTime <= 0)
            fireDelay--;
        else if(fireStatus && powerupTime > 0)
            fireDelay -= 5;
        isFiring(fireStatus, fireDelay);
        collision();

        setX(GameWindow.clamp(getX(),-22.0,750.0));
        setY(GameWindow.clamp(getY(),-32.0,542.0));
    }

    @Override
    public void render(GraphicsContext gc){

        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);
        playerIV.setRotate(getAngle());
        Image rotatedPlayer = playerIV.snapshot(param,null);
        gc.drawImage(rotatedPlayer,getX(),getY());



    }

    public void keyPressed(KeyEvent event){
        String key = event.getCode().toString();
        keys.add(key);
        if (keys.contains("W") || keys.contains("UP"))
            setyVel(-5);
        if (keys.contains("A") || keys.contains("LEFT"))
            setxVel(-5);
        if (keys.contains("S") || keys.contains("DOWN"))
            setyVel(5);
        if (keys.contains("D") || keys.contains("RIGHT"))
            setxVel(5);
    }

    public void keyReleased(KeyEvent event){
        String key = event.getCode().toString();
        keys.remove(key);
        if ((keys.contains("W") || keys.contains("UP")) && (!keys.contains("S") || !keys.contains("DOWN")))
            setyVel(-5);
        else if ((keys.contains("S") || keys.contains("DOWN")) && (!keys.contains("W") || !keys.contains("UP")))
            setyVel(5);
        else
            setyVel(0);
        if ((keys.contains("A") || keys.contains("LEFT")) && (!keys.contains("D") || !keys.contains("RIGHT")))
            setxVel(-5);
        else if ((keys.contains("D") || keys.contains("RIGHT")) && (!keys.contains("A") || !keys.contains("LEFT")))
            setxVel(5);
        else
            setxVel(0);
    }

    @Override
    public Rectangle getBound(){
        return new Rectangle(getX()+20.0,getY()+30.0,30.0,30.0);
    }

    //god save me
    public Projectile shootProjectile() {
        if(getAngle() > -25 && getAngle() <= 0)
            return new Projectile(this.getX()+68,this.getY(),getAngle());
        else if(getAngle() > -50 && getAngle() <= -25)
            return new Projectile(this.getX()+65,this.getY(),getAngle());
        else if(getAngle() > -65 && getAngle() <= -50)
            return new Projectile(this.getX()+45,this.getY(),getAngle());
        else if(getAngle() > -80 && getAngle() <= -65)
            return new Projectile(this.getX()+30,this.getY(),getAngle());
        else if(getAngle() > -90 && getAngle() <= -80)
            return new Projectile(this.getX()+10,this.getY(),getAngle());
        else if (getAngle() > -145 && getAngle() <= -90)
            return new Projectile(this.getX(),this.getY(),getAngle());
        else if (getAngle() >= -180 && getAngle() <= -145)
            return new Projectile(this.getX()-10,this.getY(),getAngle());
        else if(getAngle() <= 180 && getAngle() >= 170)
            return new Projectile(this.getX()-10,this.getY()+15,getAngle());
        else if(getAngle() < 170 && getAngle() >= 155)
            return new Projectile(this.getX()-10,this.getY()+30,getAngle());
        else if (getAngle() < 155 && getAngle() >= 145)
            return new Projectile(this.getX()-10,this.getY()+50,getAngle());
        else if(getAngle() < 145 && getAngle() >= 135)
            return new Projectile(this.getX()-10,this.getY()+60,getAngle());
        else if(getAngle() < 135 && getAngle() >= 110)
            return new Projectile(this.getX(),this.getY()+70,getAngle());
        else if(getAngle() < 110 && getAngle() >= 90)
            return new Projectile(this.getX()+5,this.getY()+80,getAngle());
        else if(getAngle() < 90 && getAngle() >= 75)
            return new Projectile(this.getX()+20,this.getY()+80,getAngle());
        else if(getAngle() < 75 && getAngle() >= 60)
            return new Projectile(this.getX()+45,this.getY()+80,getAngle());
        else if(getAngle() < 60 && getAngle() >= 50)
            return new Projectile(this.getX()+60,this.getY()+80,getAngle());
        else if(getAngle() < 50 && getAngle() >= 40)
            return new Projectile(this.getX()+75,this.getY()+75,getAngle());
        else if(getAngle() < 40 && getAngle() >= 30)
            return new Projectile(this.getX()+75,this.getY()+60,getAngle());
        else if(getAngle() < 30 && getAngle() >= 20)
            return new Projectile(this.getX()+80,this.getY()+45,getAngle());
        else if(getAngle() < 20 && getAngle() >= 10)
            return new Projectile(this.getX()+80,this.getY()+30,getAngle());
        else
            return new Projectile(this.getX()+80,this.getY()+15,getAngle());


    }

    public void isFiring(boolean status,int delay){
        if(status && delay < 0){
            GameWindow.bullets.add(shootProjectile());
            Sound.pFire.play();
            fireDelay = 25;

        }
    }

    private void collision(){
        for(int i=0;i<GameWindow.enemies.size();i++){
            Enemy temp = GameWindow.enemies.get(i);
            if(temp instanceof SpecialEnemy){
                if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                    Sound.eHit.play();
                    GUI.health -= 25.1;
                    GameWindow.enemies.remove(temp);
                }
            }
            else if(temp instanceof TrackingEnemy){
                if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                    Sound.eHit.play();
                    GUI.health -= 15.1;
                    GameWindow.enemies.remove(temp);
                }
            }
            else if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                Sound.eHit.play();
                GUI.health -= 10.1;
                GameWindow.enemies.remove(temp);
            }
        }
        for(int i=0;i<GameWindow.eBullets.size();i++){
            EnemyProjectile temp = GameWindow.eBullets.get(i);
            if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                Sound.pHit.play();
                GUI.health -= 3;
                GameWindow.eBullets.remove(temp);
            }
        }
        for(int i=0;i<GameWindow.powerups.size();i++){
            Powerups temp = GameWindow.powerups.get(i);
            if(getBound().getBoundsInParent().intersects(temp.getBound().getBoundsInParent())){
                if(temp.getType() > 0) {
                    Sound.powerup.play();
                    powerupTime = 750;
                }
                else if(temp.getType() <= 0){
                    Sound.powerup.play();
                    GUI.score += 1000;
                }
                GameWindow.powerups.remove(temp);
            }
        }
    }
}
