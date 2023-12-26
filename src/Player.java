import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player{
    // hit box
    private double x, y;
    private double constantX;
    private double px, py;
    private int width, height;

    // vector
    private double g = 4.5; //gravity
    private double vy = 0;
    private double vx = 15;
    private double initY = -38;
    private double shipG = 1.2;
    private double shipLift = -2.008 * shipG;

    private ArrayList<Solid> ocupiedSolids = new ArrayList<Solid>();

    // rotation
    private double angle = 0;
    private double jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump

    private String gamemode;
    private final BufferedImage icon;
    public boolean onSurface = true;


    public Player(double x, double y, int width, int height) {
        this.gamemode = "cube";

        this.y = y;
        this.x = x;
        this.constantX = x;

        this.width  = width;
        this.height = height;

        this.icon = Util.resize( Util.loadBuffImage("assets/icons/Cube001.png" ), width, height);

    }


    public void move(ArrayList<Solid> solids) {
        py = y;
        px = x;
        y += vy;
        x += vx;

        for (Solid s : solids) {
            collideSolid(s);

            if( onSolid(s) && !ocupiedSolids.contains(s)) {
                ocupiedSolids.add(s);
            }
            //if !onSolid(s) and is in ocupiedSolids, then remove it from the arraylist
            if (!onSolid(s) && ocupiedSolids.contains(s)) {
                ocupiedSolids.remove(s);
            }
        }
//       System.out.println(ocupiedSolids);


        onSurface = (onGround() || ! ocupiedSolids.isEmpty());
//        onGround();



        if(gamemode.equals ("cube")  || gamemode.equals ("ufo") ) {
            if(vy < 34 || vy > -34) {    //cube velocity change
                vy += g;
            }
            if(!onSurface) {      //cube rotation
                angle += jumpRotate;
            }
        }

        if(gamemode == "ship") {
            vy += shipG ;
            if (GamePanel.mouseDown) {        //ship movement if mouse pressed
                vy += shipLift;
            }
            angle = Math.tan( vy / vx );

            if (angle > Math.PI / 3) {
                angle = Math.PI /3 ;
            }
            else if (angle < -Math.PI / 3) {
                angle = -Math.PI / 3;
            }
        }


        //rotation adjustment
        if(onSurface){
            angleAdjust();
        }

    }

    public boolean onSolid(Solid s) {
        return x + width > s.getX() && x < s.getX() + s.getWidth() && y + height <= s.getY() && y + height +vy >= s.getY();
    }

    public boolean onGround() {
        if (y + width > Globals.floor) {
            y = Globals.floor - width;
            vy = 0;
            return true;
        }
        return false;
    }


    public void angleAdjust() {
        int floorR =(int)  (angle / (Math.PI /2 ));
        if(angle % (Math.PI /2) != 0){
            angle += 0.1;
        }
        if(angle > floorR) {
            angle = floorR * (Math.PI /2 );
        }
        angle = angle % ( 2 * Math.PI);
    }



    public void collideSolid(Solid solid) {
        Rectangle solidHitbox = solid.getRect();
        int solidBottom = (int)solid.getY()+solid.getHeight();
        int playerBottom = (int)getY()+getHeight();

        if (getHitbox().intersects(solidHitbox)) {
            System.out.println("collide");
            if (!getPrevHitboxX().intersects(solidHitbox)) {
                System.out.println("collideX, dies");
                dies();
                return;
            }

            if (!getPrevHitboxY().intersects(solidHitbox)) {
                System.out.println("collideY");

                // if top, dies
                // if bottom, lands on solid and survives
                if (playerBottom > solidBottom) {

                    System.out.println("collideYtop");
                    dies();
                    return;
                }
                else {
                    System.out.println("collideYbottom");
                    y = solid.getY() - height;
                    vy = 0;
                }

            }

        }

    }

    public void collideHazard(Hazard hazard) {
        Rectangle hazardHitbox = hazard.getRect();
        if (getHitbox().intersects(hazardHitbox)) {
            dies();
        }
    }

    public void dies(){
//        y = 400;
//        vy = 0;
//        x = constantX;
//        onSurface = true;
        // stop all motion
        vy = 0;
        vx = 0;

    }


    public void cubeJump() {
        if (gamemode == "cube" ) {
            if (onSurface) {
                vy = initY; //initial y-velocity when jumping
            }
        }
        onSurface = false;
    }

    public void ufoJump() {
        if (gamemode == "ufo" ) {
            vy = initY;
        }
    }


    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }



    public void draw(Graphics g) {
        g.setColor(new Color(110,110,222));
        drawHitbox(g);

        AffineTransform rot = new AffineTransform();

        rot.rotate(angle,(double) width/2,(double) height/2);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);
        // The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR 	// NEAREST_NEIGHBOR is fastest but lowest quality
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(icon, rotOp, (int) constantX, (int) y);

        drawHitbox(g);


    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) x, (int) y , width, height);
    }

    public String getGamemode() { return gamemode; }
    public void setGamemode(String e) { gamemode = e;}

    public Rectangle getPrevHitboxY() {
        return new Rectangle((int) x, (int)py, width, height);
    }

    public Rectangle getPrevHitboxX() {
        return new Rectangle((int) px, (int) y, width, height);
    }




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
    public void setAngle( double n ) { angle = n ;}
    public void setInitY( int n ) { initY = n;}
    public void setJumpRotate(){
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    }


}
