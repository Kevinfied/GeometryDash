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
        if(type == "slabDown"){
            this.y = y + 37;
        }
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

        return new Rectangle(x, y, height, width);
    }


    public void draw(Graphics g, int offsetX, int offsetY) {

//        int yy = y;
//        if (type == "slabDown") {
//            yy += 37;
//        }
        Graphics2D g2d = (Graphics2D)g;
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        Rectangle d = getRect();
    //        Rectangle d = new Rectangle((int) -(x - offset), y, width, height);
        g.fillRect(x + offsetX, y + offsetY, width, height);
        g.setColor(new Color(0, 208, 255, 255) );
        g.drawRect(x + offsetX, y + offsetY, width, height);

    }

    public double getX() { return x;}
    public double getY() { return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}



}