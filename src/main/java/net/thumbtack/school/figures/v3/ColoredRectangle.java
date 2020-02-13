package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorErrorCode;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.iface.v3.Colored;

import java.util.Objects;

/**
 * Created by User on 09.04.2019.
 */
public class ColoredRectangle extends Rectangle implements Colored{

    private Color color;

    public ColoredRectangle(Point leftTop, Point rightBottom, Color color) throws ColorException{
        super(leftTop, rightBottom);
        setColor(color);
    }

    public ColoredRectangle(Point leftTop, Point rightBottom, String color) throws ColorException{
        this(leftTop, rightBottom, Color.colorFromString(color));
    }

    public ColoredRectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException{
        this(new Point(xLeft, yTop), new Point(xRight, yBottom), color);
    }

    public ColoredRectangle(int xLeft, int yTop, int xRight, int yBottom, String color) throws ColorException{
        this(new Point(xLeft, yTop), new Point(xRight, yBottom), color);
    }

    public ColoredRectangle(int length, int width, Color color) throws ColorException{
        super(length, width);
        setColor(color);
    }

    public ColoredRectangle(int length, int width, String color) throws  ColorException{
        this(length, width, Color.colorFromString(color));
    }

    public ColoredRectangle(Color color) throws ColorException{
        super();
        setColor(color);
    }

    public ColoredRectangle(String color) throws ColorException{
        this(Color.colorFromString(color));
    }

    public ColoredRectangle() throws ColorException{
        this(Color.GREEN);
    }

    @Override
    public Color getColor(){
        return color;
    }

    @Override
    public void setColor(Color color) throws ColorException{
        if(color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
        this.color = color;
    }

    @Override
    public void setColor(String colorString) throws ColorException {
        color = Color.colorFromString(colorString);
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
