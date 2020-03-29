package sample;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
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

public class Server extends Application {

    // Creating Window Components and GUI Components
    // The VBox container will contain the window
    VBox container = new VBox();
    // Scrollbar is created for the textarea
    ScrollPane chatBar = new ScrollPane();
    TextArea chatText = new TextArea();
    // lowerSide GridPane contains: reply button, messageBody
    GridPane lowerSide = new GridPane();
    // Create UI controls: exit button triggers exit.
    Button exitBtn = new Button("Exit");
    TextField messageBody = new TextField();

    // Main start function
    @Override
    public void start(Stage primaryStage) throws Exception {
        // This will create and configure User Window
        primaryStage.setResizable(false);
        setupGUI(primaryStage);
        // Starts the chat server
        startChat();
    }
    // Override stop to ensure program exits successfully on window close
    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }
    private void setupGUI(Stage primaryStage) {
        // Function to configure the lowerSide GridPane containing replyBtn,messageBody
        setupLowerSide();
        // Function to configure the ScrollBar and the TextArea
        setupChat();
        // Adding children to Vbox
        setupContainer();
        // Show the scene
        setupStage(primaryStage);
    }

    // This function will setup the stage settings
    private void setupStage(Stage primaryStage) {
        Scene scene = new Scene(container);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Server");
        primaryStage.show();
    }

    // This function will setup the container area
    private void setupContainer() {
        // Add components to the window
        container.setPadding(new Insets(50, 50, 50, 50));
        container.setSpacing(10);
        container.getChildren().addAll(chatBar, lowerSide);
    }

    // Function to setup the chat scrollbar, adds the textarea to it
    private void setupChat() {
        // Set width
        chatText.setMaxWidth(1000);

        // Disables editing in the textarea
        chatText.setEditable(false);
        // Add textarea to the scrollbar
        chatBar.setContent(chatText);

    }

    // Sets up the chat components
    private void setupLowerSide() {

        lowerSide.add(exitBtn, 0, 0);

        // Adds the text to chat area and provides it to the Agent.
        exitBtn.setOnAction(
                e -> {// Check if any of the fields is empty and stop the process


                    System.exit(0);

                });

    }



    /********************************************* SERVER CODE *********************************************/
    private ObjectInputStream input;
    private ServerSocket server;
    private String message = "";
    private Socket connection;
    // Function to start server and chat
    public void startChat(){
        // Create another thread for server to run on
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {
                        // Establish server socket and max number of backlogged connected users
                        server = new ServerSocket(3000, 50);
                        try {
                            // Wait for connection and run input and output stream functions to retrieve and send messages
                            waitingToConnect();
                            setupStreams();
                            connectedChat();
                        } catch (EOFException eofException) {
                            // If server connection interrupted print error message
                            showMessage("\n ERROR: Connection interrupted. ");
                        } finally {
                            // Close connection when done
                            closeConnection();
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        // Start server thread
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    // Function to connect to server and client
    private void waitingToConnect() throws IOException{
        connection = server.accept();
    }
    // Function to setup streams
    private void setupStreams() throws IOException{
        // Setup input and output streams
        input = new ObjectInputStream(connection.getInputStream());
    }

    // Function to handle messages while chatting
    // Function to handle messages while chatting
    private void connectedChat() throws IOException{
        do{
            try{
                // Append incoming user's message
                message = (String) input.readObject();
                showMessage(message);
            }catch(ClassNotFoundException classNotFoundException){
                // If message sent is not valid, print error message
                showMessage("ERROR: User has sent an invalid object!");
            }
        }while(!message.equals("END CHAT"));


    }

    // Function to close the connections
    public void closeConnection(){
        try{
            // Close path to and from client, as well as connection
            input.close();
            connection.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }


    // Update chat windows with message
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        // Show message in text box
                        chatText.appendText(text);
                    }
                }
        );
    }

}


