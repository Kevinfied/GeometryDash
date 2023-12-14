import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;


public class Player{

//    private int x, y;
    private double x, y;
    private int width, height; // hitbox
    private int width2, height2; // sprite
    private double g = 2;
    private double vy =0;

//    private double rotation = Math.toRadians(45);
    double locX = width/2;
    double locY = height/2;
//    AffineTransform tx = AffineTransform.getRotateInstance(rotation, locX, locY);

    private int floor=325;

    private int vx;
    private double rotation;

    private String gamemode;

    private boolean inAir;

    private Image icon;

    private Point[] pointsOutline;

    // point a = (a + 2cos((pi/4)-R), b + 2sin((pi/4)-R))
    // point b = (a + 2cos(-(pi/4)-R), b + 2sin(-(pi/4)-R))
    // point c = (a - 2cos((pi/4)-R), b - 2sin((pi/4)-R))
    // point d = (a - 2cos(-(pi/4)-R), b - 2sin(-(pi/4)-R))

    private Point outlinePA, outlinePB, outlinePC, outlinePD;


    public Player(double x, double y, int width, int height) {
        this.gamemode = "cube";
        this.y=y;
        this.x = x;
        this.width  = width;
        this.height = height;
        this.inAir = false;

        this.icon = new ImageIcon("assets/icons/Cube001.png").getImage();
    }

    public void move() {
            y += vy;
            vy += g;
            fallCheck();

            if( y < floor) {
                x += 5;
            }
    }
    public void fallCheck() {
        if ( y > floor ) {
            y = floor;
            vy = 0;
        }
    }


    public void thrust() {
        if( y == floor) {
            vy = -30;
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public Rectangle getSpriteBound() {
        return new Rectangle((int) x, (int) y, width2, height2);
    }

    public Polygon getOutline() {
        return new Polygon(new int[] {(int) outlinePA.getX(), (int) outlinePB.getX(), (int) outlinePC.getX(), (int) outlinePD.getX()},
                new int[] {(int) outlinePA.getY(), (int) outlinePB.getY(), (int) outlinePC.getY(), (int) outlinePD.getY()}, 4);
    }


    public void draw(Graphics g) {

        g.drawImage(icon, (int)x-width/2, (int)y-height/2, width, height, null);

        drawHitbox(g);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) x - (height/2), (int) y - (height/2), width, height);
    }




//    public void drawSpriteBound(Graphics g) {
//        g.setColor(Color.GREEN);
//        g.drawRect((int) pos.getX(), (int) pos.getY(), width2, height2);
//    }

    public void setX(int x) {
        this.x = x;
    }


    public void setY(int y) {
        this.y = y;
    }
    public void setVX(int vx) { this.vx = vx; }
    public void setVY(int vy) { this.vy = vy; }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public double getX() {return x;}
    public double getY() {return y;}
    public int getVX() {return vx;}
    public double getVY() {return vy;}

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String e) {
        gamemode = e;
    }

}
