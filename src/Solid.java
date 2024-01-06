import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Solid {

    private int x, y;
    public int width;
    public int height;

//    private String type;

    private Image icon;
    private String type;


    // constructor
    public Solid(int x, int y, String type) {

        this.x = x;
        this.y = y;
//        this.type = type;
        this.type = type;

        if (type == "solid") {
            this.width = 75;
            this.height = 75;
        }
        else if (type == "slab") {
            this.width = 75;
            this.height = 37;
        }

    }



    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }


    public void draw(Graphics g, int offsetX, int offsetY) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        Rectangle d = getRect();
    //        Rectangle d = new Rectangle((int) -(x - offset), y, width, height);
        g.fillRect(x + offsetX, y + offsetY, width, height);
        g.setColor(new Color(0, 208, 255, 255) );
        g.drawRect(x + offsetX, y + offsetY, width, height);


        // if player is on tis solid, make it light blueee, for testing
        if(GamePanel.player.onSolid( this )) {
            g.setColor(new Color(0, 118, 102, 75) );
            g.fillRect(x + offsetX, y + offsetY, width, height);
        }
    //        d.translate(offset, 0);
    //        g2d.draw(d);

    }

    public double getX() { return x;}
    public double getY() { return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}



}