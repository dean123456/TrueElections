package net.thumbtack.school.figures.v1;

import java.util.Objects;

/**
 * Created by User on 31.03.2019.
 */
public class Ellipse {

    private Point center;
    private int xAxis;
    private int yAxis;

    public Ellipse(Point center, int xAxis, int yAxis){
        this.center = center;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public Ellipse(int xCenter, int yCenter, int xAxis, int yAxis){
        this(new Point(xCenter, yCenter), xAxis, yAxis);
    }

    public Ellipse(int xAxis, int yAxis){
        this(new Point(), xAxis, yAxis);
    }

    public Ellipse(){
        this(new Point(), 1, 1);
    }

    public Point getCenter(){
        return center;
    }

    public int getXAxis(){
        return xAxis;
    }

    public int getYAxis(){
        return yAxis;
    }

    public void setXAxis(int xAxis){
        this.xAxis = xAxis;
    }

    public void setYAxis(int yAxis){
        this.yAxis = yAxis;
    }

    public void setCenter(Point center){
        this.center = center;
    }

    public void moveTo(int x, int y){
        center.moveTo(x, y);
    }

    public void moveTo(Point point){
        center.moveTo(point.getX(), point.getY());
    }

    public void moveRel(int dx, int dy){
        center.moveRel(dx, dy);
    }

    public void resize(double ratio){
        xAxis *= ratio;
        yAxis *= ratio;
    }

    public void stretch(double xRatio, double yRatio){
        xAxis *= xRatio;
        yAxis *= yRatio;
    }

    public double getArea(){
        return Math.PI*(xAxis/2)*(yAxis/2);
    }

    public double getPerimeter(){
        return 2*Math.PI*Math.sqrt((Math.pow(xAxis, 2) + Math.pow(yAxis, 2))/8);
    }

    public boolean isInside(int x, int y){
        return ((Math.pow((x-getCenter().getX()), 2)/Math.pow(xAxis/2, 2)
                + Math.pow((y-getCenter().getY()), 2)/Math.pow(yAxis/2, 2) <= 1))?true:false;
    }

    public boolean isInside(Point point){
        return isInside(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipse ellipse = (Ellipse) o;
        return xAxis == ellipse.xAxis &&
                yAxis == ellipse.yAxis &&
                Objects.equals(center, ellipse.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, xAxis, yAxis);
    }
}
