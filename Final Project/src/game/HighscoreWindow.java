package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class HighscoreWindow extends Application {
    boolean status = false;
    private Label[] position = new Label[10];
    private Label[] names = new Label[10];
    private Label[] scores = new Label[10];
    private int[] tscores = new int[10];
    private String[] temp,temp2,tnames = new String[10];
    private String[] posString = {"1st","2nd","3rd","4th","5th","6th","7th","8th","9th","10th"};
    private Button menu = new Button("Back to Main Menu");
    private ImageView background = new ImageView(new Image("images\\background2.jpg"));
    public HighscoreWindow(){}

    public HighscoreWindow(boolean status){
        this.status = status;
        if(this.status){
            Stage stage = new Stage();
            start(stage);
        }
    }

    public static void main(String[] args) throws IOException{
        launch(args);
    }

    @Override
    public void start(Stage hsWindow) {

        GridPane gp = new GridPane();
        for(int i=0;i<3;i++) {
            ColumnConstraints cc = new ColumnConstraints(150);
            gp.getColumnConstraints().add(cc);
        }
        gp.setVgap(15);
        gp.setHgap(5);
        gp.add(new Label("Position"),0,0);
        gp.add(new Label("Name"),1,0);
        gp.add(new Label("Score"),2,0);

        File hiScore = new File("Final Project\\src\\files\\highscores.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(hiScore.getAbsoluteFile()));
            if(br.readLine() == null) {
               for(int i=0;i<10;i++){
                   tnames[i] = "Default";
                   tscores[i] = 0;
               }
                br.close();
            }
            else {
                BufferedReader br2 = new BufferedReader(new FileReader(hiScore.getAbsoluteFile()));
                br2.readLine();
                temp2 = br2.readLine().split(",");
                temp = br2.readLine().split(",");


                br2.close();
                if (temp2.length < 10){
                    for(int i=0;i<temp2.length;i++){
                        tnames[i] = temp2[i];
                        tscores[i] = Integer.parseInt(temp[i]);
                    }
                    for(int i=temp2.length;i<10;i++) {
                        tnames[i] = "Default";
                        tscores[i] = 0;
                    }
                }
                else{
                    for(int i=0;i<10;i++) {
                        tnames[i] = temp2[i];
                        tscores[i] = Integer.parseInt(temp[i]);
                    }
                }

            }
            for (int i = 0; i < 10; i++) {
                position[i] = new Label(posString[i]);
                names[i] = new Label(tnames[i]);
                scores[i] = new Label("" + tscores[i]);
                gp.add(position[i], 0, (i + 1));
                gp.add(names[i], 1, (i + 1));
                gp.add(scores[i], 2, (i + 1));
            }
        }
        catch(IOException io){
            System.out.println("well.");
        }
        gp.add(menu,0,11);

        menu.setOnAction(e->{
            Sound.menu.play();
            hsWindow.hide();
            new MenuWindow(true);
        });

        StackPane sp = new StackPane();
        sp.getChildren().add(background);
        sp.getChildren().add(gp);
        Scene hsScene = new Scene(sp,505,350);
        hsScene.getStylesheets().add(this.getClass().getResource("myStyles.css").toExternalForm());


        hsWindow.setTitle("High Scores");
        hsWindow.setResizable(false);
        hsWindow.setScene(hsScene);
        hsWindow.show();
    }
}
