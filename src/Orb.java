// Orb.java
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

//game object that interact with the player by increasing its y velocty magnitude when collide with - Daisy
public class Orb {

    // coordinates and dimensions
    private int x, y;
    public static int width = 75, height = 75;

    //radius difference between each ring of the orb
    public int rDiff = 7;

    //sprite
    private BufferedImage img;

    //radius of each circle ring, saturation of each circle
    ArrayList<Integer> radius = new ArrayList<Integer>();
    ArrayList<Integer> saturation = new ArrayList<Integer>();

    public Orb(int x, int y, String type) {
        Random rand = new Random();
        this.x = x;
        this.y = y;
        for( int i = 1; i <= 7; i++) {
            radius.add( i* rDiff) ;
            saturation.add(rand.nextInt(7) * 36);
        }
    }


    //hit box used for pplayer's collision with orb
    public Rectangle getHitbox() {
        return new Rectangle(x -10, y -10, width+20, height+20);
    }

    public void drawHitbox(Graphics g, int offsetX, int offsetY) {
        //draw the rings
        for (int i = 0; i<radius.size() ; i ++ ) {
            radius.set( i ,  radius.get(i) + 3);
            int r = radius.get(i);
            if ( r > rDiff * (radius.size())) {
                radius.set( i ,  rDiff * 3);
            }
            g.setColor( new Color(0, 239, 255, saturation.get(i) ));
            g.fillOval(x + width/2 -r +offsetX, y + height/2 - r + offsetY, 2 * r, 2 * r);
        }
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        drawHitbox(g, offsetX, offsetY);
    }

}
