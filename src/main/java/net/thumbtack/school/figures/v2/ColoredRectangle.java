package net.thumbtack.school.figures.v2;

import net.thumbtack.school.iface.v2.Colored;

import java.util.Objects;

/**
 * Created by User on 09.04.2019.
 */
public class ColoredRectangle extends Rectangle implements Colored{
    private int color;

    public ColoredRectangle(Point leftTop, Point rightBottom, int color) {
        super(leftTop, rightBottom);
        this.color = color;
    }

    public ColoredRectangle(int xLeft, int yTop, int xRight, int yBottom, int color){
        super(xLeft, yTop, xRight, yBottom);
        this.color = color;
    }

    public ColoredRectangle(int length, int width, int color){
        super(length, width);
        this.color = color;
    }

    public ColoredRectangle(int color){
        super();
        this.color = color;
    }

    public ColoredRectangle(){
        super();
        color = 1;
    }

    @Override
    public int getColor(){
        return color;
    }

    @Override
    public void setColor(int color){
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ColoredRectangle that = (ColoredRectangle) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
