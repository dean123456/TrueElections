package net.thumbtack.school.colors.v3;

/**
 * Created by User on 16.04.2019.
 */
public enum Color {

    RED("RED"), GREEN("GREEN"), BLUE("BLUE");

    String color;

    Color(String color) {
        this.color = color;
    }

    public static Color colorFromString(String colorString) throws ColorException {

        try {
            return Color.valueOf(colorString);
        } catch (IllegalArgumentException ex) {
            throw new ColorException(ColorErrorCode.WRONG_COLOR_STRING);
        } catch (NullPointerException ex) {
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        }
    }

    public String getColor() {
        return color;
    }
}
