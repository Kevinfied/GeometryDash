import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class Orb {

    private int x, y;
    public static int width = 75, height = 75;
    public int rDiff = 7;
    private String type;
    private BufferedImage img;
    ArrayList<Integer> radius = new ArrayList<Integer>();
    ArrayList<Integer> saturation = new ArrayList<Integer>();

    public Orb(int x, int y, String type) {
        Random rand = new Random();
        this.x = x;
        this.y = y;
        this.type = type;
        for( int i = 1; i <= 7; i++) {
            radius.add( i* rDiff) ;
            saturation.add(rand.nextInt(7) * 36);
        }
    }


    public Rectangle getHitbox() {

        return new Rectangle(x -10, y -10, width+20, height+20);
    }

    public void drawHitbox(Graphics g, int offsetX, int offsetY) {
//        System.out.println(saturation);

        g.setColor(Color.GREEN);
        g.drawRect(x + offsetX -10, y + offsetY -10, width + 20, height+ 20);
        g.drawRect(x+offsetX, y + offsetY, width, height);
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
//        g.drawImage(img, x + offsetX, y + offsetY, null);
        drawHitbox(g, offsetX, offsetY);
    }

}
