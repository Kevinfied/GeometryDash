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
    private int groundLevel;
    private int offsetY = 0;

    // vector
    private double g = 4.5; //gravity
    private double vy = 0;
    private double vx = 19;
    private double initY = -38;
    private double shipG = 1.2;
    private double shipLift = -2.008 * shipG;


    private ArrayList<Solid> playerSolids = new ArrayList<Solid>();
    private int curSolidIndex = 0;
    private int curSpikeIndex = 0;

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

        this.groundLevel = (int) y + width;

        this.icon = Util.resize( Util.loadBuffImage("assets/icons/Cube001.png" ), width, height);

    }


    public void move(ArrayList<Solid> solids, ArrayList<Spike> spikes) {
        if (debugDead) {
            return;
        }

        py = y;
        px = x;
        y += vy;
        x += vx;


        for ( int i = curSolidIndex ; i < Math.min(curSolidIndex + 60, solids.size() ); i++) {
            Solid s = solids.get( i );
            if ( landOnSolid( s , solids ) && ! playerSolids.contains( s ) ) {
                playerSolids.add(s);
            }
        }


        for (Spike s : spikes) {
            collideSpike(s);
        }

        for( int i = 0 ; i < playerSolids.size() ; i++ ) {
            Solid s = playerSolids.get( i );
            if (! onSolid( s ) && playerSolids.contains(s)) {
                playerSolids.remove(s);
            }
        }


        onSurface = (onGround() || ! playerSolids.isEmpty()  );
        System.out.println(curSolidIndex);

        // i really don't know why this line is necessary, but it is !!!!!! DON'T DELETE THIS LINE, oh wait
        //oh wait we actually might not need it yayy
//        if(! playerSolids.isEmpty()) {
//            groundLevel = (int) playerSolids.get(0).getY();
//        }

        if(onSurface) { y = groundLevel - height;}


        if(gamemode.equals ("cube") ) {
            if(vy < 37 || vy > -37) {    //cube velocity change
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

        offsetY = Globals.floor - groundLevel;

        //rotation adjustment
        if(onSurface){
            angleAdjust();
        }

    }

    public boolean onSolid(Solid s) {  // don't delete this function
        return x + width > s.getX() && x < s.getX() + s.getWidth() && y + height <= s.getY() && y + height +vy >= s.getY();
    }

    public boolean onGround() {
        if (y + width > Globals.floor) {
            y = Globals.floor - width;
            vy = 0;
            groundLevel = Globals.floor ;
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
                angle += 0.1;
            }
        }

        if (angle > floorR ) {
            angle = floorR * (Math.PI / 2);
        }

        angle = angle % ( 2 * Math.PI);
    }

//    public void findSpikeIndex( Level l, ArrayList<Spike> spikes) {
//        int levelcolumn = (int) x / Globals.solidWidth - 1;
//        for( int c = Math.max( 0, levelcolumn -1) ; c < Math.min( levelcolumn + 1, l.mapArr.length) ; c++ ) {
//            for ( int h = 0 ; h < l.getHeight() ; h++ ) {
//                if ( l.mapArr[c][h] == l.aSpike) {
//                    curSpikeIndex = spikes.indexOf()
//                }
//            }
//        }
//
//    }


//    public void collideSolid(Solid solid) {
//        Rectangle solidHitbox = solid.getRect();
//        int solidBottom = (int)solid.getY()+solid.getHeight();
//        int playerBottom = (int)getY()+getHeight();
//
//        if (getHitbox().intersects(solidHitbox)) {
////            System.out.println("collide");
//            if (!getPrevHitboxX().intersects(solidHitbox)) {
//                dies();
//                return;
//            }
//
//            if (!getPrevHitboxY().intersects(solidHitbox)) {
////
//                if (playerBottom > solidBottom + 40) {
//                    dies();
//
//                }
//                else {
//                    y = solid.getY() - height;
//                    vy = 0;
//                    groundLevel = (int) solid.getY();
//                }
//
//            }
//
//        }
//
//    }

    public boolean landOnSolid(Solid s , ArrayList< Solid > lis ) {
        Rectangle solidHitbox = s.getRect();
        int px = (int) x;
        int py = (int) y;
        int sx = (int) s.getX();
        int sy = (int) s.getY();
        int pRightSide = (int) x + width;
        int pBottom = (int) y + height;
        int sRightSide = (int) sx + s.getWidth();
        int sBottom = (int) sy + s.getHeight();

        if (getHitbox().intersects(solidHitbox) ) {
            if (pRightSide - sx > pBottom - sy) {
                y = s.getY() - height;
                vy = 0;
                groundLevel = (int) s.getY();
                curSolidIndex = lis.indexOf( s ) ;
                return true;
            }
            else {
                dies();
                return false;
            }

        }
        return false;
    }


    public void collideSpike(Spike spike) {
        Rectangle spikeHitbox = spike.getHitbox();
        if (getHitbox().intersects(spikeHitbox)) {
            dies();
        }
    }
    public void collidePortal(Portal portal) {
        Rectangle portalHitbox = portal.getRect();
        if (getHitbox().intersects(portalHitbox)) {
            gamemode = portal.getType();
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(248, 248, 4));
        g2.setStroke(new BasicStroke(10)); // Sets the stroke width to 10
        g2.drawLine((int) constantX, groundLevel + offsetY, (int) constantX + 100, groundLevel + offsetY);

        g2.setStroke(new BasicStroke(1));

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
    public int getOffsetY() {return offsetY; }
    public int getGroundLevel() { return groundLevel; }
    public void setAngle( double n ) { angle = n ;}
    public void setInitY( int n ) { initY = n;}
    public void setJumpRotate(){
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    }


}
