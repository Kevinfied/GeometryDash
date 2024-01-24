// Background.java
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

/*
moves the background picture, and the ground picture, part of visual effect
-Daisy
 */
public class Background {

    //sprite
    private BufferedImage bg;
    private BufferedImage ground;
    private BufferedImage groundLine = Util.loadBuffImage("assets/ground/groundLine.png");

    //arraylist for x coordinate of the sprites
    private ArrayList<Integer> groundX = new ArrayList<Integer> ();
    private ArrayList<Integer> backgroundX = new ArrayList<Integer>();

    //background dimensions and ground dimension
    private int bw, bh, gw, gh;

    //the y coordinate
    private int backgroundY = 0;
    private int GroundY = Globals.floor;

    //velocity for background and ground
    private int bv = -2;
    private int gv = -22;

    //for colors
    private double blue = 155;
    private double red = 100;
    private double green = 1;
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

        //add the x coordinates
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

    public void move() { // move the x coordinates of the sprites
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




        g.setColor(new Color((int)red, (int)green, (int)blue, 77));
        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);

        int tmp = 250;
        g.drawImage(groundLine, 0, GroundY + offsetY, Globals.SCREEN_WIDTH, 5, null);


        //change the color gradually
        red += 0.1;
        blue += 0.2 ;
        green -= 0.3;

        red = (red + 255) % 255;
        blue = (blue  + 255)% 255;
        green = (green  + 255)% 255;




    }

    public void mainMenuDraw(Graphics g) { // draws the background for mainmenu

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
