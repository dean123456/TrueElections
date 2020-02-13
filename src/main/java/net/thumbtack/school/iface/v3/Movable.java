package net.thumbtack.school.iface.v3;

import net.thumbtack.school.figures.v3.Point;

/**
 * Created by User on 12.04.2019.
 */
public interface Movable {
    void moveTo(int x, int y);
    default void moveTo(Point point){
        moveTo(point.getX(), point.getY());
    };
    void moveRel(int dx, int dy);
}
