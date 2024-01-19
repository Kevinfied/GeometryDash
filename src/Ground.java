
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Ground {

    BufferedImage img = Util.loadBuffImage("assets/ground/ground1.png" );

    int x, y;
    int width = 250;
    int height = 250;

    private int blue = 255;
    private int red = 100;
    private int green = 1;
    int counter = 0;
    public Ground(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        int xx = x + offsetX;
        int yy = y + offsetY;
        g.drawImage(img, xx, yy, width, height, null);

        g.setColor(new Color(red, green, blue, 77));
        g.fillRect(xx, yy-5, width, height+5);
        if ( counter % 100 == 0) {
            red = (int) (0.97 * red);
            blue = (int) (0.97 * blue);
            green = (int) (1.02 * green);
            if (green > 255) {
                green = 255;
            }
            counter = 1;
        }
        counter += 1;
    }

    public void move(int vx) {
        x -= vx;
    }

}