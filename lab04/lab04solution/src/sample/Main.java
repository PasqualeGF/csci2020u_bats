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
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("lab04solution");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        userTextField.setPromptText("User Name");
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwField = new PasswordField();
        pwField.setPromptText("Password");
        grid.add(pwField, 1, 2);

        Label fullName = new Label("Full Name:");
        grid.add(fullName, 0, 3);

        TextField fullTextField = new TextField();
        fullTextField.setPromptText("Full Name");
        grid.add(fullTextField, 1, 3);

        Label email = new Label("Email:");
        grid.add(email, 0, 4);

        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Email");
        grid.add(emailTextField, 1, 4);

        Label phoneNo = new Label("Phone #:");
        grid.add(phoneNo, 0, 5);

        TextField phoneTextField = new TextField();
        phoneTextField.setPromptText("Phone #");
        grid.add(phoneTextField, 1, 5);

        Label birthday = new Label("Date of Birth:");
        grid.add(birthday, 0, 6);

        DatePicker birthdayDatePicker = new DatePicker();
        birthdayDatePicker.setPromptText("Date of Birth");
        grid.add(birthdayDatePicker, 1, 6);

        Button btn = new Button("Register");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 7);


        final Text actiontarget = new Text();
                grid.add(actiontarget, 1, 6);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed");
            }
        });

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
