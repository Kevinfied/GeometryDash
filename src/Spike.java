import java.awt.*;

// hazards kill the player on contact - Kevin
public class Spike {

    private int x, y;
    private int width = 75, height = 75;
    private int orientation; // 0 = up, 1 = down
    private Image img;
    private String type;

    // constructor
    public Spike(int xx, int yy, int orientation, String type) {
        this.x = xx;
        this.y = yy;
        this.orientation = orientation;
        this.type = type;
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


        return null;
    }



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
    }



    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
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