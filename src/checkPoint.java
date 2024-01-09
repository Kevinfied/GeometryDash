import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class checkPoint {

    private int x, y;
    private int width = 75, height = 75;
    private BufferedImage img;
    private String type;

    // constructor
    public checkPoint(int xx, int yy) {
        this.x = xx;
        this.y = yy;

        img = Util.loadBuffImage("assets/checkpoint/checkpoint.png");
        img = Util.resize(img, width, height);
    }




    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(img, x + offsetX, y + offsetY, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
