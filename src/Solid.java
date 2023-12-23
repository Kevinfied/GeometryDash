import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Solid {

    private int x, y;
    public static int width = 75;
    public static int height = 75;

    private String type;

    private Image icon;


    // constructor
    public Solid(int x, int y, String type) {

        this.x = x;
        this.y = y;
        this.type = type;

    }

    public void collide(Player player) {
        Rectangle playerHitbox = player.getHitbox();
        if (playerHitbox.intersects(getRect())) {
            System.out.println("collide");

        }
//        int playerX = (int)(rectangle.getX() + width/2);
//        int playerY = (int)(rectangle.getY() + height/2);
//        int distX = Math.abs(playerX - x);
//        int distY = Math.abs(playerY - y);
//        if (rectangle.intersects(getRect())) {
//            System.out.println("collide");
//            if (distX > distY) { // survives. landed on top of the solid
//                if (playerX > x) {
//                    rectangle.setLocation((int)(rectangle.getX() + distX - distY), (int)rectangle.getY());
//                }
//
//                else {
//                    rectangle.setLocation((int)(rectangle.getX() - distX + distY), (int)rectangle.getY());
//                }
//
//                System.out.println("collide");
//
//            }
//            else { // dies. hit the side of the solid
//                if (playerY > y) {
//                    rectangle.setLocation((int)rectangle.getX(), (int)(rectangle.getY() + distY - distX));
//                }
//
//                else {
//                    rectangle.setLocation((int)rectangle.getX(), (int)(rectangle.getY() - distY + distX));
//                }
//                System.out.println("dies");
////                dies();
//            }
//
//        }

    }


    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        g.fillRect(x+offset, y, width, height);
        g.setColor(Color.RED);
        g.drawRect(x+offset, y, width, height);
        Rectangle d = getRect();
//        Rectangle d = new Rectangle((int) -(x - offset), y, width, height);

        g.fillRect(x + offsetX, y + offsetY, width, height);
//        d.translate(offset, 0);
//        g2d.draw(d);

    }

    public double getX() { return x;}
    public double getY() { return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}





}


/*
    if (player.coolide(solid)) {
        if (
 */
