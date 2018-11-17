package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;


public class GameWindow extends Application{
    protected static Player player = new Player();
    protected static AnimationTimer game;
    protected static boolean running = true;
    protected static LinkedList<Enemy> enemies = new LinkedList<>();
    protected static LinkedList<Projectile> bullets = new LinkedList<>();
    protected static LinkedList<EnemyProjectile> eBullets = new LinkedList<>();
    protected static LinkedList<Powerups> powerups = new LinkedList<>();
    protected static Stage gameStage = new Stage();
    private Image background = new Image("images\\background.png");
    private Group play = new Group();
    private Scene scene1 = new Scene(play);
    private boolean status = false;
    private GUI gui = new GUI();
    private Controls playerC = new Controls(scene1,player);


    public GameWindow(){}

    public GameWindow(boolean status) throws IOException{
        this.status = status;
        if(this.status){
            Stage stage = new Stage();
            start(stage);
            player.setxVel(0);
            player.setyVel(0);
            player.setX(340.0);
            player.setY(240.0);
            Sound.pFire.setVolume(0.3);
        }
        GUI.health = 150.0;
        GUI.score = 0;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage playField) throws IOException{
        Canvas playingField = new Canvas(800, 600);
        play.getChildren().add(playingField);
        GraphicsContext gc = playingField.getGraphicsContext2D();

        scene1.getStylesheets().add(this.getClass().getResource("myStyles.css").toExternalForm());
        playerC.setControls();


        game = new AnimationTimer(){
            @Override
            public void handle(long nanoTime){
                gc.drawImage(background, 0, 0);
                try {
                    tick();
                    if (GameWindow.enemies.isEmpty()) {
                      Wave.spawnWave();
                      Wave.waveNum++;
                    }
                    else if((GUI.score % 999) == 0){
                        Wave.spawnWave();
                        Wave.waveNum++;
                    }
                }catch(IOException e) {
                    System.out.println("oops");
                }
                render(gc);
            }
        };
        game.start();


        gameStage.setTitle("Fake Touhou v1");
        gameStage.setScene(scene1);
        gameStage.show();
    }


    private void tick() throws IOException {
        gui.tick();
        for(Enemy e : enemies)
            e.tick();
        for(EnemyProjectile eb : eBullets)
            eb.tick();
        for(Projectile b : bullets)
            b.tick();
        player.tick();

    }
    private void render(GraphicsContext gc){
        gui.render(gc);
        for(Powerups p : powerups)
            p.render(gc);
        for(Enemy e : enemies)
            e.render(gc);
        for(EnemyProjectile eb : eBullets)
            eb.render(gc);
        for(Projectile b : bullets)
            b.render(gc);
        player.render(gc);
    }

    public static double clamp(double position, double min, double max){
        if(position >= max){
            position = max;
        }
        else if (position <= min){
            position = min;
        }
        return position;
    }

    public static void stopGame(){
        game.stop();
    }

}
