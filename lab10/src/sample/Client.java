package sample;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Client extends Application {

    // Creating Window Components and GUI Components
    VBox container = new VBox();
    // upperSide creates the pane: containing nameLabel, ClientNameField and save button
    GridPane upperSide = new GridPane();
    // lowerSide:  replybutton, messageBody
    GridPane lowerSide = new GridPane();
    // Create UI controls: reply button which triggering the response function.
    Button replyBtn = new Button("Send");
    Button exitBtn = new Button("Exit");
    // Client name label
    Label nameLbl = new Label(" Username:");
    // Message name label
    Label msgLbl = new Label(" Message:");
    // Reply body
    TextField messageBody = new TextField();
    // Adds the user name from the textfield
    TextField ClientNameField = new TextField();


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Creates and configure User Window
        primaryStage.setResizable(false);
        setupGUI(primaryStage);
        // Start the chat server
        startChat();

    }
    // Override stop to ensure program exits successfully on window close
    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
    private void setupGUI(Stage primaryStage) {
        // Configure the upperSide GridPane: nameLabel, ClientNameField and save button
        setupUpperside(primaryStage);
        // Configure the lowerSide GridPane: replyBtn, messageBody
        setupLowerSide();
        // Adds children to Vbox
        setupContainer();
        // Show the scene
        setupStage(primaryStage);
    }

    // This function will setup the stage settings
    private void setupStage(Stage primaryStage) {
        Scene scene = new Scene(container);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Client");
        primaryStage.show();
    }

    // This function will setup the container area
    private void setupContainer() {
        // Add components in order to fill the window
        container.setPadding(new Insets(50, 20, 50, 50));
        container.setSpacing(10);
        container.getChildren().addAll(upperSide, lowerSide, replyBtn, exitBtn);
        exitBtn.setOnAction(
                e -> {// Check if any of the fields is empty and stop the process


                    System.exit(0);

                });


    }


    // This function will setup the chat components
    private void setupLowerSide() {
        // Set number of columns to organize elements
        int numCols = 4;
        for (int i = 0; i < numCols; i++) { // Loops through the columns
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(100);
            lowerSide.getColumnConstraints().add(colConst);
        }
        // Set message body max width
        messageBody.setMaxWidth(2000);

        lowerSide.add(messageBody, 1, 0,2,1);
        lowerSide.add(msgLbl,0,0);
        replyBtn.setOnAction(
                // Setup the reply by adding the text to chat area and writes it to the client.

                e -> {// If any of the fields are empty and stop the process
                    if (messageBody.getText().isEmpty() == false & ClientNameField.getText().isEmpty()==false)
                    {
                        // Send message to Agent
                        sendMessage("\n" + ClientNameField.getText() + ": " + messageBody.getText());
                    }

                    else {// This code will stop the user from sending empty replies: alert Box
                        setupErrorBox();
                    }
                    // Reset the reply text
                    messageBody.setText("");
                });

    }

    // This function will stop the user from sending empty replies: alert box
    private void setupErrorBox() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Box");
        alert.setContentText("Reply or Name is empty.");
        alert.showAndWait();
    }

    // This function will setup the name components
    private void setupUpperside(Stage primaryStage) {
        // Set number of columns to organize elements
        int numCols = 4;
        for (int i = 0; i < numCols; i++) { //loops through the cols
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(100);

            upperSide.getColumnConstraints().add(colConst);
        }
        // Add elements to the columns
        ClientNameField.setMaxWidth(1000);
        upperSide.add(nameLbl, 0, 0);
        upperSide.add(ClientNameField, 1, 0,2,1);

    }


    /********************************************* SERVER CODE *********************************************/
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP = "127.0.0.1";
    private Socket connection;
    // Function to connect to server and start chat
    public void startChat(){
        // Create another thread for server to run on
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    // Establish connection and run input and output stream functions: retrieves and send messages
                    connectToAgent();
                    setupStreams();
                    connectedChat();
                } catch (EOFException eofException) {

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    // Close connection when done
                    closeConnection();
                }
            }
        };
        // Start server thread
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    // Function to connect to Agent on server
    private void connectToAgent() throws IOException{
        // Get server connection
        connection = new Socket(InetAddress.getByName(serverIP), 3000);

    }

    // Function to setup streams
    private void setupStreams() throws IOException{
        // Setup input and output streams
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
    }

    // Function to handle messages while chatting
    private void connectedChat() throws IOException{
        do{
            try{
                // Append user's message
                message = (String) input.readObject();

            }catch(ClassNotFoundException classNotFoundException){
                // If message sent is not valid, print error message

            }
        }while(!message.equals("END CHAT"));
    }
    // Function to close the connections
    public void closeConnection(){
        try{
            // Close path to and from Agent
            output.close();
            connection.close(); //close connection
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    // Send message to the Agent
    private void sendMessage(String message){
        try{
            output.writeObject(message);
            output.flush();
        }catch(IOException ioException){

        }
    }




}
