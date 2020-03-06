package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class Main extends Application {
    String file = "test.txt";

    @Override
    public void start(Stage primaryStage) throws IOException{
        // Create filename variable

        // Create array to count each letter in file and max count variable
        double[] numberOfLetters =  new double [26];
        double maxCount = 0;
        // Initialize array
        for (int i=0;i<26;i++){
            numberOfLetters[i] = 0;
        }
        // Create alphabet array (for comparison)
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        // Load the text file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        // Read line by line
        while (true) {
            String line = reader.readLine();
            // End loop at null line
            if (line == null) {
                break;
            }
            // Check each line for number of each letter, letter by letter
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c != ' ') {
                    // Check which letter and increment count
                    for (int j=0; j<26; j++){
                        if(c == alphabet[j]){
                            numberOfLetters[j]++;
                            // Update maxCount if needed
                            if(numberOfLetters[j]>maxCount){
                                maxCount = numberOfLetters[j];
                            }
                        }
                    }
                }
            }
        }

        // Close the reader
        reader.close();

        // Set the scene and canvas for histogram
        Pane root = new Pane();
        Scene s = new Scene(root, 300, 300, Color.WHITE);
        final Canvas canvas = new Canvas(1000,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw a rectangle for each letter with relative height
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        for (int i = 0; i<26;i++) {
            gc.fillRect(20+(i*10), 50+(100-((numberOfLetters[i])/maxCount)*100), 8,  ((numberOfLetters[i])/maxCount)*100);
        }
        // Add bottom axis and axis label (letters)
        gc.fillRect(20,150,258,1);
        gc.setFont(new Font("Times New Roman", 11));
        gc.strokeText("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z",20,160);

        // Add canvas to pane
        root.getChildren().add(canvas);
        // Add label and text to pane
        Label fileName = new Label("Filename:");
        fileName.setLayoutX(20);
        fileName.setLayoutY(190);
        TextField fileTextField = new TextField();
        fileTextField.setPromptText("Type a filename");
        fileTextField.setLayoutX(80);
        fileTextField.setLayoutY(190);
        root.getChildren().add(fileName);
        root.getChildren().add(fileTextField);

        // Add button
        Button btn = new Button();
        btn.setText("View");
        btn.setOnAction(e -> {
            String fs = String.valueOf(fileTextField.getText());
            // Display future value to the appropriate text field
            file = fs;
        });
        btn.setLayoutX(230);
        btn.setLayoutY(190);
        root.getChildren().add(btn);


        // Set scene and display
        primaryStage.setTitle("Number of Letters");
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
