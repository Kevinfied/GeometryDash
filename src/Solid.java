import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Solid {

    private int x, y;
    private int width = 50;
    private int height = 50;

    private String type;

    private Image icon;


    // constructor
    public Solid(int x, int y, String type) {

        this.x = x;
        this.y = y;
        this.type = type;

    }

    public void collide(Rectangle rectangle) {
        int playerX = (int)(rectangle.getX() + width/2);
        int playerY = (int)(rectangle.getY() + height/2);
        int distX = Math.abs(playerX - x);
        int distY = Math.abs(playerY - y);

        if (rectangle.intersects(getRect())) {

            if (distX > distY) { // survives. landed on top of the solid
                if (playerX > x) {
                    rectangle.setLocation((int)(rectangle.getX() + distX - distY), (int)rectangle.getY());
                }

                else {
                    rectangle.setLocation((int)(rectangle.getX() - distX + distY), (int)rectangle.getY());
                }
            }
            else { // dies. hit the side of the solid
                if (playerY > y) {
                    rectangle.setLocation((int)rectangle.getX(), (int)(rectangle.getY() + distY - distX));
                }

                else {
                    rectangle.setLocation((int)rectangle.getX(), (int)(rectangle.getY() - distY + distX));
                }

//                dies();
            }

        }
    }


    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

    }




}


/*
    if (player.coolide(solid)) {
        if (
 */
