package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Set stage title
        primaryStage.setTitle("Future Value Calculation");

        // Create grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 10, 10, 10));

        // Create each appropriate label and text field for the 4 parameters
        // Investment amount label and text field
        Label invAmount = new Label("Investment Amount:");
        grid.add(invAmount, 0, 1);
        TextField invTextField = new TextField();
        invTextField.setPromptText("Investment Amount");
        grid.add(invTextField, 1, 1);

        // Number of years label and text field
        Label years = new Label("Years:");
        grid.add(years, 0, 2);

        TextField yearsTextField = new TextField();
        yearsTextField.setPromptText("Years");
        grid.add(yearsTextField, 1, 2);

        // Annual rate label and text field
        Label annualRate = new Label("Annual Interest Rate:");
        grid.add(annualRate, 0, 3);

        TextField rateTextField = new TextField();
        rateTextField.setPromptText("Annual Interest Rate");
        grid.add(rateTextField, 1, 3);

        // Future value label and text field
        Label futureValue = new Label("Future Value:");
        grid.add(futureValue, 0, 4);

        TextField fvTextField = new TextField();
        // Ensure user cannot edit this text field
        fvTextField.setEditable(false);
        fvTextField.setPromptText("Press the button");
        grid.add(fvTextField, 1, 4);

        // Create button to execute calculation
        Button btn = new Button("Calculate");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 7);

        // Set button action to do calculation
        btn.setOnAction(e -> {
            Double invA = Double.valueOf(invTextField.getText());
            Double y = Double.valueOf(yearsTextField.getText());
            Double rate = Double.valueOf(rateTextField.getText());
            // Set decimal format to round the future value
            DecimalFormat df = new DecimalFormat("0.00");
            // Calculate future value
            Double finalFV = (invA * Math.pow((1+rate/100),y));
            // Display future value to the appropriate text field
            fvTextField.setText(df.format(finalFV));
        });

        // Create, set and display scene
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
