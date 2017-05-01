/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author YinQuan
 */
public class InitEvent {
    InitGui gui;
    Giftwrapping gw;
    CanvasDraw cd;
    Thread newThread;
    private final Object monitor = new Object();
    public InitEvent(InitGui gui, Giftwrapping gw, CanvasDraw cd) {
        this.gui = gui;
        this.gw = gw;
        this.cd = cd;
    }
    public void initAll(){
        gui.getClearPrev().setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                System.out.println("pointCounter"+ gw.pointCounter);
                if(gw.pointCounter>0){
                gw.getActualPoints().remove(--gw.pointCounter);
                gw.getPoints().remove(gw.pointCounter);
                cd.repaint(gui.getGc(),gw.getPoints());
                }
    }
});
        gui.getClearAll().setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                for(int i=gw.getPoints().size()-1;i>=0;i--){
                    gw.getPoints().remove(i);
                    gw.getActualPoints().remove(i);
                    System.out.println("removing "+i);
                }
                System.out.println("size of points"+ gw.getPoints().size());
                cd.repaint(gui.getGc(),gw.getPoints());
                gw.pointCounter = 0;
    }
});
        
        TableColumn pointIndex = new TableColumn("PointIndex");
        pointIndex.setMinWidth(100);
        pointIndex.setCellValueFactory(
                new PropertyValueFactory<Point, Integer>("index"));
        
        TableColumn xCord = new TableColumn("X-cord");
        xCord.setMinWidth(100);
        xCord.setCellValueFactory(
                new PropertyValueFactory<Point, Double>("axisX"));
        TableColumn yCord = new TableColumn("Y-cord");
        yCord.setMinWidth(100);
        yCord.setCellValueFactory(
                new PropertyValueFactory<Point, Double>("axisY"));
        
        TableColumn step = new TableColumn("Algo Descrip");
        step.setMinWidth(300);
        step.setCellValueFactory(
                new PropertyValueFactory<Step, String>("description"));
        
        gui.getTable().getColumns().addAll(pointIndex, xCord, yCord);
        gui.getTable().setItems(gw.getPoints());
        
        gui.getSteps().getColumns().add(step);
        gui.getSteps().setItems(gw.getSteps());
        
        gui.getGiftWrapButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
                try {
                    runGiftWrapping(gw.getActualPoints(),gui.getGc(),gw.getPoints());
                } catch (InterruptedException ex) {
                    Logger.getLogger(InitEvent.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
});     
        gui.getCanvas().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                System.out.println("mouse click detected! "+event.getSceneX()+" "+event.getSceneY());
                cd.drawPoints(gui.getGc(),event.getX(),event.getY());
        }
    });
        gui.getReadFileButton().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Open File");
                chooser.getExtensionFilters().addAll(
                        new ExtensionFilter("Text Files", "*.txt"));
                File file = chooser.showOpenDialog(new Stage());
                if(file !=null){
                try{
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = br.readLine();
                    double rfx;
                    double rfy;                 
                    while(line!=null){
                        rfx=Double.parseDouble(line.split(",")[0]);
                        rfy=Double.parseDouble(line.split(",")[1]);
                        if(rfx>-400&&rfx<400&&rfy>-300&&rfy<300){
                            cd.drawPoints(gui.getGc(),rfx+400,300-rfy);
                        }
                        line = br.readLine();
                    }
                }catch(IOException e1){}
                
                }
            }
        });
        
        gui.getGenerateButton().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                //System.out.println("marh"+Math.random());
                gw.getPoints().clear();
                gw.getActualPoints().clear();
                cd.repaint(gui.getGc(),gw.getPoints());
                gw.pointCounter = 0;
                double numbers = Math.random()*100;
                System.out.println("numbers"+numbers);
                for(int i=0;i<numbers;i++){
                    cd.drawPoints(gui.getGc(), (int)(Math.random()*800), (int)(Math.random()*600));
                }
                }
            }
        );
        
        
        gui.getPauseButton().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                gw.isPause = true;
                
                }
            }
        );
        
        gui.getStartButton().setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                gw.isPause = false;  
                }
            }
        );
        
       gui.getFinishButton().setOnAction(new EventHandler<ActionEvent>(){
           @Override
            public void handle(ActionEvent e){
                gui.getSlider().setValue(gui.getSlider().getMin());
                }
       }
       );
       
        gui.getGrahamButton().setOnAction(new EventHandler<ActionEvent>(){
           @Override
            public void handle(ActionEvent e){
               try {
                   runGraham(gw.getActualPoints(),gui.getGc(),gw.getPoints());
               } catch (InterruptedException ex) {
                   Logger.getLogger(InitEvent.class.getName()).log(Level.SEVERE, null, ex);
               }
                }
       }
       );
    }
    
    
    private void runGiftWrapping(ArrayList<Point> Actualpoints, GraphicsContext gc,ObservableList<Point> points) throws InterruptedException{
         disableWhileRuning();
         Task<Void> algo = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //GiftWrappingAlgo graham = new GiftWrappingAlgo(Actualpoints,gc,points,cd);
                gw.getSteps().clear();
                JarvisAlgo j = new JarvisAlgo(Actualpoints,gc,points,cd,gw,gui);
                
                return null;
            }
        };
         newThread = new Thread(algo);
         newThread.start();  
    }
    private void runGraham(ArrayList<Point> Actualpoints, GraphicsContext gc,ObservableList<Point> points) throws InterruptedException{
         disableWhileRuning();
         Task<Void> algo = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //GiftWrappingAlgo graham = new GiftWrappingAlgo(Actualpoints,gc,points,cd);
                gw.getSteps().clear();
                GrahmAlgo g = new GrahmAlgo(Actualpoints,gc,points,cd,gw,gui);
                
                return null;
            }
        };
         newThread = new Thread(algo);
         newThread.start();  
    }
    private void disableWhileRuning(){
        gui.getClearAll().setDisable(true);
        gui.getClearPrev().setDisable(true);
        gui.getGiftWrapButton().setDisable(true);
        gui.getIncrementalButton().setDisable(true);
        gui.getReadFileButton().setDisable(true);
        gui.getGrahamButton().setDisable(true);
        gui.getCanvas().setOnMousePressed(null);
    }
    
    public void enableAfterRuning(){
        gui.getClearAll().setDisable(false);
        gui.getClearPrev().setDisable(false);
        gui.getGiftWrapButton().setDisable(false);
        gui.getIncrementalButton().setDisable(false);
        gui.getReadFileButton().setDisable(false);
        gui.getGrahamButton().setDisable(false);
        gui.getCanvas().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                
                System.out.println("mouse click detected! "+event.getSceneX()+" "+event.getSceneY());
                cd.drawPoints(gui.getGc(),event.getX(),event.getY());
        }
    });
    }
    
    
}
