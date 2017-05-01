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
public class YcordComparator implements Comparator<Point>{
    public int compare(Point p, Point q)
        {
            if (p.getY() < q.getY())
                return -1;
            if (p.getY() > q.getY())
                return 1;
            return 0;
        }
}
