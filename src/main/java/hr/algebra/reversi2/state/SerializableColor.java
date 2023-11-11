package hr.algebra.reversi2.state;

import javafx.scene.paint.Color;

import java.io.Serializable;

//https://stackoverflow.com/questions/36748358/saving-color-as-state-in-a-javafx-application

public class SerializableColor implements Serializable {
    private final double red;
    private final double green;
    private final double blue;
    private final double opacity;

    public SerializableColor(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }

    public Color toColor() {
        return new Color(red, green, blue, opacity);
    }

    public static SerializableColor fromColor(Color color) {
        if (color == null) {
            return null;
        }
        return new SerializableColor(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
    }
}
