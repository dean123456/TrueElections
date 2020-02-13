package net.thumbtack.school.colors.v3;

/**
 * Created by User on 16.04.2019.
 */
public enum ColorErrorCode {
    WRONG_COLOR_STRING("Не верный цвет"),
    NULL_COLOR("Цвет не может быть null");

    private String errorString;

    ColorErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
