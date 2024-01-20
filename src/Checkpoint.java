import java.awt.*;
import java.awt.image.BufferedImage;

public class Checkpoint {

    private double x, y;
    private int width = 30, height = 60;

//    private
    private BufferedImage img;
    public double vx, vy, g, initY, shipG, shipLift;
    private String type;
    private String gamemode;
    public boolean reverse;
    private boolean upsideDown;
    // constructor
    public Checkpoint(double xx, double yy, double vx, double vy, double g, double initY, double shipG, double shipLift, String gamemode, boolean reverse) {
        this.x = xx;
        this.y = yy;
        this.vx = vx;
        this.vy = vy;
        this.g = g;
        this.shipG = shipG;
        this.initY = initY;
        this.shipLift = shipLift;
        this.gamemode = gamemode;
//        this.upsideDown = upsideDown;
        img = Util.loadBuffImage("assets/checkpoint/checkpoint.png");
        img = Util.resize(img, width, height);
        this.reverse = reverse;
    }




    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(img, (int)x + offsetX, (int)y + offsetY, null);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }
    public String getGamemode() {
        return gamemode;
    }

    public boolean getUpsideDown() {
        return upsideDown;
    }

    public String toString() {
        return "<Checkpoint: " + x + ", " + y + ", " + gamemode + ">";
    }


}