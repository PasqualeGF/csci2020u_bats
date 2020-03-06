package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.ArcType;


public class Main extends Application {
    private static double[] avgHousingPricesByYear = {247381.0,264171.4,287715.3,294736.1,308431.4,322635.9,340253.0,363153.7};
    private static double[] avgCommercialPricesByYear = {1121585.3,1219479.5,1246354.2,1295364.8,1335932.6,1472362.0,1583521.9,1613246.3};
    private static String[] ageGroups = {"18-25", "26-35", "36-45", "46-55", "56-65", "65+"};
    private static int[] purchasesByAgeGroup = {648, 1021, 2453, 3173, 1868, 2247};
    private static Color[] pieColours = {Color.AQUA, Color.GOLD, Color.DARKORANGE,Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM};
    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.WHITE);

        final Canvas canvas = new Canvas(1000,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double maxHouse = 363153.7;
        double maxComm = 1613246.3;

        gc.setFill(Color.RED);
        for (int i = 0; i<avgHousingPricesByYear.length;i++) {
            gc.fillRect(20+(i*20), 50+(100-((avgHousingPricesByYear[i])/maxComm)*100), 5,  ((avgHousingPricesByYear[i])/maxComm)*100);
        }

        gc.setFill(Color.BLUE);
        for (int i = 0; i<avgCommercialPricesByYear.length;i++) {
            gc.fillRect(25+(i*20), 50+(100-((avgCommercialPricesByYear[i])/maxComm)*100), 5,  ((avgCommercialPricesByYear[i])/maxComm)*100);
        }

        double totalPurch = 11410;
        double totalDegree = 0;
        for (int i=0; i<purchasesByAgeGroup.length;i++){
            gc.setFill(pieColours[i]);
            double percent = (purchasesByAgeGroup[i]/totalPurch);
            double degree = (percent*360);
            System.out.println(percent);
            System.out.println(degree);
            gc.fillArc(190,60,100,100,totalDegree,degree,ArcType.ROUND);
            totalDegree += degree;
        }
        root.getChildren().add(canvas);
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
