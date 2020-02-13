package net.thumbtack.school.figures.v2;

import net.thumbtack.school.iface.v2.Stretchable;

import java.util.Objects;

/**
 * Created by User on 29.03.2019.
 */
public class Rectangle extends Figure implements Stretchable{

    private Point leftTop, rightBottom;

    public Rectangle(Point leftTop, Point rightBottom){
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom){
        this(new Point(xLeft, yTop), new Point(xRight, yBottom));
    }

    public Rectangle(int length, int width){
        this(new Point(0, 0-width), new Point(length, 0));
    }

    public Rectangle(){
        this(new Point(0, -1), new Point(1, 0));
    }

    public Point getTopLeft(){
        return leftTop;
    }

    public Point getBottomRight(){
        return rightBottom;
    }

    public void setTopLeft(Point topLeft){
        leftTop = topLeft;
    }

    public void setBottomRight(Point bottomRight){
        rightBottom = bottomRight;
    }

    public int getLength(){
        return rightBottom.getX() - leftTop.getX();
    }

    public int getWidth(){
        return rightBottom.getY() - leftTop.getY();
    }

    @Override
    public void moveTo(int x, int y){
        rightBottom.moveTo(x+getLength(), y+getWidth());
        leftTop.moveTo(x,y);
    }

    @Override
    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx, dy);
        rightBottom.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio){
        rightBottom.setX((int) (leftTop.getX() + getLength()*ratio));
        rightBottom.setY((int) (leftTop.getY() + getWidth()*ratio));
    }

    @Override
    public void stretch(double xRatio, double yRatio){
        rightBottom.setX((int) (leftTop.getX() + getLength()*xRatio));
        rightBottom.setY((int) (leftTop.getY() + getWidth()*yRatio));
    }

    @Override
    public double getArea(){
        return getLength()*getWidth();
    }

    @Override
    public double getPerimeter(){
        return 2*(getLength() + getWidth());
    }

    @Override
    public boolean isInside(int x, int y){
        return (leftTop.getX()<= x && rightBottom.getX()>=x)&&
                (leftTop.getY()<=y && rightBottom.getY()>=y);
    }

    public boolean isIntersects(Rectangle rectangle){
        return !(rightBottom.getX()<rectangle.getTopLeft().getX()
                || leftTop.getY()>rectangle.getBottomRight().getX()
                || rightBottom.getY()<rectangle.getTopLeft().getY()
                || leftTop.getY()>rectangle.getBottomRight().getY());
    }

    public boolean isInside(Rectangle rectangle){
        return (leftTop.getX()<=rectangle.getTopLeft().getX()
                && rightBottom.getY()>=rectangle.getTopLeft().getX() && leftTop.getY()<=rectangle.getTopLeft().getY()
                && rightBottom.getY()>=rectangle.getTopLeft().getY())
                && (leftTop.getX()<=rectangle.getBottomRight().getX()
                && rightBottom.getX()>=rectangle.getBottomRight().getX()
                && leftTop.getY()<=rectangle.getBottomRight().getY()
                && rightBottom.getY()>=rectangle.getBottomRight().getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(leftTop, rectangle.leftTop) &&
                Objects.equals(rightBottom, rectangle.rightBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, rightBottom);
    }
}
