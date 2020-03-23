package sample;
/*Lab 9
Get an API key from here: https://www.alphavantage.co/
Example Call: https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=MSFT&apikey=YOURKEY
function: Type of the data being requested. (i.e TIME_SERIES_MONTHLY)
symbol: The company being requests (i.e MSFT)
apikey: Your api key. (i.e Hexadecimal value)*/

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        double[] stock1 = downloadStockPrices("GOOG");
        double[] stock2 = downloadStockPrices("AAPL");
        primaryStage.setScene(drawLinePlot(stock1,stock2));
        primaryStage.setTitle("Lab 09: Stock Performance");
        primaryStage.show();
    }
    String key = "4Z21N37XHKY8X2AT";
    public double[] downloadStockPrices (String name) throws IOException {
        URL stock = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol="+name+"&apikey=YOURKEY");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(stock.openStream()));
                String data = "";
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    data += inputLine;
                }
                in.close();
                JsonParser parser = new JsonParser();
                JsonElement jsonStocks = parser.parse(data);


                JsonObject jsonStocksList = jsonStocks.getAsJsonObject();
                JsonElement jsonStocksMonthly = jsonStocksList.get("Monthly Time Series");
                JsonObject jsonObjectMonthly = jsonStocksMonthly.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = jsonObjectMonthly.entrySet();
                double[] prices =  new double[entries.size()];
                int i =0;
                for (Map.Entry<String, JsonElement> entry: entries) {
                    JsonElement thisMonth = jsonObjectMonthly.get(entry.getKey());
                    JsonObject month = thisMonth.getAsJsonObject();
                    String price = (month.get("4. close").toString());
                    double priceDouble = Double.parseDouble(price.substring(1,price.length()-1));
                    prices[i] = priceDouble;
                    i++;
                }
                return prices;


    }
    public Scene drawLinePlot(double[] stock1, double[] stock2){
        Line yaxis = new Line(50,550,50,50);
        Line xaxis = new Line(50,550,750,550);
        Group lines = new Group(yaxis,xaxis);
        double temp[] = stock1.clone();
        double temp2[] = stock2.clone();
        Arrays.sort(temp);
        Arrays.sort(temp2);
        double max = Math.max(temp[temp.length-1],temp2[temp2.length-1]);
        lines.getChildren().addAll(plotLine(stock1, Color.RED, max),plotLine(stock2, Color.BLUE, max));
        Scene scene = new Scene(lines,800, 600);
        return scene;

    }
    public Group plotLine(double[] stock, Color colour, double max) {
        double lastx = 50.00;
        double lasty = 550 - (500*(stock[0]/max));
        double deltax = 700/(double)(stock.length);
        Group graph = new Group();
        for(int i=1;i<stock.length;i++){
            double yvalue = 550-(500*(stock[i]/max));
            Line data = new Line(lastx,lasty,lastx+deltax,yvalue);
            data.setStroke(colour);
            lasty=yvalue;
            lastx += deltax;
            graph.getChildren().add(data);
        }
        return graph;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
