import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Orb {

    private int x, y;
    public static int width = 75, height = 75;
    private String type;
    private BufferedImage img;

    public Orb(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public Rectangle getHitbox() {

        return new Rectangle(x -10, y -10, width+20, height+20);
    }

    public void drawHitbox(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.GREEN);
        g.drawRect(x + offsetX -10, y + offsetY -10, width + 20, height+ 20);
        g.drawRect(x+offsetX, y + offsetY, width, height);
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
//        g.drawImage(img, x + offsetX, y + offsetY, null);
        drawHitbox(g, offsetX, offsetY);
    }

}
