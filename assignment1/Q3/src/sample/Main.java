package sample;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

// To note: I built this off of in-class example code, hence some similarities
public class Main extends Application {
    private double radius = 10;
    Circle[] circle = {new Circle(40, 40, 10),
            new Circle(140, 40, 10), new Circle(60, 140, 10)};
    private Line line1 = new Line();
    private Line line2 = new Line();
    private Line line3 = new Line();
    private Text[] text = {new Text(), new Text(), new Text()};
    Circle big = new Circle(100,100,50);
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        // Set colours for the circles
        big.setStroke(javafx.scene.paint.Color.BLACK);
        big.setFill (null);
        for (int i = 0; i < 3; i++) {
            circle[i].setFill(javafx.scene.paint.Color.RED);
            circle[i].setStroke(javafx.scene.paint.Color.BLACK);
        }
        // Set random locations for points along big circle
        for (int i=0; i<3; i++) {
            // Set random X and Y for point
            double randomX = (Math.random() * 200+1);
            double randomY = (Math.random() * 200+1);
            Point2D bigCenter = new Point2D(big.getCenterX(), big.getCenterY());
            // Use random X and Y for calculation of location along circle
            Point2D oldPoint = new Point2D(randomX, randomY);
            Point2D bigCenterToPoint = oldPoint.subtract(bigCenter);
            Point2D bigCenterToNewPoint = bigCenterToPoint.normalize().multiply(big.getRadius());
            Point2D newPoint = bigCenterToNewPoint.add(bigCenter);
            // Set new random location
            circle[i].setCenterX(newPoint.getX());
            circle[i].setCenterY(newPoint.getY());
        }
        // Set lines
        setLines();
        // Add all objects to the pane
        pane.getChildren().addAll(circle[0], circle[1], circle[2],
                line1, line2, line3, text[0], text[1], text[2], big);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 400, 250);
        primaryStage.setTitle("Drag Points"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        // Set dragging of points along big circle
        circle[0].setOnMouseDragged(e -> {
            // Get big circle center and mouse location
            Point2D bigCenter = new Point2D(big.getCenterX(), big.getCenterY());
            Point2D mouse = new Point2D(e.getX(), e.getY());
            // Find current vector from center to mouse location
            Point2D bigCenterToPoint = mouse.subtract(bigCenter);
            // Normalize vector with radius to ensure point stays on circle
            Point2D bigCenterToNewPoint = bigCenterToPoint.normalize().multiply(big.getRadius());
            Point2D newPoint = bigCenterToNewPoint.add(bigCenter);
            // Set the new coordinates and redraw lines
            circle[0].setCenterX(newPoint.getX());
            circle[0].setCenterY(newPoint.getY());
            setLines();
        });

        circle[1].setOnMouseDragged(e -> {
            // Get big circle center and mouse location
            Point2D bigCenter = new Point2D(big.getCenterX(), big.getCenterY());
            Point2D mouse = new Point2D(e.getX(), e.getY());
            // Find current vector from center to mouse location
            Point2D bigCenterToPoint = mouse.subtract(bigCenter);
            // Normalize vector with radius to ensure point stays on circle
            Point2D bigCenterToNewPoint = bigCenterToPoint.normalize().multiply(big.getRadius());
            Point2D newPoint = bigCenterToNewPoint.add(bigCenter);
            // Set the new coordinates and redraw lines
            circle[1].setCenterX(newPoint.getX());
            circle[1].setCenterY(newPoint.getY());
            setLines();
        });

        circle[2].setOnMouseDragged(e -> {
            // Get big circle center and mouse location
            Point2D bigCenter = new Point2D(big.getCenterX(), big.getCenterY());
            Point2D mouse = new Point2D(e.getX(), e.getY());
            // Find current vector from center to mouse location
            Point2D bigCenterToPoint = mouse.subtract(bigCenter);
            // Normalize vector with radius to ensure point stays on circle
            Point2D bigCenterToNewPoint = bigCenterToPoint.normalize().multiply(big.getRadius());
            Point2D newPoint = bigCenterToNewPoint.add(bigCenter);
            // Set the new coordinates and redraw lines
            circle[2].setCenterX(newPoint.getX());
            circle[2].setCenterY(newPoint.getY());
            setLines();
        });
    }

    private void setLines() {
        // Set all lines to draw between the points
        line1.setStartX(circle[0].getCenterX());
        line1.setStartY(circle[0].getCenterY());
        line1.setEndX(circle[1].getCenterX());
        line1.setEndY(circle[1].getCenterY());
        line2.setStartX(circle[0].getCenterX());
        line2.setStartY(circle[0].getCenterY());
        line2.setEndX(circle[2].getCenterX());
        line2.setEndY(circle[2].getCenterY());
        line3.setStartX(circle[1].getCenterX());
        line3.setStartY(circle[1].getCenterY());
        line3.setEndX(circle[2].getCenterX());
        line3.setEndY(circle[2].getCenterY());

        // Compute angles
        double a = new Point2D(circle[2].getCenterX(), circle[2].getCenterY()).
                distance(circle[1].getCenterX(), circle[1].getCenterY());
        double b = new Point2D(circle[2].getCenterX(), circle[2].getCenterY()).
                distance(circle[0].getCenterX(), circle[0].getCenterY());
        double c = new Point2D(circle[1].getCenterX(), circle[1].getCenterY()).
                distance(circle[0].getCenterX(), circle[0].getCenterY());
        double[] angle = new double[3];
        angle[0] = Math.acos((a * a - b * b - c * c) / (-2 * b * c));
        angle[1] = Math.acos((b * b - a * a - c * c) / (-2 * a * c));
        angle[2] = Math.acos((c * c - b * b - a * a) / (-2 * a * b));

        for (int i = 0; i < 3; i++) {
            text[i].setX(circle[i].getCenterX());
            text[i].setY(circle[i].getCenterY() - radius);
            text[i].setText(String.format("%.2f", Math.toDegrees(angle[i])));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 