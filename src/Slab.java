import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;




public class Slab {

    private int x, y;
    public static int width = 75;
    public static int height = 37;

    private BufferedImage img;

    // constructor
    public Slab(int x, int y) {

        this.x = x;
        this.y = y;

    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
//        g.drawImage(img, x, y, null);
        g.setColor(Color.GREEN);
        g.fillRect(x + offsetX, y + offsetY, width, height);
        g.setColor(Color.RED);
        g.drawRect(x + offsetX, y + offsetY, width, height);


        // if player is on tis solid, make it light blueee, for testing
        if(GamePanel.player.onSlab( this )) {
            g.setColor(new Color(38, 238, 221, 145) );
            g.fillRect(x + offsetX, y + offsetY, width, height);
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



}