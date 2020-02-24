package sample;
import java.lang.String;
public class StudentRecord {
    public String StudentID;
    public double Midterm;
    public double Assignments;
    public double finalExam;
    public double finalGrade;
    public char letterGrade;

    public StudentRecord(String sID, double midT, double assignM, double finalE) {
        this.StudentID = sID;
        this.Midterm = midT;
        this.Assignments = assignM;
        this.finalExam = finalE;
        this.finalGrade = (0.2 * assignM) + (0.3 * midT) + (0.5 * finalE);
        if (finalGrade < 50) {
            letterGrade = 'F';
        } else if ((finalGrade > 49) && (finalGrade < 60)) {
            letterGrade = 'D';
        } else if ((finalGrade > 59) && (finalGrade < 70)) {
            letterGrade = 'C';
        } else if ((finalGrade > 69) && (finalGrade < 80)) {
            letterGrade = 'B';
        } else {
            letterGrade = 'A';
        }
    }

    public String getStudentID() {
        return StudentID;
    }

    public double getMidterm() {
        return Midterm;
    }

    public double getAssignments() {
        return Assignments;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public char getLetterGrade(){
        return letterGrade;
    }

}
