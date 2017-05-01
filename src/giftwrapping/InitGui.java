/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

/**
 *
 * @author YinQuan
 */
import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
public class InitGui {

    public HBox getHbox() {
        return hbox;
    }

    public void setHbox(HBox hbox) {
        this.hbox = hbox;
    }

    public Label getFpsLable() {
        return fpsLable;
    }

    public void setFpsLable(Label fpsLable) {
        this.fpsLable = fpsLable;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public Axes getXyAxis() {
        return xyAxis;
    }

    public void setXyAxis(Axes xyAxis) {
        this.xyAxis = xyAxis;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }

    public void setButtonBox(HBox buttonBox) {
        this.buttonBox = buttonBox;
    }

    public Button getClearPrev() {
        return clearPrev;
    }

    public void setClearPrev(Button clearPrev) {
        this.clearPrev = clearPrev;
    }

    public Button getClearAll() {
        return clearAll;
    }

    public void setClearAll(Button clearAll) {
        this.clearAll = clearAll;
    }

    public HBox getPauseStartBox() {
        return pauseStartBox;
    }

    public void setPauseStartBox(HBox pauseStartBox) {
        this.pauseStartBox = pauseStartBox;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(Button pauseButton) {
        this.pauseButton = pauseButton;
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public HBox getSliderBox() {
        return sliderBox;
    }

    public void setSliderBox(HBox sliderBox) {
        this.sliderBox = sliderBox;
    }

    public HBox getAlgorithmBox() {
        return algorithmBox;
    }

    public void setAlgorithmBox(HBox algorithmBox) {
        this.algorithmBox = algorithmBox;
    }

    public Button getGiftWrapButton() {
        return giftWrapButton;
    }

    public void setGiftWrapButton(Button giftWrapButton) {
        this.giftWrapButton = giftWrapButton;
    }

    public Button getIncrementalButton() {
        return IncrementalButton;
    }

    public void setIncrementalButton(Button IncrementalButton) {
        this.IncrementalButton = IncrementalButton;
    }

    public TableView<Point> getTable() {
        return table;
    }

    public void setTable(TableView<Point> table) {
        this.table = table;
    }

    public Scene getScene() {
        return scene;
    }
    
    public TableView<Step> getSteps() {
        return steps;
    }

    public void setSteps(TableView<Step> steps) {
        this.steps = steps;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
        HBox hbox = new HBox();
        Label fpsLable = new Label("FPS:");
        Canvas canvas = new Canvas(800,600);
        GraphicsContext gc;
        Axes xyAxis  = new Axes(
        800, 600,
                -400, 400, 50,
                -300, 300, 50
        );
        StackPane stackPane = new StackPane(xyAxis,canvas);
        VBox vbox = new VBox();
        HBox buttonBox = new HBox();
        Button clearPrev = new Button("clear prev");
        Button clearAll = new Button("clear all");
        HBox pauseStartBox = new HBox();
        Button pauseButton  = new Button("pause");
        Button startButton = new Button("start");
        Button finishButton = new Button("finish");
        Slider slider = new Slider();
        HBox sliderBox = new HBox();
        HBox algorithmBox = new HBox();
        Button generateButton = new Button("Gen pts");

    public Button getGenerateButton() {
        return generateButton;
    }

    public void setGenerateButton(Button generateButton) {
        this.generateButton = generateButton;
    }
        Button giftWrapButton = new Button("giftWarp");
        Button grahamButton = new Button("graham");

    public Button getFinishButton() {
        return finishButton;
    }

    public void setFinishButton(Button finishButton) {
        this.finishButton = finishButton;
    }

    public Button getGrahamButton() {
        return grahamButton;
    }

    public void setGrahamButton(Button grahamButton) {
        this.grahamButton = grahamButton;
    }
        Button IncrementalButton = new Button("incremental");
        TableView<Point> table = new TableView<Point>();
        TableView<Step> steps = new TableView<Step>();
        Scene scene = new Scene(hbox, 1200, 600);
        Button readFileButton = new Button("Read txt");
    public InitGui(){
        hbox.getChildren().add(stackPane);
        gc = canvas.getGraphicsContext2D();
        hbox.getChildren().add(vbox);
        clearPrev.setPrefWidth(100);
        clearAll.setPrefWidth(100);
        readFileButton.setPrefWidth(100);
        buttonBox.getChildren().addAll(clearAll,clearPrev,readFileButton);
        pauseButton.setPrefWidth(100);
        startButton.setPrefWidth(100);
        finishButton.setPrefWidth(100);
        pauseStartBox.getChildren().addAll(pauseButton,startButton,finishButton);
        slider.setMin(50);
        slider.setMax(500);
        slider.setValue(200);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        generateButton.setPrefWidth(100);
        sliderBox.getChildren().addAll(generateButton,fpsLable,slider);
        table.setEditable(true);
        table.setMaxHeight(200);
        steps.setMaxHeight(200);
        giftWrapButton.setPrefWidth(100);
        IncrementalButton.setPrefWidth(100);
        grahamButton.setPrefWidth(100);
        algorithmBox.getChildren().addAll(giftWrapButton,IncrementalButton,grahamButton);
        vbox.getChildren().addAll(table,steps,buttonBox,sliderBox,algorithmBox,pauseStartBox);
        vbox.setSpacing(10);
        sliderBox.setSpacing(5);
        hbox.setSpacing(10);
        buttonBox.setSpacing(10);
        algorithmBox.setSpacing(10);
        pauseStartBox.setSpacing(10);
}

    public Button getReadFileButton() {
        return readFileButton;
    }

    public void setReadFileButton(Button readFileButton) {
        this.readFileButton = readFileButton;
    }
    
}
