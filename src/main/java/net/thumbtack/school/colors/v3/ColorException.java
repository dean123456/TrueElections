package net.thumbtack.school.colors.v3;

/**
 * Created by User on 16.04.2019.
 */
public class ColorException extends Exception {

    ColorErrorCode colorErrorCode;

    public ColorException(ColorErrorCode colorErrorCode) {
        super(colorErrorCode.getErrorString());
        this.colorErrorCode = colorErrorCode;
    }

    public ColorErrorCode getErrorCode() {
        return colorErrorCode;
    }
}
