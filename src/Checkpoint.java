import java.awt.*;
import java.awt.image.BufferedImage;

public class Checkpoint {

    private double x, y;
    private int width = 30, height = 60;

//    private
    private BufferedImage img;
    private double vx, vy;
    private String type;
    private String gamemode;
    // constructor
    public Checkpoint(double xx, double yy, double vx, double vy, String gamemode) {
        this.x = xx;
        this.y = yy;
        this.vx = vx;
        this.vy = vy;
        this.gamemode = gamemode;
        img = Util.loadBuffImage("assets/checkpoint/checkpoint.png");
        img = Util.resize(img, width, height);
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

    public String toString() {
        return "<Checkpoint: " + x + ", " + y + ", " + gamemode + ">";
    }

}
