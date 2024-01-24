/*
    Checkpoint.java

    This is the class for the checkpoint objects. It stores all the values of the player when the user makes it. Used in practice mode
 */

import java.awt.*;
import java.awt.image.BufferedImage;

public class Checkpoint {

    private double x, y; // coordinates of the checkpoint
    private int width = 30, height = 60; // dimensions of the checkpoint to draw
    private BufferedImage img; // the image of the checkpoint
    public double vx, vy, g, initY, shipG, shipLift; // the velocities and gravity stored
    private String gamemode; // the gamemode stored
    public boolean reverse; // if player should be upside down

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
        img = Util.loadBuffImage("assets/checkpoint/checkpoint.png");
        img = Util.resize(img, width, height);
        this.reverse = reverse;
    }

    public void draw(Graphics g, int offsetX, int offsetY) { // draws the checkpoint
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