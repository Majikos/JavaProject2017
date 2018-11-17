package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuWindow extends Application {

    private Button start = new Button("Start");
    private Button highscores = new Button("High Scores");
    private Button help = new Button("Guide");
    private Button quit = new Button("Quit");
    private Image background = new Image("images\\menu.jpg");
    private ImageView iv = new ImageView(background);
    private boolean status = false;

    public MenuWindow(){}

    public MenuWindow(boolean status){
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
    public void start(Stage menuStage) {

        GridPane gp1 = new GridPane();
        gp1.add(start,2,0);
        gp1.add(highscores,2,1);
        gp1.add(help,2,2);
        gp1.add(quit,2,3);
        gp1.setVgap(50);
        gp1.setPadding(new Insets(170,0,0,340));


        StackPane sp1 = new StackPane();
        sp1.getChildren().add(iv);
        sp1.getChildren().add(gp1);

        Scene scene1 = new Scene(sp1,800,600);
        scene1.getStylesheets().add(this.getClass().getResource("myStyles.css").toExternalForm());


        start.setOnAction(e -> {
            Sound.menu.play();
            menuStage.hide();
            try {
                GameWindow.enemies.clear();
                GameWindow.eBullets.clear();
                GameWindow.bullets.clear();
                Player.keys.clear();
                new GameWindow(true);
                Sound.bgmp.play();
            }
            catch(IOException e1){
                System.out.println("shit dude.");
            }
        });


        highscores.setOnAction(e -> {
            Sound.menu.play();
            menuStage.hide();
            new HighscoreWindow(true);


        });

        help.setOnAction(e->{
            Sound.menu.play();
            menuStage.hide();
            new HelpWindow(true);
        });


        quit.setOnAction(e ->{
            Sound.menu.play();
            System.exit(0);
        });


        menuStage.setTitle("Fake Touhou v1");
        menuStage.setWidth(800);
        menuStage.setHeight(600);
        menuStage.setResizable(false);
        menuStage.setScene(scene1);
        menuStage.show();
    }
}
