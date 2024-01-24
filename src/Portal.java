// Portal.java
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
// game object that interact with player by changing gamemode(ship, cube) and or reverse its direction - Daisy
public class Portal {

    // coordinate and dimensions
    private int x, y;
    public static int width = 75;
    public static int height = 225;
    private static int gravity; // 0 no switch, 1 upside down, 2 up

    //type: to ship, to cube, or reverse y vector
    private String type;

    //sprites
    private BufferedImage cubePortalIcon;
    private BufferedImage shipPortalIcon;
    private BufferedImage portalIcon;

    // constructor
    public Portal(int x, int y, String type, int gravity) {

        this.x = x;
        this.y = y;
        this.type = type;
        this.gravity = gravity;

        //assign portal image accorddingly the reseize it
        if (type == "cube"){
            portalIcon = Util.loadBuffImage("assets/portals/cubePortal.png");
        }
        else if (type == "ship" ||  type == "reverse" || type == "upright") {
            portalIcon = Util.loadBuffImage("assets/portals/shipPortal.png");
        }
        portalIcon = Util.resize(portalIcon, width, height);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void draw( Graphics g, int offsetX, int offsetY) { //draws the portal
        g.drawImage(portalIcon, x + offsetX, y + offsetY, null);
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

    public int getGravity() {
        return gravity;
    }

    public void setType(String type) {
        this.type = type;
    }
}
