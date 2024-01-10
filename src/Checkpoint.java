import java.awt.*;
import java.awt.image.BufferedImage;

public class Checkpoint {

    private int x, y;
    private int width = 30, height = 60;

//    private
    private BufferedImage img;
    private String type;
    private String gamemode;
    // constructor
    public Checkpoint(int xx, int yy, String gamemode) {
        this.x = xx;
        this.y = yy;

        img = Util.loadBuffImage("assets/checkpoint/checkpoint.png");
        img = Util.resize(img, width, height);
    }




    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(img, x + offsetX, y + offsetY, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String getGamemode() {
        return gamemode;
    }
}
