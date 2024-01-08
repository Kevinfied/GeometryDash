import java.awt.*;

public class Barrier {
    private int x, y;
    public int width;
    public int height;



    // constructor
    public Barrier(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;

        this.width = 75;
        this.height = 75;
    }


    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }


    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.BLACK);
        g.setColor(new Color(0, 208, 255, 255));
        g.drawRect(x + offsetX, y + offsetY, width, height);
    }


    public double getY() { return y;}
    public double getWidth() {return width;}
    public double getX() {return x;}
    public double getHeight() {return height;}

}