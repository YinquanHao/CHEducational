/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author YinQuan
 */
public class GrahmAlgo {
    private Stack<Point> res =  new Stack<Point>();
    private Stack<Double> localMaxY =  new Stack<Double>();
    private Stack<Point> dominate = new Stack<Point>();
    private double maxY = -300;
    private Point[] pointArray;
    String GET_RIGHT_MOST_POINT = "Get the right most point %";
    String SORT = "Sorting by angle, % is % element in sorted array";
    String CHK_IF_CCW = "check if triangle(% % %) is CCW";
    String NOT_CCW ="Triangle % % % is not CCW";
    String ADD_TO_CONVEX = "Temporary add point % to Convex set";
    String ADD_TO_CONVEX_TURE = "Add right most point % to Convex set";
    String POP = "Pop out the point % from our result stack";
    String ADD_TO_DOMINATE = "add point % to dominate points set";
    String CHK_IF_DOMINATE = "check if point % is dominate point.";
    String IS_CCW = "Triangle % % % is CCW";
    String NOT_DOMINATE ="Point % is not a dominate point";
    
    
    public GrahmAlgo(ArrayList<Point> input,GraphicsContext gc, ObservableList<Point> points, CanvasDraw cd, Giftwrapping gw, InitGui gui) throws InterruptedException{
        cd.repaint(gc, points);
        int numberOfPoints = input.size();
        //if there is no point return null
        if(numberOfPoints==0){
            gw.ie.enableAfterRuning();
            return;
        }
        pointArray = new Point[numberOfPoints];
        for(int i=0;i<numberOfPoints;i++){
            pointArray[i] = input.get(i);
        }
        Arrays.sort(pointArray,pointArray[0].XcordOrderMax);
        gw.getSteps().add(new Step(gw.StringMod(GET_RIGHT_MOST_POINT, Integer.toString(pointArray[0].getIndex()))));
        
        print();
        Arrays.sort(pointArray,1,numberOfPoints,pointArray[0].PolarOrder);
        System.out.println("");
        System.out.println("");
        print();
        
        res.push(pointArray[0]);
        localMaxY.push(isDominate(maxY, pointArray[0],dominate, gw));
        System.out.println("mgovmdgmvpeignmopienmrgomoeinmrgioemnrgomeroigmreoimgoremgo");
        for(int m=dominate.size()-1; m>=0;m--)  
                System.out.println(dominate.get(m).getIndex());
        System.out.println("oooooooooooooooooooooooooooooooooo");
        
        gw.getSteps().add(new Step(gw.StringMod(ADD_TO_CONVEX_TURE, Integer.toString(pointArray[0].getIndex()))));
        
        System.out.println("push"+ pointArray[0].getIndex());
        
        
        if(gw.isFinish==false){
            cd.highlightVertice(gc,pointArray[0]);
        }
        
        
        
        System.out.println("get the highest point");
        
        //if there is only one point return res with one pt
        if(numberOfPoints==1){
            gw.ie.enableAfterRuning();
            return;
        }
        
        for(int i=1;i<pointArray.length;i++){
            if(gw.isPause==true){
                gw.ie.diableWhilePause();
                    while(gw.isPause==true){
                        Thread.sleep(200);
                    }
                gw.ie.enableAfterPause();
                }
            if(gw.isFinish==false){
                Thread.sleep((long)gui.getSlider().getValue());
                cd.drawLine(gc,pointArray[0],pointArray[i]);
            }
            String [] args = {Integer.toString(pointArray[i].getIndex()),Integer.toString(i)};
            gw.getSteps().add(new Step(gw.StringModMutiple(SORT, args)));
        }
        
      

        int checkEqual;
        for(checkEqual =1;checkEqual<numberOfPoints;checkEqual++){
            if(pointArray[0].getX()!=pointArray[checkEqual].getX() || pointArray[0].getY()!=pointArray[checkEqual].getY())
                break;
        }
        if(checkEqual==numberOfPoints){
            return;
        }
        
        System.out.println("check equal"+ checkEqual);
        int k2;
        for (k2 = checkEqual + 1; k2 < numberOfPoints; k2++){
            String []args = {Integer.toString(pointArray[0].getIndex()),Integer.toString(pointArray[checkEqual].getIndex()),Integer.toString(pointArray[k2].getIndex())};
            //gw.getSteps().add(new Step(gw.StringModMutiple(CHK_IF_CCW, args)));
            if (Point.isCcw(pointArray[0], pointArray[checkEqual], pointArray[k2]) != 0)
                 //gw.getSteps().add(new Step(gw.StringModMutiple(NOT_CCW, args)));
                break;
        }
        System.out.println("k2"+ k2);
        
        res.push(pointArray[k2 - 1]); // pointArray[k2-1] is second extreme point
        localMaxY.push(isDominate(localMaxY.peek(), pointArray[k2 - 1],dominate, gw));
        System.out.println("mgovmdgmvpeignmopienmrgomoeinmrgioemnrgomeroigmreoimgoremgo");
        for(int m=dominate.size()-1; m>=0;m--)  
                System.out.println(dominate.get(m).getIndex());
        System.out.println("oooooooooooooooooooooooooooooooooo");
        
        
        
        gw.getSteps().add(new Step(gw.StringMod(ADD_TO_CONVEX, Integer.toString(pointArray[k2 - 1].getIndex()))));
        for(int m=res.size()-1; m>=0;m--)  
                System.out.println(res.get(m).getIndex());
            System.out.println("      ");
        System.out.println("push"+ pointArray[k2 - 1].getIndex());
        
        
        
        if(gw.isFinish==false){
        cd.highlightLine(gc,pointArray[0],pointArray[k2-1]);
        Thread.sleep((long)gui.getSlider().getValue());
        /*
        cd.repaint(gc,points);
        cd.highlightLine(gc,pointArray[0],pointArray[k2-1]);
*/
        cd.repaint(gc, points);
        System.out.println("res size before"+res.size());
        cd.drawLineSet(gc, res);
        cd.drawDominatePointsFromStack(gc, dominate);
        System.out.println("res size"+res.size());
        }
        
        
        
        for (int i = k2; i < numberOfPoints; i++)
        {
            if(gw.isPause==true){
                    gw.ie.diableWhilePause();
                    while(gw.isPause==true){
                        Thread.sleep(200);
                    }
                gw.ie.enableAfterPause();
                }
            Point top = res.pop();
            double popedMaxY= localMaxY.pop();
            if(top.equals(dominate.peek())){
                   dominate.pop();
                }
            
            System.out.println("mgovmdgmvpeignmopienmrgomoeinmrgioemnrgomeroigmreoimgoremgo");
            for(int m=dominate.size()-1; m>=0;m--)  
                System.out.println(dominate.get(m).getIndex());
            System.out.println("oooooooooooooooooooooooooooooooooo");
            
            System.out.println("pop"+ top.getIndex());
            gw.getSteps().add(new Step(gw.StringMod(POP, Integer.toString(top.getIndex()))));
            int whileloopCounter=0;
            while (Point.isCcw(res.peek(), top, pointArray[i]) <= 0)
            {
                if(gw.isPause==true){
                    gw.ie.diableWhilePause();
                    while(gw.isPause==true){
                        Thread.sleep(200);
                    }
                gw.ie.enableAfterPause();
                }
                String [] args = {Integer.toString(res.peek().getIndex()),Integer.toString(top.getIndex()),Integer.toString(pointArray[i].getIndex())};
                gw.getSteps().add(new Step(gw.StringModMutiple(CHK_IF_CCW, args)));
                gw.getSteps().add(new Step(gw.StringModMutiple(NOT_CCW, args)));
                top = res.pop();
                
                
                popedMaxY= localMaxY.pop();
                if(top.equals(dominate.peek())){
                   dominate.pop();
                }
                System.out.println("mgovmdgmvpeignmopienmrgomoeinmrgioemnrgomeroigmreoimgoremgo");
                for(int m=dominate.size()-1; m>=0;m--)  
                    System.out.println(dominate.get(m).getIndex());
                System.out.println("oooooooooooooooooooooooooooooooooo");
                
                
                gw.getSteps().add(new Step(gw.StringMod(POP, Integer.toString(top.getIndex()))));
                System.out.println("pop"+ top.getIndex());
                
                if(gw.isFinish==false){
                cd.highlightVertice(gc,top);
                Thread.sleep((long)gui.getSlider().getValue());
                }
                whileloopCounter++;
            }
            String [] args = {Integer.toString(res.peek().getIndex()),Integer.toString(top.getIndex()),Integer.toString(pointArray[i].getIndex())};
            gw.getSteps().add(new Step(gw.StringModMutiple(CHK_IF_CCW, args)));
            gw.getSteps().add(new Step(gw.StringModMutiple(IS_CCW, args)));
            System.out.println("push a"+ top.getIndex());
            res.push(top);
            
            //dominate.push(top);
            localMaxY.push(isDominate(localMaxY.peek(), top,dominate, gw));
            
            gw.getSteps().add(new Step(gw.StringMod(ADD_TO_CONVEX, Integer.toString(top.getIndex()))));
            if(whileloopCounter>0){
            for(int m=res.size()-1; m>=0;m--){ 
                        System.out.println(res.get(m).getIndex());
                        System.out.println("v      ");
                    }
        if(gw.isFinish==false){
        cd.repaint(gc, points);
        System.out.println("res size before"+res.size());
        cd.drawLineSet(gc, res);
        cd.drawDominatePointsFromStack(gc, dominate);
        }
        System.out.println("res size"+res.size());
        
            }
            whileloopCounter=0;
            
            System.out.println("push"+ pointArray[i].getIndex());
            res.push(pointArray[i]);
            
            //dominate.push(top);
            localMaxY.push(isDominate(localMaxY.peek(), pointArray[i],dominate, gw));
            
            gw.getSteps().add(new Step(gw.StringMod(ADD_TO_CONVEX, Integer.toString( pointArray[i].getIndex()))));
            //cd.highlightLine(gc,top,pointArray[i]);
            
            
            if(gw.isFinish==false){
            Thread.sleep((long)gui.getSlider().getValue());
            
            for(int m=res.size()-1; m>=0;m--)  
                System.out.println(res.get(m).getIndex());
            System.out.println("      ");
            
            
            cd.repaint(gc, points);
            System.out.println("res size before"+res.size());
            cd.drawLineSet(gc, res);
            cd.drawDominatePointsFromStack(gc, dominate);
            }
            System.out.println("res size"+res.size());
        
        }
        
        for(int i=res.size()-1; i>=0;i--)  
            System.out.println(res.get(i).getIndex());
            
            cd.repaint(gc, points);
            System.out.println("res size before"+res.size());
            cd.drawLineSet(gc, res);
            System.out.println("res size"+res.size());
            Thread.sleep((long)gui.getSlider().getValue());
            cd.drawHeadTailLine(gc, res);
            cd.drawDominatePointsFromStack(gc, dominate);
            for(int i=dominate.size()-1; i>=0;i--)  
                System.out.println(dominate.get(i).getIndex());
            
        gw.ie.enableAfterRuning();
    }
    
    public void print(){
        for(int i=0;i<pointArray.length;i++){ 
            System.out.println(pointArray[i].getIndex()+" ,");
        }
    }
     private double isDominate(double maxY, Point pt, Stack<Point> dominate,Giftwrapping gw){
         System.out.println("sfsdfmsdmfkmsdofmoksdfmo"+maxY);
         if(pt.getAxisY()>maxY){
             dominate.push(pt);
             gw.getSteps().add(new Step(gw.StringMod(ADD_TO_DOMINATE, Integer.toString(pt.getIndex()))));
             return pt.getAxisY();
         }
         else{
             gw.getSteps().add(new Step(gw.StringMod(NOT_DOMINATE, Integer.toString(pt.getIndex()))));
             return maxY;
         }
     }
    
   
    
    
    
    
}
