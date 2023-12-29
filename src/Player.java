import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player{

    private boolean debugDead = false;

    // hit box
    private double x, y;
    private double constantX;
    private double px, py;
    private int width, height;

    // vector
    private double g = 4.5; //gravity
    private double vy = 0;
    private double vx = 17;
    private double initY = -38;
    private double shipG = 1.2;
    private double shipLift = -2.008 * shipG;


    private ArrayList<Solid> playerSolids = new ArrayList<Solid>();
    private ArrayList<Slab> playerSlabs = new ArrayList<Slab>() ;

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


    public void move(ArrayList<Solid> solids, ArrayList<Slab> slabs, ArrayList<Spike> spikes) {
        if (debugDead) {
            return;
        }

        py = y;
        px = x;
        y += vy;
        x += vx;

        for (Solid s : solids) {
            collideSolid(s);

            if( onSolid(s) && !playerSolids.contains(s)) {
                playerSolids.add(s);
            }
            //if !onSolid(s) and is in playerSolids, then remove it from the arraylist
            if (!onSolid(s) && playerSolids.contains(s)) {
                playerSolids.remove(s);
            }
        }


        for (Slab s : slabs) {
            collideSlab(s);
            if( onSlab(s) && !playerSlabs.contains(s)) {
                playerSlabs.add(s);
            }
            //if !onSolid(s) and is in playerSolids, then remove it from the arraylist
            if (!onSlab(s) && playerSlabs.contains(s)) {
                playerSlabs.remove(s);
            }
        }

        for (Spike s : spikes) {
            collideSpike(s);
        }


//       System.out.println(playerSolids);


        onSurface = (onGround() || ! playerSolids.isEmpty() || !playerSlabs.isEmpty() );


        if(gamemode.equals ("cube") ) {
            if(vy < 34 || vy > -34) {    //cube velocity change
                vy += g;
            }
            if(!onSurface) {      //cube rotation
                angle += jumpRotate;
            }
        }

        if(gamemode.equals( "ship" ) ) {
            vy += shipG ;
            if (GamePanel.mouseDown) {        //ship movement if mouse pressed
                vy += shipLift;
            }

            if ( vy >= Math.tan( Math.PI / 4) * vx ) {
                angle = Math.PI /4 ;
            }
            else if ( vy <= Math.tan( - Math.PI / 4) * vx ) {
                angle = -Math.PI / 4;
            }
            else {
                angle = Math.atan2( vy , vx );
            }
        }

        if(gamemode.equals ("ufo") ) {
            if(vy < 34 || vy > -34) {    //cube velocity change
                vy += shipG;
            }

            System.out.println( vy );
            if(! onSurface ) {

                if (vy < 0 && angle < Math.PI / 7) {
                    angle += 0.02;

                } else if (vy > 0 && angle > -Math.PI / 7) {
                    angle -= 0.02;
                }

            }

        }


        //rotation adjustment
        if(onSurface){
            angleAdjust();
        }

    }

    public boolean will_land_on( Solid s ) {
        return x + width > s.getX() && x < s.getX() + s.getWidth();
    }

    public boolean onSolid(Solid s) {
        return x + width > s.getX() && x < s.getX() + s.getWidth() && y + height <= s.getY() && y + height +vy >= s.getY();
    }
    public boolean onSlab( Slab s) {
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

        if (angle % (Math.PI / 2) != 0) {
            if(gamemode.equals( "cube" ) || gamemode.equals( "ufo" )) {
                angle += 0.1;
            }
            if(gamemode.equals( "ship" ))  {
                angle += 0.001;
            }
        }

        if (angle > floorR) {
            angle = floorR * (Math.PI / 2);
        }
        angle = angle % ( 2 * Math.PI);
    }



    public void collideSolid(Solid solid) {
        Rectangle solidHitbox = solid.getRect();
        int solidBottom = (int)solid.getY()+solid.getHeight();
        int playerBottom = (int)getY()+getHeight();

        if (getHitbox().intersects(solidHitbox)) {
//            System.out.println("collide");
            if (!getPrevHitboxX().intersects(solidHitbox)) {
//                System.out.println("collideX, dies");
                dies();
                return;
            }

            if (!getPrevHitboxY().intersects(solidHitbox)) {
//                System.out.println("collideY");

                // if top, dies
                // if bottom, lands on solid and survives
                if (playerBottom > solidBottom) {

//                    System.out.println("collideYtop");

                    dies();
                    return;

                }
                else {
//                    System.out.println("collideYbottom");
                    y = solid.getY() - height;
                    vy = 0;
//                    if(!playerSolids.contains(solid)) {
//                        playerSolids.add(solid);
//                    }
                }

            }

        }

    }

    public void collideSlab(Slab slab) {
        Rectangle slabHitbox = slab.getRect();
        int slabBottom = (int)slab.getY()+slab.getHeight();
        int playerBottom = (int)getY()+getHeight();

        if (getHitbox().intersects(slabHitbox)) {
//            System.out.println("collide");
            if (!getPrevHitboxX().intersects(slabHitbox)) {
//                System.out.println("collideX, dies");
                dies();
                return;
            }

            if (!getPrevHitboxY().intersects(slabHitbox)) {
//                System.out.println("collideY");

                // if top, dies
                // if bottom, lands on solid and survives
                if (playerBottom > slabBottom) {
                    dies();
                    return;
                }
                else {
                    y = slab.getY() - height;
                    vy = 0;
                }

            }
        }
    }

    public void collideSpike(Spike spike) {
        Rectangle spikeHitbox = spike.getHitbox();
        if (getHitbox().intersects(spikeHitbox)) {
            dies();
        }
    }

    public void dies(){
//        y = 400;
//        vy = 0;
//        x = constantX;
//        onSurface = true;
        // stop all motion - for debugging
        debugDead = true;
//        vy = 0;
//        vx = 0;

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



    public void draw(Graphics g, int offsetY) {

        g.setColor(new Color(110,110,222));
        drawHitbox(g);

        AffineTransform rot = new AffineTransform();

        rot.rotate(angle,(double) width/2,(double) height/2);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);
        // The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR 	// NEAREST_NEIGHBOR is fastest but lowest quality
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(icon, rotOp, (int) constantX, (int) y + offsetY);


        drawHitbox(g);

        //debug hitbox
        g.drawRect((int)constantX, (int) y + offsetY , width, height);


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
