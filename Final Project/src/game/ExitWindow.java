package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;


public class ExitWindow extends Application {
    private boolean status = false;
    private int position = 1,numEntries = 0;
    private Button exitButton = new Button("Submit");
    private TextField name = new TextField("");
    private Label prompt = new Label("");
    private Image gameover = new Image("images\\gameover.jpg");
    private ImageView gm1 = new ImageView(gameover);
    private static int[] scores;
    private static String[] names,temp,temp2;
    private String suffix ="";
    public ExitWindow(){}

    public ExitWindow(boolean status) throws IOException{
        this.status = status;
        if(this.status){
            Stage stage = new Stage();
            start(stage);
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage exitStage) throws IOException{
        name.setMinWidth(225);
        GridPane gp1 = new GridPane();
        gp1.setVgap(5.0);
        gp1.add(gm1,0,0);
        gp1.add(prompt,0,1);
        FlowPane fp = new FlowPane();
        fp.setHgap(2.0);
        fp.getChildren().addAll(name,exitButton);
        gp1.add(fp,0,2);

        StackPane sp1 = new StackPane();
        sp1.getChildren().add(gp1);
        Scene exit = new Scene(sp1,505,350);
        exit.getStylesheets().add(this.getClass().getResource("myStyles.css").toExternalForm());

        File hiScore = new File("Final Project\\src\\files\\highscores.txt");
        if (!hiScore.exists()) {
            hiScore.createNewFile();
        }

        BufferedReader br = new BufferedReader(new FileReader(hiScore.getAbsoluteFile()));
        if(br.readLine() == null) {
            numEntries = 1;
            names = new String[1];
            scores = new int [1];
            names[0] = name.getText();
            scores[0] = GUI.score;
            br.close();
        }
        else {
            BufferedReader br2 = new BufferedReader(new FileReader(hiScore.getAbsoluteFile()));
            numEntries = Integer.parseInt(br2.readLine());
            numEntries++;
            names = new String[numEntries];
            scores = new int[numEntries];
            temp2 = br2.readLine().split(",");
            temp = br2.readLine().split(",");

            for (int i = 0; i < temp.length; i++)
                scores[i] = Integer.parseInt(temp[i]);
            for (int i = 0; i < temp2.length; i++)
                names[i] = temp2[i];
            scores[numEntries - 1] = GUI.score;
            br2.close();
        }
        br.close();
        insertionSort();

        for(int i=0;i<scores.length;i++){
            if (GUI.score == scores[i])
                position = i+1;
        }
        suffix = calculateSuffix(position);
        prompt.setText("Your final score is: " + GUI.score +"\nYou have placed " + position + suffix + "\n" +
                "Please enter your name:");
        exitButton.setOnAction(e->{
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(hiScore.getAbsoluteFile()));
                names[position-1] = name.getText().trim().replaceAll(",","");
                String tempStr = Integer.toString(numEntries);
                bw.write(tempStr);
                bw.newLine();
                for(int i=0;i<names.length;i++) {
                    if (i == names.length-1)
                        bw.write(names[i]);
                    else
                        bw.write(names[i] + ",");
                }
                bw.newLine();
                for(int i=0;i<scores.length;i++) {
                    tempStr = Integer.toString(scores[i]);
                    if (i == scores.length-1)
                        bw.write(tempStr);
                    else
                        bw.write(tempStr + ",");
                }
                bw.close();
            }
            catch(IOException io){
                System.out.println("shit my dude.");
            }
            Sound.menu.play();
            exitStage.hide();
            GameWindow.gameStage.hide();
            new MenuWindow(true);
        });


        exitStage.setX(Screen.getPrimary().getVisualBounds().getMaxX()/2 - 150);
        exitStage.setTitle("Game Over");
        exitStage.setMaxHeight(350);
        exitStage.setMaxWidth(505);
        exitStage.setResizable(false);
        exitStage.setScene(exit);
        exitStage.show();

    }

    private void insertionSort(){
        int temp;
        String temp2;
        for(int i=1;i<scores.length;i++){
            for(int j=i;j>0;j--){
                if(scores[j] > scores[j-1]) {
                    temp = scores[j];
                    scores[j] = scores[j-1];
                    scores[j-1] = temp;
                    temp2 = names[j];
                    names[j] = names[j-1];
                    names[j-1] = temp2;
                }
            }
        }
    }

    private String calculateSuffix(int position){
        String suffix;
        switch (position){
            case 1:
            case 21:
            case 31:
                suffix = "st";
                break;
            case 2:
            case 22:
            case 32:
                suffix = "nd";
                break;
            case 3:
            case 23:
            case 33:
                suffix = "rd";
                break;
            default: suffix ="th";
        }
        return suffix;
    }
}
