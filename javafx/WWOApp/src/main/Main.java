package main;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.persistence.oxm.MediaType;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class Main extends Application {

  private StackPane sp;

  LocalWeather.Data weather;
  LocationSearch.Data location;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    sp = new StackPane();

    try {
      getWeatherData();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    updateGUI();

    Scene scene = new Scene(sp, 250, 175);
    primaryStage.setScene(scene);
    primaryStage.setResizable(false);
    primaryStage.setTitle("Local weather (from worldweatheronline.com)");
    primaryStage.show();
  }

  public void getWeatherData() throws IOException {
    Ip ipAddress =
        new ObjectMapper().readValue(new URL("http://api.ipify.org/?format=json"), Ip.class);

    LocalWeather lw = new LocalWeather(true);
    String
        query =
        (lw.new Params(lw.getKey(), ipAddress.getIp())).withFormat("xml").withFx("no")
            .getQueryString(LocalWeather.Params.class);
    weather = lw.callAPI(query, MediaType.APPLICATION_XML);

    //get location
    LocationSearch ls = new LocationSearch(true);
    query =
        (ls.new Params(ls.getKey(), ipAddress.getIp())).withFormat("xml").withNumOfResults("1")
            .getQueryString(LocationSearch.Params.class);
    location = ls.callAPI(query, MediaType.APPLICATION_XML);
  }

  private void updateGUI() {
    Text text1 = new Text(
        "Location: " + (location.result.region.isEmpty() ? location.result.areaName
                                                         : location.result.region)
        + ", " + location.result.country);
    text1.setFill(Color.BLACK);
    text1.setFont(Font.font("Helvetica", FontWeight.BOLD, 11));
    Text text2 = new Text(
        " \n" + weather.currentCondition.weatherDesc + "\n" + weather.currentCondition.tempC
        + "\u2103" + "\n" + weather.currentCondition.tempF + "\u2109");
    text2.setFill(Color.BLUE);
    text2.setFont(Font.font("Helvetica", FontWeight.BOLD, 11));
    TextFlow textFlow = new TextFlow(text1, text2);

    Image img = new Image(weather.currentCondition.weatherIconUrl);
    ImageView imgView = new ImageView(img);
    sp.getChildren().addAll(textFlow, imgView);
  }
}
