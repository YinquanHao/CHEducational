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
public class XcordComparator implements Comparator<Point>
{
     public int compare(Point p, Point q)
        {
            if (p.getX() < q.getX())
                return -1;
            if (p.getX() > q.getX())
                return 1;
            return 0;
        }
}
