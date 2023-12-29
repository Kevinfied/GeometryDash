import java.awt.*;

// hazards kill the player on contact - Kevin
public class Spike {

    private int x, y;
    private int width = 75, height = 75;
    private int orientation; // 0 = up, 1 = down
    private Image img;

    // constructor
    public Spike(int xx, int yy, int orientation) {
        this.x = xx;
        this.y = yy;
        this.orientation = orientation;
    }

    public Rectangle getRect() { // bounding box
        return new Rectangle(x, y, height, width);
    }



    public Rectangle getHitbox() { // hitbox for collision
        /*
            should be (30, 22) (44, 22) (30, 52) (44, 52) hitbox for both orientations
         */
        return new Rectangle(x + 30, y + 22, 15, 31);
    }

    public void drawHitbox(Graphics g, int offsetX, int offsetY) {
        int x = this.x + offsetX;
        int y = this.y + offsetY;
        g.setColor(Color.RED);
        g.drawRect(x + 30 , y + 22, 15, 31);
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