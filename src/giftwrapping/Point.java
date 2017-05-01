      /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giftwrapping;

import java.util.Comparator;

/**
 *
 * @author YinQuan
 */
public class Point implements Comparable<Point>{
    public final Comparator<Point> XcordOrder = new XcordComparator();
    public final Comparator<Point> XcordOrderMax = new XcordComparatorMax();
    public final Comparator<Point> YcordOrder = new YcordComparator();
    public final Comparator<Point> PolarOrder = new PolarComparator();

    private int index;
    private double x;
    private double y;
    private double axisX;
    private double axisY;
    
    
    public Point(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.axisX = x-400;
        this.axisY = 300-y;
        
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getAxisX() {
        return this.axisX;
    }
    
    public double getAxisY() {
        return this.axisY;
    }
    
    public double theta()
    {
        return Math.atan2(axisX, axisY);
    }
    
    private double angleTo(Point dest)
    {
        double dx = dest.axisX - this.axisX;
        double dy = dest.axisY - this.axisY;
        return Math.atan2(dy, dx);
    }
    
     public static int isCcw(Point a, Point b, Point c)
    {
        double area = (b.axisX - a.axisX) * (c.axisY - a.axisY) - (b.axisY - a.axisY) * (c.axisX - a.axisX);
        if (area < 0)
            return -1;
        else if (area > 0)
            return 1;
        else
            return 0;
    }
      public int compareTo(Point dest)
    {
        if (this.axisY < dest.axisY)
            return -1;
        if (this.axisY > dest.axisY)
            return 1;
        if (this.axisX < dest.axisX)
            return -1;
        if (this.axisX > dest.axisX)
            return 1;
        return 0;
    }
     
    private static class XcordComparator implements Comparator<Point>
{
        public int compare(Point p, Point q)
            {
                if (p.getX() < q.getX()){
                    return -1;
                }
                if (p.getX() > q.getX()){
                    return 1;
                }
                return 0;
            }
}
    
    private static class XcordComparatorMax implements Comparator<Point>
{
        public int compare(Point p, Point q)
            {
                if (p.getAxisX() < q.getAxisX()){
                    return 1;
                }
                if (p.getAxisX() > q.getAxisX()){
                    return -1;
                }
                return 0;
            }
}
     
    private class YcordComparator implements Comparator<Point>{
        public int compare(Point p, Point q)
            {
                if (p.getAxisY() < q.getAxisY()){
                    return -1;
                }
                if (p.getAxisY() > q.getAxisY()){
                    return 1;
                }
                return 0;
            }
    }
    
    private class AngleComparator implements Comparator<Point>{
        public int compare(Point q1, Point q2){
                double angleToQ1 = angleTo(q1);
                double angleToQ2 = angleTo(q2);
            if (angleToQ1 < angleToQ2)
                return -1;
            else if (angleToQ1 > angleToQ2)
                return 1;
            else
                return 0;
        }
}
    private class PolarComparator implements Comparator<Point>
    {
        public int compare(Point q1, Point q2)
        {
            double dx1 = q1.axisX - axisX;
            double dy1 = q1.axisY - axisY;
            double dx2 = q2.axisX - axisX;
            double dy2 = q2.axisY - axisY;
 
            if (dy1 >= 0 && dy2 < 0)
                return -1; // q1 above; q2 below
            else if (dy2 >= 0 && dy1 < 0)
                return +1; // q1 below; q2 above
            else if (dy1 == 0 && dy2 == 0)
            { // 3-collinear and horizontal
                if (dx1 >= 0 && dx2 < 0)
                    return -1;
                else if (dx2 >= 0 && dx1 < 0)
                    return +1;
                else
                    return 0;
            } else
                return isCcw(Point.this, q1, q2)*-1; // both above or below
        }
    }

    
}
