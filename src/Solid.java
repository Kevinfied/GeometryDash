import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Solid {

    private int x, y;
    private int width, height;

    private String type;

    private Image icon;


    // constructor
    public Solid(int x, int y, String type) {

        this.x = x;
        this.y = y;
        this.type = type;

    }


    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);

    }




}