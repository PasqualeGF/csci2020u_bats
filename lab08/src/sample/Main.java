package sample;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.Event;

import java.io.*;
import java.text.DecimalFormat;
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

        // Add adding text boxes and buttons
        // Create grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 10, 10, 10));

        // Create each appropriate label and text field for the 4 parameters
        // Investment amount label and text field
        Label studentID = new Label("SID:");
        grid.add(studentID, 0, 1);
        TextField sIDTextField = new TextField();
        sIDTextField.setPromptText("SID");
        grid.add(sIDTextField, 1, 1);

        // Number of assignments label and text field
        Label assignments = new Label("Assignments:");
        grid.add(assignments, 2, 1);

        TextField asmtTextField = new TextField();
        asmtTextField.setPromptText("Assignments/100");
        grid.add(asmtTextField, 3, 1);

        // Annual rate label and text field
        Label midterms = new Label("Midterm:");
        grid.add(midterms, 0, 2);

        TextField midTextField = new TextField();
        midTextField.setPromptText("Midterm/100");
        grid.add(midTextField, 1, 2);

        // Future value label and text field
        Label exam = new Label("Final Exam:");
        grid.add(exam, 2, 2);

        TextField examTextField = new TextField();
        examTextField.setPromptText("Final Exam/100");
        grid.add(examTextField, 3, 2);

        // Create button to execute calculation
        Button btn = new Button("Add");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3);

        // Set button action to do calculation
        btn.setOnAction(e -> {
            String sID = String.valueOf(sIDTextField.getText());
            Double asmt = Double.valueOf(asmtTextField.getText());
            Double mid = Double.valueOf(midTextField.getText());
            Double finalExam = Double.valueOf(examTextField.getText());
            StudentRecord addition = new StudentRecord(sID,asmt,mid,finalExam);
            ObservableList<StudentRecord> resetTable = load(currentFilename);
            resetTable.add(addition);
            table.getItems().clear();
            table.setItems(resetTable);

        });

        // Create vbox with menu and table
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(menuBar);
        vbox.getChildren().addAll(label, table,grid);


        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}

