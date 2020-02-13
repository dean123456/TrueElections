package net.thumbtack.school.iface.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;

/**
 * Created by User on 12.04.2019.
 */
public interface Colored {

    void setColor(Color color) throws ColorException;
    Color getColor();
    void setColor(String colorString) throws ColorException;
}
