package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.ArcType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Main extends Application {
    private static Color[] pieColours = {Color.AQUA, Color.GOLD, Color.DARKORANGE,Color.DARKSALMON};
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.WHITE);
        // Declare array for number of each disaster (flood, thunder, marine, tornado)
        int[] disasters = new int[4];
        for (int i =0;i<4;i++){
            disasters[i] = 0;
        }
        // Read file and put # of each disaster into array
        String csvFile = "weatherwarnings-2015.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // Use comma as separator
                String[] disaster = line.split(cvsSplitBy);

                if (disaster[5].equals("FLASH FLOOD")){
                    disasters[0]+=1;
                }
                else if (disaster[5].equals("SEVERE THUNDERSTORM")){
                    disasters[1]+=1;
                }
                if (disaster[5].equals("SPECIAL MARINE")){
                    disasters[2]+=1;
                }
                else if (disaster[5].equals("TORNADO")){
                    disasters[3]+=1;
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        final Canvas canvas = new Canvas(1000,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double totalDisaster = 29195;
        double totalDegree = 0;
        for (int i=0; i<4;i++){
            gc.setFill(pieColours[i]);
            double percent = (disasters[i]/totalDisaster);
            double degree = (percent*360);
            System.out.println(disasters[i]);
            System.out.println(percent);
            System.out.println(degree);
            gc.fillArc(190,60,100,100,totalDegree,degree,ArcType.ROUND);
            totalDegree += degree;
        }

        String[] labels = {"FLASH FLOOD", "SEVERE THUNDERSTORM", "SPECIAL MARINE", "TORNADO"};
        gc.setFont(new Font(10));
        for (int i=0;i<4;i++) {
            gc.setFill(pieColours[i]);
            System.out.println(i);
            gc.fillRect(30, 60+i*25, 30, 15);
            gc.strokeText(labels[i],65,73+i*25);
        }
        root.getChildren().add(canvas);
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
