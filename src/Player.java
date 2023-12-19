import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player{
    // hit box
    private double x, y;
    private double px, py;
    private int width, height;

    // sprite
    private int width2, height2;

    // vector
    private double g = 2; //gravity
    private double vy = 0;
    private double vx = 4;
    private double initY = -30;

//    double locX = (double) width/2;
//    double locY = (double) height/2;


    // rotation
    private double angle = 0;
    private double jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump


    private String gamemode;
    private int floor=450;

    private final BufferedImage icon;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];

    private Point[] pointsOutline;

    private Point outlinePA, outlinePB, outlinePC, outlinePD;


    public Player(double x, double y, int width, int height) {
        this.gamemode = "cube";

        this.y=y;
        this.x = x;

        this.width  = width;
        this.height = height;

        this.icon = Util.resize( Util.loadBuffImage("assets/icons/Cube001.png" ), width, height);

    }


    public void move(ArrayList<Solid> solids) {
        y += vy;
        vy += g;
        Ground(solids);
        if(y + width < floor) {
            x += vx;
            angle += jumpRotate;
        }
        System.out.println(y+width);
    }


    public void Ground(ArrayList<Solid> solids ) {
        if ( y + width > floor ) {
            y = floor - width;
            vy = 0;
        }
    }


    public void thrust() {
        if( gamemode == "cube" ) {
            if (y + width == floor) {
                vy = initY; //initial velocity added
            }
        }
        if( gamemode == "ship" ) {
            vy = initY;
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public Rectangle getSpriteBound() {
        return new Rectangle((int) x, (int) y, width2, height2);
    }

    public Polygon getOutline() {
        return new Polygon(new int[]{(int) outlinePA.getX(), (int) outlinePB.getX(), (int) outlinePC.getX(), (int) outlinePD.getX()},
                new int[] {(int) outlinePA.getY(), (int) outlinePB.getY(), (int) outlinePC.getY(), (int) outlinePD.getY()},
                4);
    }


    public void draw(Graphics g) {
        g.setColor(new Color(110,110,222));
        drawHitbox(g);

        AffineTransform rot = new AffineTransform();

        rot.rotate(angle,(double) width/2,(double) height/2);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);
        // The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR 	// NEAREST_NEIGHBOR is fastest but lowest quality
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(icon,rotOp,(int)x,(int)y);

        drawHitbox(g);
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) x, (int) y , width, height);
    }

    public String getGamemode() { return gamemode; }
    public void setGamemode(String e) { gamemode = e;}


    public void setX(int x) { this.x = x;}
    public void setY(int y) { this.y = y; }
    public void setVX(int vx) { this.vx = vx; }
    public void setVY(int vy) { this.vy = vy; }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public double getX() {return x;}
    public double getY() {return y;}
    public double getVX() {return vx;}
    public double getVY() {return vy;}
    public void setInitY( int n ) { initY = n;}
    public void setJumpRotate(){
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    }


}
