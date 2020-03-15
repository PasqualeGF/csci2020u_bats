package sample;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.Event;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {
    String currentFilename = "records.csv";

    private TableView<StudentRecord> table = new TableView<StudentRecord>();
    public static void main(String[] args) {
        launch(args);
    }

    public void save(String fileName, ObservableList<StudentRecord> data)throws Exception{
        Writer writer=null;
        try{
            File file=new File(fileName);
            writer=new BufferedWriter(new FileWriter(file));
            for(StudentRecord person:data){
                String text=person.getStudentID()+","+person.getAssignments()+","+person.getMidterm()+
                        ","+person.getFinalExam()+"\n";
                writer.write(text);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally{

            writer.flush();
            writer.close();
        }
    }
    public ObservableList<StudentRecord> load(String fileName) {

        String CsvFile = fileName;
        String FieldDelimiter = ",";

        BufferedReader br;
        ObservableList<StudentRecord> dataList  = FXCollections.observableArrayList();
        try {
            br = new BufferedReader(new FileReader(CsvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);

                StudentRecord record = new StudentRecord(fields[0], Double.valueOf(fields[1]), Double.valueOf(fields[2]),
                        Double.valueOf(fields[3]));
                dataList.add(record);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return dataList;

    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Student Record");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Student Records");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn studentIDCol = new TableColumn("Student ID");
        studentIDCol.setMinWidth(100);
        studentIDCol.setCellValueFactory(
                new PropertyValueFactory<StudentRecord, String>("StudentID"));

        TableColumn midtermCol = new TableColumn("Midterm");
        midtermCol.setMinWidth(100);
        midtermCol.setCellValueFactory(
                new PropertyValueFactory<StudentRecord, String>("Midterm"));

        TableColumn assignmentsCol = new TableColumn("Assignments");
        assignmentsCol.setMinWidth(100);
        assignmentsCol.setCellValueFactory(
                new PropertyValueFactory<StudentRecord, String>("Assignments"));

        TableColumn finalExamCol = new TableColumn("Final Exam");
        finalExamCol.setMinWidth(100);
        finalExamCol.setCellValueFactory(
                new PropertyValueFactory<StudentRecord, String>("finalExam"));

        TableColumn finalGradeCol = new TableColumn("Final Grade");
        finalGradeCol.setMinWidth(100);
        finalGradeCol.setCellValueFactory(
                new PropertyValueFactory<StudentRecord, String>("finalGrade"));

        TableColumn letterGradeCol = new TableColumn("Letter Grade");
        letterGradeCol.setMinWidth(100);
        letterGradeCol.setCellValueFactory(
                new PropertyValueFactory<StudentRecord, String>("letterGrade"));

        table.setItems(DataSource.getAllMarks());
        table.getColumns().addAll(studentIDCol,midtermCol,assignmentsCol,finalExamCol,finalGradeCol,letterGradeCol);

        //Drop down menu
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");

        menuBar.getMenus().addAll(menuFile);

        // New file menu option
        MenuItem menuNew = new MenuItem("New");
        menuNew.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Clear table
                table.getItems().clear();
            }
        });

        // Open file menu option
        MenuItem menuOpen = new MenuItem("Open");
        menuOpen.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Clear table
                table.getItems().clear();
                // Open file chooser for csv files
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
                File selectedFile = fileChooser.showOpenDialog(null);
                // Save file name
                currentFilename = selectedFile.getName();
                // Re-populate with new file
                table.setItems(load(currentFilename));

            }
        });

        // Save file menu option
        MenuItem menuSave = new MenuItem("Save");
        menuSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    save(currentFilename,table.getItems());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // SaveAs file menu option
        MenuItem menuSaveAs = new MenuItem("Save As");
        menuSaveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // Open file chooser for csv files
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
                File selectedFile = fileChooser.showOpenDialog(null);
                // Save file
                currentFilename = selectedFile.getName();
                try {
                    save(currentFilename,table.getItems());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // Exit file menu option
        MenuItem menuExit = new MenuItem("Exit");
        menuExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        // Add all options to menu
        menuFile.getItems().addAll(menuNew,menuOpen,menuSave,menuSaveAs,menuExit);

        // Create vbox with menu and table
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(menuBar);
        vbox.getChildren().addAll(label, table);


        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}

