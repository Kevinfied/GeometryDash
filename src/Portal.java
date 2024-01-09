import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class Portal {

    private int x, y;
    public static int width = 75;
    public static int height = 225;

    private String type;
    private BufferedImage cubePortalIcon;
    private BufferedImage shipPortalIcon;
    private BufferedImage portalIcon;

    // constructor
    public Portal(int x, int y, String type) {

        this.x = x;
        this.y = y;
        this.type = type;

//        cubePortalIcon = Util.resize(Util.loadBuffImage("assets/portal/cube.png"), width, height);
//        shipPortalIcon = Util.resize(Util.loadBuffImage("assets/portal/ship.png"), width, height);
        System.out.println("assets/portals/" + type + ".png");
//        portalIcon = Util.resize(Util.loadBuffImage("assets/portal/" + type + ".png"), width, height);
        portalIcon = Util.loadBuffImage("assets/portals/ship.png");
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void draw( Graphics g, int offsetX, int offsetY) {
//        if (type == "ship") {
////            g.setColor(Color.YELLOW);
//            g.drawImage(shipPortalIcon, x + offsetX, y + offsetY, null);
//        }
//
//        else if (type == "cube") {
////            g.setColor(Color.GREEN);
//            g.drawImage(cubePortalIcon, x + offsetX, y + offsetY, null);
//        }
//        portalIcon = Util.resize(Util.loadBuffImage("assets/portal/" + type + ".png"), width, height);
        g.drawImage(portalIcon, x + offsetX, y + offsetY, null);
//        g.fillRect(x + offsetX, y + offsetY, width, height);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
