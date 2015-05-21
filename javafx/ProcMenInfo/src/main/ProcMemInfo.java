package main;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class ProcMemInfo extends Application {

    final static String FILE_MEMINFO = "/proc/meminfo";

    private final TableView<Record> tableMem = new TableView<>();
    ObservableList<Record> listRecords = FXCollections.observableArrayList();

    XYChart.Series series1 = new XYChart.Series();  //load in Application Thread
    XYChart.Series series2 = new XYChart.Series();  //load in Background Thread
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    @Override
    public void start(Stage primaryStage) {

        //position A - run in UI Thread, before UI show
        //prepareMemInfo();

        tableMem.setEditable(false);

        TableColumn colMemField = new TableColumn("Field");
        colMemField.setMinWidth(150);
        colMemField.setCellValueFactory(
            new PropertyValueFactory<>("memField"));

        TableColumn colMemValue = new TableColumn("Value");
        colMemValue.setMinWidth(100);
        colMemValue.setCellValueFactory(
            new PropertyValueFactory<>("memValue"));

        TableColumn colMemUnit = new TableColumn("Unit");
        colMemUnit.setMinWidth(50);
        colMemUnit.setCellValueFactory(
            new PropertyValueFactory<>("memUnit"));

        TableColumn colMemQty = new TableColumn("Qty");
        colMemQty.setMinWidth(200);
        colMemQty.setCellValueFactory(
            new PropertyValueFactory<>("memQty"));

        tableMem.setItems(listRecords);
        tableMem.getColumns().addAll(colMemField,
            colMemValue, colMemUnit, colMemQty);

        //Create a dummy ProgressBar running
        ProgressTask progressTask = new ProgressTask();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.progressProperty().bind(progressTask.progressProperty());

        new Thread(progressTask).start();
        //---

        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableMem, progressBar);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 500, 250);

        primaryStage.setTitle("java-buddy");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Open second window
        barChart.setTitle(FILE_MEMINFO);
        xAxis.setLabel("Field");
        yAxis.setLabel("Qty");
        barChart.getData().addAll(series1, series2);

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(barChart);

        Scene secondScene = new Scene(secondaryLayout, 500, 400);

        Stage secondStage = new Stage();
        secondStage.setTitle("Second Stage");
        secondStage.setScene(secondScene);

        //Set position of second window, related to primary window.
        secondStage.setX(primaryStage.getX() + 250);
        secondStage.setY(primaryStage.getY() + 20);
        secondStage.show();

        //position B - run in UI Thread, after UI show
        //prepareMemInfo();

        //position C - run in background Thread
        new Thread(BackgroundPrepareMemoInfo).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    Runnable BackgroundPrepareMemoInfo = new Runnable() {
        @Override
        public void run() {
            prepareMemInfo();
        }
    };

    private void prepareMemInfo() {

        //dummy delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcMemInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        Stream<String> streamMemInfo = readFile(FILE_MEMINFO);
        streamMemInfo.forEach((line) -> {
            System.out.println(line);

            //split one line by whitespace/grouped whitespaces
            String[] oneLine = line.split("\\s+");

            for (String ele : oneLine) {
                System.out.println(ele);
            }

            System.out.println("---");

            String rField = "";
            BigInteger rMemValue = new BigInteger("0");
            String rMemUnit = "";

            if (oneLine.length <= 3) {
                if (oneLine.length == 3) {
                    rField = oneLine[0];
                    rMemValue = BigInteger.valueOf(Long.valueOf(oneLine[1]));
                    rMemUnit = oneLine[2];
                } else if (oneLine.length == 2) {
                    rField = oneLine[0];
                    rMemValue = BigInteger.valueOf(Long.valueOf(oneLine[1]));
                    rMemUnit = "B";
                } else if (oneLine.length == 1) {
                    rField = oneLine[0];
                    rMemValue = BigInteger.ZERO;
                    rMemUnit = "B";
                }

                Record record = new Record(
                    rField, rMemValue, rMemUnit);
                listRecords.add(record);

                if (Platform.isFxApplicationThread()) {
                    //It's running in UI Thread
                    series1.getData().add(new XYChart.Data(record.getMemField(), record.getMemQty()));
                } else {
                    //It's running in background Thread
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            series2.getData().add(new XYChart.Data(record.getMemField(), record.getMemQty()));
                        }
                    });
                }

            }

        });
    }

    private Stream<String> readFile(String filePath) {
        Path path = Paths.get(filePath);

        Stream<String> fileLines = null;
        try {
            fileLines = Files.lines(path);
        } catch (IOException ex) {
            Logger.getLogger(ProcMemInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileLines;
    }

    public static class Record {

        private final SimpleStringProperty memField;
        private final SimpleIntegerProperty memValue;
        private final SimpleStringProperty memUnit;
        private final SimpleFloatProperty memQty;

        private Record(String memField, BigInteger memValue, String memUnit) {
            this.memField = new SimpleStringProperty(memField);

            this.memValue = new SimpleIntegerProperty(memValue.intValue());
            this.memUnit = new SimpleStringProperty(memUnit);
            if (memValue.intValue() == 0) {
                this.memQty = new SimpleFloatProperty(0.0f);
            } else if (memUnit.equalsIgnoreCase("MB")) {
                this.memQty = new SimpleFloatProperty((float) memValue.intValue() * 1000000.0f);
            } else if (memUnit.equalsIgnoreCase("kB")) {
                this.memQty = new SimpleFloatProperty((float) memValue.intValue() * 1000.0f);
            } else {
                this.memQty = new SimpleFloatProperty((float) memValue.intValue());
            }
        }

        public String getMemField() {
            return memField.get();
        }

        public int getMemValue() {
            return memValue.get();
        }

        public String getMemUnit() {
            return memUnit.get();
        }

        public float getMemQty() {
            return memQty.get();
        }

    }

    final int MAX_PROGRESS = 50;
    class ProgressTask extends Task<Void>{

        @Override
        protected Void call() throws Exception {
            for (int i = 1; i <= MAX_PROGRESS; i++) {
                updateProgress(i, MAX_PROGRESS);
                Thread.sleep(200);
            }
            return null;
        }

    }

}
