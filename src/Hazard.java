import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// hazards kill the player on contact - Kevin
public class Hazard {

    private int x, y;
    private int width, height;

    private String type;

    // constructor
    public Hazard(int x, int y, String type) {

        this.x = x;
        this.y = y;
        this.type = type;

    }
}