package net.thumbtack.school.figures.v2;

import net.thumbtack.school.iface.v2.HasArea;
import net.thumbtack.school.iface.v2.Movable;
import net.thumbtack.school.iface.v2.Resizable;

/**
 * Created by User on 12.04.2019.
 */
public abstract class Figure implements Movable, Resizable, HasArea{

    abstract public double getPerimeter();
    abstract public boolean isInside(int x, int y);
    public boolean isInside(Point point) {
        return isInside(point.getX(), point.getY());
    }
}
