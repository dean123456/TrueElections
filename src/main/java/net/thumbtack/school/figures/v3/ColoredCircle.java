package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorErrorCode;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.iface.v3.Colored;

import java.util.Objects;

/**
 * Created by User on 09.04.2019.
 */
public class ColoredCircle extends Circle implements Colored {

    private Color color;

    public ColoredCircle(Point center, int radius, Color color) throws ColorException {
        super(center, radius);
        setColor(color);
    }

    public ColoredCircle(Point center, int radius, String color) throws ColorException {
        this(center, radius, Color.colorFromString(color));
    }

    public ColoredCircle(int xCenter, int yCenter, int radius, Color color) throws ColorException {
        this(new Point(xCenter, yCenter), radius, color);
    }

    public ColoredCircle(int xCenter, int yCenter, int radius, String color) throws ColorException {
        this(new Point(xCenter, yCenter), radius, color);
    }


    public ColoredCircle(int radius, Color color) throws ColorException {
        super(radius);
        setColor(color);
    }

    public ColoredCircle(int radius, String color) throws ColorException {
        this(radius, Color.colorFromString(color));
    }

    public ColoredCircle(Color color) throws ColorException {
        super();
        setColor(color);
    }

    public ColoredCircle(String color) throws ColorException {
        this(Color.colorFromString(color));
    }

    public ColoredCircle() throws ColorException {
        this(Color.RED);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) throws ColorException {
        if (color == null) throw new ColorException(ColorErrorCode.NULL_COLOR);
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
        ColoredCircle that = (ColoredCircle) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}
