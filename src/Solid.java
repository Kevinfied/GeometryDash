// Solid.java
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
game object that player can land on safely depends on the player's mode(reverse or normal) - Daisy
 */
public class Solid {
    //solid's coordnate and dimensions

    private int x, y;
    public int width;
    public int height;


    //sprite
    private BufferedImage img = Util.loadBuffImage("assets/solids/block.png");

    //types: normal, slabUp, slabDown
    private String type;


    // constructor
    public Solid(int x, int y, String type) {
        //initialize coordinate and dimensions accordding to type

        this.x = x;
        this.y = y;
        if(type == "slabDown"){
            this.y = y + 37;
        }
        this.type = type;

        if (type == "solid") {
            this.width = 75;
            this.height = 75;
        }
        else if (type == "slabUp" || type == "slabDown") {
            this.width = 75;
            this.height = 37;
        }

        img = Util.resize(img, width, height);
    }



    public Rectangle getRect() {
        return new Rectangle(x, y, height, width);
    }


    public void draw(Graphics g, int offsetX, int offsetY, Player player) {

        int n = (int) (Globals.SCREEN_WIDTH / 2 - GamePanel.player.constantX);
        int a = (int) Math.abs(x - (player.getX() + n));
        //if the solid is on screen (within a half screen distance away from player) then draw it with visual effect
        if( Util.onScreen(player, x)) {
            int b =  (int) (a * 0.07 );//how much the y display have to adjust
            if ( a < 550 || Math.abs(player.getGroundLevel() - Globals.floor) <300) {
                b = 0;
            }
            if( y + width < player.getY()) { // if solid is below player's coordinate, it will move down
                b *= -1;
            }
            g.drawImage(img, x + offsetX, y + offsetY + b, width, height, null);

        }


    }

    public double getX() { return x;}
    public double getY() { return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}



}