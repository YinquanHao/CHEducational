/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author YinQuan
 */
public class JarvisAlgo {
    private final Object monitor =null;
    private Stack<Point> res =  new Stack<Point>();
    private ArrayList<Point> rightMostList = new ArrayList<Point>();
    private ArrayList<Point> dominate =  new ArrayList<Point>();
    private ArrayList<Point> input;
    private GraphicsContext gc;
    private ObservableList<Point> points;
    private CanvasDraw cd;
    private Giftwrapping gw;
    private InitGui gui;
    String ADD_RIGHT_MOST_TO_CONVEX = "add right Most Point % to convex ";
    String ADD_TO_CONVEX = "add Point % to convex";
    String ADD_TO_DOMINATE = "add point % to dominate points set";
    String CHK_IF_DOMINATE = "check if point % is dominate point.";
    String CHK_IF_CCW = "check if triangle % % % is CCW";
    String IS_CCW = "Triangle % % % is CCW";
    String NOT_DOMINATE ="Point % is not a dominate point";
    String NOT_CCW ="Triangle % % % is not CCW";
    
    public JarvisAlgo(ArrayList<Point> input,GraphicsContext gc, ObservableList<Point> points, CanvasDraw cd, Giftwrapping gw, InitGui gui) throws InterruptedException{
        this.input = input;
        this.gc = gc;
        this.points = points;
        this.cd = cd;
        this.gw = gw;
        this.gui = gui;
//get the number of points in the point set

        int numberOfPoints = input.size();
        double maxY=0;
        //if there is fewer than three points return
        if(numberOfPoints<3){
            return;
        }
        //get the right most point
        Point rightMostPoint = input.get(0);
        for(Point point :input){
            if(point.getAxisX()>rightMostPoint.getAxisX()){
                rightMostList.clear();
                rightMostPoint = point;
                rightMostList.add(rightMostPoint);
            }
            else if(point.getAxisX()==rightMostPoint.getAxisX()){
                rightMostList.add(point);
            }
        }
        
        for(Point point : rightMostList){
            if(point.getAxisY()>rightMostPoint.getAxisY()){
                rightMostPoint = point;
            }
        }
        //get the right most point and push it to result stack
        int p = rightMostPoint.getIndex();
        res.push(input.get(p));
        maxY=rightMostPoint.getAxisY();
        dominate.add(rightMostPoint);
        System.out.println("is null" + gw);
        gw.getSteps().add(new Step(gw.StringMod(ADD_RIGHT_MOST_TO_CONVEX, Integer.toString(rightMostPoint.getIndex()))));
        cd.drawLineSet(gc, res);
        cd.drawDominatePoints(gc, dominate);
        
        
        gw.getSteps().add(new Step(gw.StringMod(ADD_TO_DOMINATE, Integer.toString(rightMostPoint.getIndex()))));
        
        int q;
        do
        {
            q = (p + 1) % numberOfPoints;
            System.out.println("now q = "+q);
            System.out.println(" p = "+p +"q = "+q);
            for (int i = 0; i < numberOfPoints; i++){
                System.out.println("naidnjsandknsajdnjasndnsadjnksadnjk"+gw.isPause);
                if(gw.isPause==true){
                    while(gw.isPause==true){
                        Thread.sleep(200);
                    }
                }
                
                String[] args = {Integer.toString(p),Integer.toString(i),Integer.toString(q)};
                gw.getSteps().add(new Step(gw.StringModMutiple(CHK_IF_CCW, args)));
                System.out.println("?? ccw p = "+p + " i = "+ i +" q = "+q+"? "+ CCW(input.get(p), input.get(i), input.get(q)) );
              if (CCW(input.get(p), input.get(i), input.get(q))){
                  gw.getSteps().add(new Step(gw.StringModMutiple(IS_CCW, args)));
                  res.push(input.get(q));
                  cd.drawTriangle(gc,input.get(p),input.get(i),input.get(q));
                  //cd.drawLineSet(gc, res);
                  cd.drawDominatePoints(gc, dominate);
                  //Thread.sleep((long)gui.getSlider().getValue());
                  res.pop();
                  cd.repaint(gc, points);
                  cd.drawLineSet(gc, res);
                  cd.drawDominatePoints(gc, dominate);
                  System.out.println("is ccw p = "+p + " i = "+ i +" q = "+q+"? "+ CCW(input.get(p), input.get(i), input.get(q)) );
                 q = i;
              }else{
                  cd.repaint(gc, points);
                  cd.drawLineSet(gc, res);
                  cd.drawDominatePoints(gc, dominate);
                  cd.drawTriangle(gc,input.get(p),input.get(i),input.get(q));
                  
                  //Thread.sleep((long)gui.getSlider().getValue());
                  gw.getSteps().add(new Step(gw.StringModMutiple(NOT_CCW, args)));
              }
              Thread.sleep((long)gui.getSlider().getValue());
            }
            System.out.println("q = "+q);
            if(!input.get(q).equals(rightMostPoint)){
            res.push(input.get(q));
            gw.getSteps().add(new Step(gw.StringMod(ADD_TO_CONVEX, Integer.toString(q))));
            gw.getSteps().add(new Step(gw.StringMod(CHK_IF_DOMINATE,Integer.toString(q))));
            maxY=isDominate(maxY,input.get(q),dominate,gw);
            }
            p = q; 
        } while (p != rightMostPoint.getIndex());
        cd.repaint(gc, points);
        cd.drawLineSet(gc, res);
        cd.drawHeadTailLine(gc, res);
        cd.drawDominatePoints(gc, dominate);
        gw.getSteps().add(new Step("Done"));
        gw.ie.enableAfterRuning();
        
    }
     private boolean CCW(Point p, Point q, Point r)
    {
        double val = (q.getAxisY() - p.getAxisY()) * (r.getAxisX() - q.getAxisX()) - (q.getAxisX() - p.getAxisX()) * (r.getAxisY() - q.getAxisY());
 
         if (val >= 0)
             return false;
         return true;
    }
     private double isDominate(double maxY, Point pt, ArrayList<Point> dominate,Giftwrapping gw){
         if(pt.getAxisY()>maxY){
             dominate.add(pt);
             gw.getSteps().add(new Step(gw.StringMod(ADD_TO_DOMINATE, Integer.toString(pt.getIndex()))));
             return pt.getAxisY();
         }
         else{
             gw.getSteps().add(new Step(gw.StringMod(NOT_DOMINATE, Integer.toString(pt.getIndex()))));
             return maxY;
         }
     }
     private void printDominates(ArrayList<Point> pts){
         for(Point pt:pts){
             System.out.println("Point "+pt.getIndex()+ " is dominate point.");
         }
     }

     
}

