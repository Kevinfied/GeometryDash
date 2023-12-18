import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// hazards kill the player on contact - Kevin
public class Hazard {

    private int x, y;
    private int ox, oy;
    private int width, height;

    private String type;
    private Image icon;

    // constructor
    public Hazard(int xx, int yy, String type) {

        this.ox = xx;
        this.oy = yy;
        this.type = type;

        this.x = xx;
        this.y = yy;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);

    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}