package net.thumbtack.school.figures.v3;

import net.thumbtack.school.iface.v3.Stretchable;

import java.util.Objects;

/**
 * Created by User on 31.03.2019.
 */
public class Ellipse extends Figure implements Stretchable{

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
        xAxis *= ratio;
        yAxis *= ratio;
    }

    @Override
    public void stretch(double xRatio, double yRatio){
        xAxis *= xRatio;
        yAxis *= yRatio;
    }

    @Override
    public double getArea(){
        return Math.PI*(xAxis/2)*(yAxis/2);
    }

    @Override
    public double getPerimeter(){
        return 2*Math.PI*Math.sqrt((Math.pow(xAxis, 2) + Math.pow(yAxis, 2))/8);
    }

    @Override
    public boolean isInside(int x, int y){
        return ((Math.pow((x-getCenter().getX()), 2)/Math.pow(xAxis/2, 2)
                + Math.pow((y-getCenter().getY()), 2)/Math.pow(yAxis/2, 2) <= 1));
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
