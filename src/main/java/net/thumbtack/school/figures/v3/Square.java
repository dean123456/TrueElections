package net.thumbtack.school.figures.v3;

import java.util.Objects;

/**
 * Created by User on 31.03.2019.
 */
public class Square extends Figure {

    private Point leftTop, rightBottom;
    private int size;

    public Square(Point leftTop, int size){
        this.leftTop = leftTop;
        this.size = size;
        rightBottom = new Point(leftTop.getX()+size, leftTop.getY()+size);
    }

    public Square(int xLeft, int yTop, int size){
        this(new Point(xLeft, yTop), size);
    }

    public Square(int size){
        this(new Point(0, 0-size), size);
    }

    public Square(){
        this(new Point(0, -1), 1);
    }

    public Point getTopLeft(){
        return leftTop;
    }

    public Point getBottomRight(){
        return rightBottom;
    }

    public void setTopLeft(Point topLeft){
        leftTop = topLeft;
        rightBottom.setX(topLeft.getX() + size);
        rightBottom.setY(topLeft.getY() + size);
    }

    public int getLength(){
        return size;
    }

    @Override
    public void moveTo(int x, int y){
        leftTop.moveTo(x, y);
        rightBottom.moveTo(leftTop.getX()+size, leftTop.getY()+size);
    }

    @Override
    public void moveRel(int dx, int dy){
        leftTop.moveRel(dx, dy);
        rightBottom.moveRel(dx, dy);
    }

    @Override
    public void resize(double ratio){
        size *= ratio;
        rightBottom.setX(leftTop.getX() + size);
        rightBottom.setY(leftTop.getY() + size);
    }

    @Override
    public double getArea(){
        return size * size;
    }

    @Override
    public double getPerimeter(){
        return 2*(size + size);
    }

    @Override
    public boolean isInside(int x, int y){
        return (leftTop.getX()<=x && rightBottom.getX()>=x)
                &&(leftTop.getY()<=y && rightBottom.getY()>=y);
    }

    public boolean isIntersects(Square square){
        return !(rightBottom.getX()<square.getTopLeft().getX()
                || leftTop.getX()>square.getBottomRight().getX()
                || rightBottom.getY()<square.getTopLeft().getY()
                || leftTop.getY()>square.getBottomRight().getY());
    }

    public boolean isInside(Square Square){
        return (leftTop.getX()<=Square.getTopLeft().getX()
                && rightBottom.getX()>=Square.getTopLeft().getX()
                && leftTop.getY()<=Square.getTopLeft().getY()
                && rightBottom.getY()>=Square.getTopLeft().getY())&&
                (leftTop.getX()<=Square.getBottomRight().getX()
                && rightBottom.getX()>=Square.getBottomRight().getX()
                && leftTop.getY()<=Square.getBottomRight().getY()
                && rightBottom.getY()>=Square.getBottomRight().getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return size == square.size &&
                Objects.equals(leftTop, square.leftTop) &&
                Objects.equals(rightBottom, square.rightBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, rightBottom, size);
    }
}
