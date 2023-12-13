import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;


public class Player{

//    private int x, y;
    private double x, y;
    private int width, height; // hitbox
    private int width2, height2; // sprite
    private double gravity = 1;
    private double velocity =0;
//    private Vector pos, velocity, acceleration, gravity;
    private int floor=325;

    private int vx, vy;
    private double rotation;

    private String gamemode;

    private boolean inAir;

    private Image icon;
    public Player(double x, double y, int width, int height) {
        this.y=y;
        this.x = x;
        this.width  = width;
        this.height = height;
        this.gamemode = "cube";
        this.inAir = false;

        this.icon = new ImageIcon("assets/icons/Cube001.png").getImage();
    }

    public void move() {

            y += velocity;
            velocity += gravity;

    }
    public void fallCheck() {
        if ( y > floor ) {
            y = floor;
            velocity = 0;
            System.out.println(y);
        }
    }


    public void thrust() {
            velocity = -20;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public Rectangle getSpriteBound() {
        return new Rectangle((int) x, (int) y, width2, height2);
    }


    public void draw(Graphics g) {

        g.drawImage(icon, (int) x, (int) y, width, height, null);

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
    public int getVY() {return vy;}

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String e) {
        gamemode = e;
    }

}
