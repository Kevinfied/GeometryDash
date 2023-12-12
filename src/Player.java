import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Player {

    private int x, y;
    private int width, height; // hitbox
    private int width2, height2; // sprite

    private int vx, vy;
    private double rotation;


    public Player(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width  = width;
        this.height = height;
    }

    public void move() {
        x += vx;
        y += vy;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getSpriteBound() {
        return new Rectangle(x, y, width2, height2);
    }



    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(x, y, width, height);
    }

    public void drawSpriteBound(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(x, y, width2, height2);
    }


    public void setX(int x) {
        this.x = x;
    }


    public void setY(int y) {
        this.y = y;
    }



    public void setVX(int vx) {
        this.vx = vx;
    }

    public void setVY(int vy) {
        this.vy = vy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVX() {
        return vx;
    }

    public int getVY() {
        return vy;
    }


}
