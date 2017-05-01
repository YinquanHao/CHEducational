/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author YinQuan
 */
public class Giftwrapping extends Application {
    public int pointCounter = 0;
    private ObservableList<Point> points = FXCollections.observableArrayList();
    private ObservableList<Step> steps =  FXCollections.observableArrayList();
    public boolean isPause = false;
    public boolean isFinish = false;

    public boolean isIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
    public InitEvent ie;

    public InitEvent getIe() {
        return ie;
    }

    public void setIe(InitEvent ie) {
        this.ie = ie;
    }

    public ObservableList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ObservableList<Step> steps) {
        this.steps = steps;
    }
    private ArrayList<Point> actualPoints = new ArrayList<Point>();

    public int getPointCounter() {
        return pointCounter;
    }

    public void setPointCounter(int pointCounter) {
        this.pointCounter = pointCounter;
    }

    public ObservableList<Point> getPoints() {
        return points;
    }

    public void setPoints(ObservableList<Point> points) {
        this.points = points;
    }

    public ArrayList<Point> getActualPoints() {
        return actualPoints;
    }

    public void setActualPoints(ArrayList<Point> actualPoints) {
        this.actualPoints = actualPoints;
    }
    
   
    
    @Override
    public void start(Stage primaryStage) {
        InitGui gui = new InitGui();
        CanvasDraw cd = new CanvasDraw(this);
        ie = new InitEvent(gui,this, cd);
        ie.initAll();
        

        
       
        
        primaryStage.setTitle("Gif Warpping");
        primaryStage.setScene(gui.getScene());
        primaryStage.show();
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public String StringMod(String format, String obj){
        String res = "";
        res = format.replaceFirst("%", obj);
        System.out.println(res);
        return res;
    }
    public String StringModMutiple(String format, String[] args){
        for(int i=0;i<args.length;i++){
            format = format.replaceFirst("%", args[i]);
        }
        return format;
    }
  
    
     
    
}

