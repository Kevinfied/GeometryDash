import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Solid {

    private int x, y;
    public int width;
    public int height;

//    private String type;

    private BufferedImage img;
    private String type;


    // constructor
    public Solid(int x, int y, String type) {

        this.x = x;
        this.y = y;
//        this.type = type;
        this.type = type;

        if (type == "solid") {
            this.width = 75;
            this.height = 75;
            img = Util.loadBuffImage("assets/solids/block.png");
        }
        else if (type == "slabUp" || type == "slabDown") {
            this.width = 75;
            this.height = 37;
            img = Util.loadBuffImage("assets/solids/block.png");
        }


        img = Util.resize(img, 75, 75);
    }



    public Rectangle getRect() {
        int yy = y;
        if (type == "slabDown") {
            yy += 37;
        }
        return new Rectangle(x, yy, height, width);
    }


    public void draw(Graphics g, int offsetX, int offsetY) {

        int yy = y;
        if (type == "slabDown") {
            yy += 37;
        }
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.BLACK);
        g.drawRect(x, yy, width, height);
        Rectangle d = getRect();
    //        Rectangle d = new Rectangle((int) -(x - offset), y, width, height);
        g.fillRect(x + offsetX, yy + offsetY, width, height);
        g.setColor(new Color(0, 208, 255, 255) );
        g.drawRect(x + offsetX, yy + offsetY, width, height);


        // if player is on tis solid, make it light blueee, for testing
//        if(GamePanel.player.onSolid( this )) {
//            g.setColor(new Color(0, 255, 219, 255) );
//            g.fillRect(x + offsetX, yy + offsetY, width, height);
//        }
    //        d.translate(offset, 0);
    //        g2d.draw(d);

    }

    public double getX() { return x;}
    public double getY() { return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}



}