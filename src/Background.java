import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
public class Background {
    private BufferedImage BG1, BG2;
    private int w, h;
    private int v = 2;
    private int bg1x, bg2x;
//    BufferedImage BG1 = Util.loadBuffImage("assets/background/stereoBG.png" );
//    BufferedImage BG2 = Util.loadBuffImage("assets/background/stereoBG.png" );

    public Background( BufferedImage bg) {
        this.BG1 = bg;
        this.BG2 = bg;
        this.w = bg.getWidth();
        this.h = bg.getHeight();
        this.bg1x = 0;
        this.bg2x = w;
    }

    public void move() {
        bg1x -= v;
        bg2x -= v;
        if(bg1x + w < 0){
            bg1x = bg2x + w;
        }
        if(bg2x + w < 0){
            bg1x = bg1x + w;
        }
    }

    public void draw( Graphics g) {
        g.drawImage(BG1, bg1x, 0, w, h, null);
        g.drawImage(BG2, bg2x, 0, w, h, null);
    }





}
