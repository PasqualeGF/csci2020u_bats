package sample;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private TableView<StudentRecord> table = new TableView<StudentRecord>();
    public static void main(String[] args) {
        launch(args);
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

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}