/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

import java.util.ArrayList;
import java.util.Stack;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author YinQuan
 */
public class CanvasDraw {
    Giftwrapping gw;

    public CanvasDraw(Giftwrapping gw) {
        this.gw = gw;
        
    }
    public void repaint(GraphicsContext gc, ObservableList<Point> points){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        for(Point temp : points){
            gc.fillText(Integer.toString(temp.getIndex()),temp.getX()-5,temp.getY()-5);
            gc.strokeOval(temp.getX(), temp.getY(), 5, 5);
        }
    }
    
    public void drawLineSet(GraphicsContext gc, Stack<Point> st){
        Stack<Point> copiedStack = new Stack<Point>();
        copiedStack.addAll(st);
        if(copiedStack.size()>=2){
            Point sourcePt = copiedStack.pop();
            Point targetPt = copiedStack.pop();
            highlightLine(gc, sourcePt, targetPt);
            while(copiedStack.empty()==false){
                highlightLine(gc, sourcePt, targetPt);
                sourcePt = targetPt;
                targetPt = copiedStack.pop();
            }
            highlightLine(gc, sourcePt, targetPt);
        }else if(copiedStack.size()==1){
            highlightVertice(gc,copiedStack.peek());
        }else{
            
        }
        
    }
    
    public void drawHeadTailLine(GraphicsContext gc, Stack<Point> st){
        System.out.println("enter this");
        Stack<Point> copiedStack = new Stack<Point>();
        copiedStack.addAll(st);
        if(copiedStack.size()>=2){
            copiedStack.addAll(st);
            Point head = copiedStack.pop();
            while(copiedStack.size()>1){
                copiedStack.pop();
            }
            Point tail = copiedStack.pop();
            System.out.println("head"+head.getIndex());
            System.out.println("tail"+tail.getIndex());
            highlightLine(gc,head,tail);
        }
        
    
    }
    
    public void drawPoints(GraphicsContext gc,double x, double y){
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeOval(x,y,5,5);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        Point newpoint  = new Point(gw.pointCounter,x,y);
        gc.fillText(Integer.toString(newpoint.getIndex()),x-5,y-5);
        gw.getPoints().add(newpoint);
        gw.getActualPoints().add(newpoint);
        gw.pointCounter++;
        System.out.println("pointCounter"+ gw.pointCounter);
    }
    public void highlightVertice(GraphicsContext gc, Point pt){
        gc.setFill(Color.YELLOW);
        gc.fillOval(pt.getX()-2.5, pt.getY()-2.5, 10, 10);
        gc.setFill(Color.GREEN);
    }
    
    public void drawLine(GraphicsContext gc, Point from, Point to){
        gc.setLineWidth(1);
        gc.strokeLine(from.getX()+2.5, from.getY()+2.5, to.getX()+2.5, to.getY()+2.5); 
        gc.setLineWidth(5);
    }
    
   public void drawTriangle(GraphicsContext gc, Point a, Point b,Point c){
        drawLine(gc,a,b);
        drawLine(gc,b,c);
        drawLine(gc,c,a);
   }
    
    public void highlightLine(GraphicsContext gc, Point from, Point to){
        gc.setStroke(Color.RED);
        gc.strokeLine(from.getX()+2.5, from.getY()+2.5, to.getX()+2.5, to.getY()+2.5);
        gc.setStroke(Color.BLUE);
    } 
    
    public void drawDominatePoints(GraphicsContext gc, ArrayList<Point> pts){
        gc.setFill(Color.CADETBLUE);
        for(Point pt:pts){
            gc.fillOval(pt.getX()-2.5, pt.getY()-2.5, 10, 10);
        }
        gc.setFill(Color.GREEN);
        
    }
    
    public void drawDominatePointsFromStack(GraphicsContext gc, Stack<Point> pts){
        gc.setFill(Color.CADETBLUE);
        for(Point pt:pts){
            gc.fillOval(pt.getX()-2.5, pt.getY()-2.5, 10, 10);
        }
        gc.setFill(Color.GREEN);
        
    }
    
}
