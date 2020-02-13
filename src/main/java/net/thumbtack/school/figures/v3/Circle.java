package net.thumbtack.school.figures.v3;

import java.util.Objects;

/**
 * Created by User on 31.03.2019.
 */
public class Circle extends Figure {

    private Point center;
    private int radius;

    public Circle(Point center, int radius){
        this.center = center;
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius){
        this(new Point(xCenter, yCenter), radius);
    }

    public Circle(int radius){
        this(new Point(), radius);
    }

    public Circle(){
        this(new Point(), 1);
    }

    public Point getCenter(){
        return center;
    }

    public int getRadius(){
        return radius;
    }

    public void setCenter(Point center){
        this.center = center;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    @Override
    public void moveTo(int x, int y){
        center.moveTo(x, y);
    }

    @Override
    public void moveRel(int dx, int dy){
        center.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio){
        radius *= ratio;
    }

    @Override
    public double getArea(){
        return Math.PI*Math.pow(radius, 2);
    }

    @Override
    public double getPerimeter(){
        return 2*Math.PI*radius;
    }

    @Override
    public boolean isInside(int x, int y){
        return (Math.pow((x-getCenter().getX()), 2) + Math.pow((y-getCenter().getY()), 2) <= Math.pow(radius, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return radius == circle.radius &&
                Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }
}
