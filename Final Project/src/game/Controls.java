package game;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by Richard on 3/24/17.
 */
public class Controls {
    Scene scene1;
    Player player;
    public double newAngle;

    public Controls(Scene scene, Player player){
        this.scene1 = scene;
        this.player = player;
    }

    public void setControls(){
        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                player.keyPressed(event);
            }
        });
        scene1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                player.keyPressed(event);
                player.keyReleased(event);
            }
        });

        scene1.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newAngle = (Math.atan2(((event.getY() - (player.getY()+ 24))),((event.getX() - (player.getX() + 24.0)))));
                player.setAngle((Math.toDegrees(newAngle)));

            }
        });

        scene1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.fireStatus = true;
                player.fireDelay -= 10;

            }
        });

        scene1.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.fireStatus = true;
                newAngle = (Math.atan2(((event.getY() - (player.getY()+ 24))),((event.getX() - (player.getX() + 24.0)))));
                player.setAngle((Math.toDegrees(newAngle)));

            }
        });

        scene1.setOnMouseReleased(e->{
            player.fireStatus = false;
            player.fireDelay += 10;
        });


    }
}
