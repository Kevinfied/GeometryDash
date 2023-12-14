import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;


public class Player{
    // hit box
    private double x, y;
    private int width, height;

    // sprite
    private int width2, height2;

    // vector
    private double g = 2; //gravity
    private double vy = 0;
    private double vx = 5;
    private double initY = -30;

    double locX = width/2;
    double locY = height/2;

    private int floor=325;

    // rotation
    private double angle = 0;
    private double jumpRotate = (double) ( -Math.PI * g ) / ( 4 * initY ); // add to angle when jump

    private double ang = 0;

    private String gamemode;

    private boolean inAir;

    private BufferedImage icon;

    private Point[] pointsOutline;

    private Point outlinePA, outlinePB, outlinePC, outlinePD;


    public Player(double x, double y, int width, int height) {
        this.gamemode = "cube";

        this.y=y;
        this.x = x;

        this.width  = width;
        this.height = height;

        this.inAir = false;

        this.icon = Util.resize(Util.loadBuffImage("assets/icons/Cube001.png"), width, height);

    }


    public void move() {
        y += vy;
        vy += g;
        fallCheck();
        if( y < floor) {
            x += vx;
            angle += jumpRotate;
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
            vy = initY; //initial velocity added
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public Rectangle getSpriteBound() {
        return new Rectangle((int) x, (int) y, width2, height2);
    }

    public Polygon getOutline() {
        return new Polygon(new int[]
                {(int) outlinePA.getX(), (int) outlinePB.getX(), (int) outlinePC.getX(), (int) outlinePD.getX()},
                new int[] {(int) outlinePA.getY(), (int) outlinePB.getY(), (int) outlinePC.getY(), (int) outlinePD.getY()},
                4);
    }


    public void draw(Graphics g) {
        System.out.println("angle: "+ angle);

        g.setColor(new Color(110,110,222));
//        g.fillRect(0,0,800,600);
//        g.drawImage(icon, (int)x-width/2, (int)y-height/2, width, height, null);
//        drawHitbox(g);

        AffineTransform rot = new AffineTransform();

        rot.rotate(angle,(double) width/2,(double) height/2);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);
        // The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR
        Graphics2D g2D = (Graphics2D)g; 														// NEAREST_NEIGHBOR is fastest but lowest quality
        g2D.drawImage(icon,rotOp,(int)x-width/2,(int)y-height/2);

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

    public void setX(int x) { this.x = x;}

    public void setY(int y) {
        this.y = y;
    }
    public void setVX(int vx) { this.vx = vx; }
    public void setVY(int vy) { this.vy = vy; }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public double getX() {return x;}
    public double getY() {return y;}
    public double getVX() {return vx;}
    public double getVY() {return vy;}

    public String getGamemode() { return gamemode; }

    public void setGamemode(String e) { gamemode = e;
    }

}
