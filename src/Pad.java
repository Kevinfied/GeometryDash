/*
    Pad.class

    This class is for the pad objects. Boosts the player upon contact
 */

import java.awt.*;

public class Pad {
    //coordinate and dimensions
    private double x,y, width, height;

    public Pad(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    public Rectangle getRect() {
        return new Rectangle( (int) x, (int) y, (int)height, (int)width);
    }


    // for testing only
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.BLACK);
        g.setColor(new Color(0, 208, 255, 255));
//        g.drawRect((int)x + offsetX, (int)y + offsetY, (int)width, (int)height);
    }


    public double getY() { return y;}
    public double getWidth() {return width;}
    public double getX() {return x;}
    public double getHeight() {return height;}



}
