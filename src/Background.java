import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
public class Background {
    private BufferedImage bg;
    private BufferedImage ground;
    private ArrayList<Integer> groundX = new ArrayList<Integer> ();
    private ArrayList<Integer> backgroundX = new ArrayList<Integer>();
    private int bw, bh, gw, gh;
    private int backgroundY = 0;
    private int GroundY = Globals.floor;
    private int bv = -2;
    private int gv = -22;
    private int blue = 255;
    private int red = 100;
    private int green = 1;
    int counter = 0;
    private int bg1x, bg2x;
//    BufferedImage BG1 = Util.loadBuffImage("assets/background/stereoBG.png" );
//    BufferedImage BG2 = Util.loadBuffImage("assets/background/stereoBG.png" );

    public Background( BufferedImage bg, BufferedImage ground) {
        this.bg = bg;
        this.ground = ground;
        this.bw = bg.getWidth();
        this.bh = bg.getHeight();
        this.gw = ground.getWidth();
        this.gh = ground.getHeight();

        int Xcounter = 0;
        for (int i = 0; i < 5 ; i++) {
            backgroundX.add(Xcounter);
            Xcounter+=bw;
        }

        Xcounter = 0;
        for (int i = 0; i<8; i++) {
            groundX.add(Xcounter);
            Xcounter+=gw;
        }


    }

    public void move() {
        for (int i = 0; i < backgroundX.size(); i++) {
            int x = backgroundX.get(i);
            x += bv;
            if (x + bw < 0) {
                backgroundX.set(i, backgroundX.get((backgroundX.size() -1) % backgroundX.size() ) + bw);
            } else {
                backgroundX.set(i, x);
            }
        }

        for (int i = 0; i < groundX.size(); i++) {
            int x = groundX.get(i);
            x += gv;
            groundX.set(i, x);
            if (x + gw < 0) {
                groundX.set(i, groundX.get( ((i + groundX.size() -1 ) % (groundX.size())) ) + gw-20);
            }
        }

    }

    public void draw(Graphics g, int offsetY) {

        for (Integer x : backgroundX) {
            g.drawImage(bg, x, backgroundY, bw, bh, null);
        }

        for (Integer x : groundX) {
            g.drawImage(ground, x, GroundY + offsetY, gw, gh, null);
        }



        g.setColor(new Color(red, green, blue, 77));
        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        if ( counter % 100 == 0) {
            red = (int) (0.97 * red);
            blue = (int) (0.97 * blue);
            green = (int) (1.02 * green);
            if (green > 255) {
                green = 255;
            }
            counter = 1;
        }
        counter += 1;

    }

    public void mainMenuDraw(Graphics g) {

        for (Integer x : backgroundX) {
            g.drawImage(bg, x, backgroundY, bw, bh, null);
        }

        for (Integer x : groundX) {
            g.drawImage(ground, x, GroundY - 90, gw, gh, null);
        }

        g.setColor(new Color(0, 0, 255, 120));
        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);


    }





}
