// Spike.java
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// hazards kill the player on contact - Kevin
public class Spike {

    //coordinate and dimensions of spike
    private int x, y;
    private int width = 75, height = 75;

    //orintation
    private int orientation; // 0 = up, 1 = down

    //sprite
    private BufferedImage img;

    //type: normal or small or side
    private String type;

    // constructor
    public Spike(int xx, int yy, int orientation, String type) {
        this.x = xx;
        this.y = yy;
        this.orientation = orientation;
        this.type = type;

        //assign the correct sprite according to spike's orientation and type

        if (type == "normal") {

            if (orientation == 0) {
                img = Util.loadBuffImage("assets/spikes/normalUpright.png");
            }
            else if (orientation == 1) {
                img = Util.loadBuffImage("assets/spikes/normalDown.png");
            }
        }
        else if (type == "small") {
            if (orientation == 0) {
                img = Util.loadBuffImage("assets/spikes/smallUpright.png");
            }
            else if (orientation == 1) {
                img = Util.loadBuffImage("assets/spikes/smallDown.png");
            }
        }

        else if (type == "side") {
            img = Util.loadBuffImage("assets/spikes/normalLeft.png");
        }
        img = Util.resize(img, width, height);
    }

    public Rectangle getRect() { // bounding box
        return new Rectangle(x, y, height, width);
    }



    public Rectangle getHitbox() { // hitbox for collision
        /*
        normal size
            should be (30, 22) (44, 22) (30, 52) (44, 52) hitbox for both orientations
         */
        if (type == "normal") {

            return new Rectangle(x + 30, y + 22, 15, 31);
        }
        else if (type == "small"){
            if (orientation == 0) {
                return new Rectangle(x + 28, y+52, 19, 18);
            }
            else if (orientation == 1) {
                return new Rectangle(x + 28, y+5, 19, 18);
            }
        }

        else if (type == "side") {
            return new Rectangle(x + 22, y + 30, 31, 15);
        }


        return null;
    }



    //for testing purposes
    public void drawHitbox(Graphics g, int offsetX, int offsetY) {

        int x = this.x + offsetX;
        int y = this.y + offsetY;
        g.setColor(Color.RED);
        if (type == "normal") {
            g.drawRect(x + 30 , y + 22, 15, 31);
        }
        else if (type == "small"){
            if (orientation == 0) {
                g.drawRect(x + 28, y+52, 19, 18);
            }
            else if (orientation == 1) {
                g.drawRect(x + 28, y+5, 19, 18);
            }
        }
        else if (type == "side") {
            g.drawRect(x + 22, y + 30, 31, 15);
        }
    }



    public void draw(Graphics g, int offsetX, int offsetY, Player player) {
        //same as solid

        int n = (int) (Globals.SCREEN_WIDTH / 2 - GamePanel.player.constantX);
        int a = (int) Math.abs(x - (player.getX() + n));

        //same as solid
        if( Util.onScreen(player, x)) {
            int b =  (int) (a * 0.07 );
            if ( a < 550 || Math.abs(player.getGroundLevel() - Globals.floor) <300) {
                b = 0;
            }
            if( y + width < player.getY()) {
                b *= -1;
            }
            g.drawImage(img, x + offsetX, y + offsetY + b, null);

        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int w) {
        this.width = w;
    }

    public void setHeight(int h) {
        this.height = h;
    }

}