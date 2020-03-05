package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import java.io.*;


public class Main extends Application {

    @Override
    public void start(Stage s) throws Exception{
        // Set stage title
        s.setTitle("Cards");

        //Generate 3 random numbers (from 1-54)
        int random1 = (int )(Math.random() * 54 + 1);
        int random2 = (int )(Math.random() * 54 + 1);
        int random3 = (int )(Math.random() * 54 + 1);

        // Create 3 file input streams (one per card) using random numbers
        FileInputStream input1 = new FileInputStream("cards/"+random1+".png");
        FileInputStream input2 = new FileInputStream("cards/"+random2+".png");
        FileInputStream input3 = new FileInputStream("cards/"+random3+".png");

        // Create the images for each card
        Image card1 = new Image(input1);
        Image card2 = new Image(input2);
        Image card3 = new Image(input3);

        // Create the ImageViews for each card
        ImageView c1 = new ImageView(card1);
        ImageView c2 = new ImageView(card2);
        ImageView c3 = new ImageView(card3);

        // Create a label for each card
        Label card1L = new Label("", c1);
        Label card2L = new Label("", c2);
        Label card3L = new Label("", c3);

        // Create a horizontal box for cards
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(1,1,1,1));
        hBox.getChildren().add(card1L);
        hBox.getChildren().add(card2L);
        hBox.getChildren().add(card3L);

        // Create the scene
        Scene sc = new Scene(hBox);

        // Set the scene
        s.setScene(sc);
        // Show the scene
        s.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
