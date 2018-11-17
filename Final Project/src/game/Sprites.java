package game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public abstract class Sprites {

    private double angle,x,y,xVel,yVel;

    public Sprites(){}

    public Sprites(double x,double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public void setAngle(double angle){this.angle = angle;}

    public double getAngle(){return this.angle;}

    public abstract void tick();

    public abstract void render(GraphicsContext gc);

    public abstract Rectangle getBound();

}
