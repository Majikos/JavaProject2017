package game;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GUI {
    protected static int score = 0;
    public static double health = 150.0;

    public void tick() throws IOException{
        if(GameWindow.running) {
            score++;
        }
        if(health < 0) {
            GameWindow.stopGame();
            new ExitWindow(true);
            Sound.bgmp.stop();
        }
    }
    public void render(GraphicsContext gc){
        gc.setFill(Color.rgb(255,10,10));
        gc.fillRoundRect(20,20,150,20,15,15);
        gc.setFill(Color.rgb(10,255,10));
        gc.fillRoundRect(20,20,GameWindow.clamp(health,-1,150),20,15,15);
        gc.fillText("Health",20,15);
        gc.fillText("Wave: " + Wave.waveNum,660,20);
        gc.fillText("Score: " + score,720,20);
    }
}
