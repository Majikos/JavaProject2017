package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelpWindow extends Application {
    Image[] images = new Image[8];
    ImageView[] iv = new ImageView[8];
    String[] src = {"images\\player.png","images\\bullet.png","images\\attackUP.png",
            "images\\scoreUP.png","images\\NormalEnemy.png","images\\SpecialEnemy.png",
            "images\\TrackingEnemy.png","images\\enemyBullet.png"};
    Label[] desc = new Label[8];
    String[] descString = {"Player (Move with WASD keys or arrow keys\nTurn with mouse LClick to fire)","Player bullet","Attack upgrade (Attack speed+)",
            "Score increase (+1000)","Normal enemy","Special enemy (2 HP)","Tracking enemy","Enemy Projectile"};
    ImageView bg = new ImageView(new Image("images\\picture.png"));
    private Button menu = new Button("Back to menu");
    private boolean status = false;
    public HelpWindow(){}

    public HelpWindow(boolean status){
        this.status = status;
        if(this.status){
            Stage stage = new Stage();
            start(stage);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage helpStage) {
        for(int i=0;i<images.length;i++) {
            images[i] = new Image(src[i]);
            iv[i] = new ImageView(images[i]);
            desc[i] = new Label(descString[i]);
        }
        GridPane gp = new GridPane();
        gp.setVgap(15);
        gp.setHgap(5);
        gp.add(new Label("Image"),0,0);
        gp.add(new Label("Description"),1,0);
        for(int i=0;i<images.length;i++) {
            gp.add(iv[i], 0, i+1);
            gp.add(desc[i],1,i+1);
        }
        gp.add(menu,0,9);

        menu.setOnAction(e->{
            Sound.menu.play();
            helpStage.hide();
            new MenuWindow(true);
        });
        StackPane sp = new StackPane();
        bg.setFitWidth(400);
        bg.setFitHeight(400);
        sp.getChildren().add(bg);
        sp.getChildren().add(gp);
        Scene hScene = new Scene(sp,600,480);
        hScene.getStylesheets().add(this.getClass().getResource("myStyles.css").toExternalForm());

        helpStage.setScene(hScene);
        helpStage.setTitle("Guide");
        helpStage.show();



    }
}
