import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player{

    private boolean debugDead = false;
    public boolean orbActivate = false;
    public boolean changeYdirection = false;

    // hit box
    private double x, y;
    public double constantX;
    private double px, py;
    private String collidingOrb = "none";
    private int width, height;
    private int groundLevel;
    private int offsetY = 0;

    // vector
    public double g = 5.2; //gravity
    private double vy = 0;
    private double vx = 22;
    public double initY = -41.5;
    public double shipG = 1.2;
    public double shipLift = -2.008 * shipG;


    private ArrayList<Solid> playerSolids = new ArrayList<Solid>();
    private int curSolidIndex = 0;

    // rotation
    private double angle = 0;
    private double jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    public boolean reverse = false;

    private String gamemode;
    private final BufferedImage icon;
    public boolean onSurface = true;
    public boolean prevOnSurface = true;
    public boolean onCeiling = false;

    public boolean practiceMode;
    private final BufferedImage shipIcon;
    private final BufferedImage ufoIcon;

    public Player(double x, double y, int width, int height) {
        this.gamemode = "cube";

        this.y = y;
        this.x = x;
        this.constantX = x;

        this.width  = width;
        this.height = height;

        this.groundLevel = (int) y + width;

        practiceMode = false;
        this.icon = Util.resize( Util.loadBuffImage("assets/icons/Cube001.png" ), width, height);
        this.shipIcon = Util.resize( Util.loadBuffImage("assets/icons/Ship001.png" ), width, height);
        this.ufoIcon = Util.resize( Util.loadBuffImage("assets/icons/UFO001.png" ), width, height);
    }


    public void move(ArrayList<Solid> solids, ArrayList<Spike> spikes, ArrayList<Portal> portals, ArrayList<Pad> pads, ArrayList<Orb> orbs) {
        if (debugDead) {
            return;
        }

//        System.out.println(angle + " ,  " + onSurface);


        py = y;
        px = x;
//        y += vy;
//        x += vx;

        if(gamemode.equals ("cube") ) {
//            System.out.println(onSurface);
            jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY );
//            if(vy < 37 || vy > -37) {    //cube velocity change
            vy += g;
//            }
            if(!onSurface) {     //cube rotation
                angle += jumpRotate;
            }
        }

        if(gamemode.equals( "ship" ) ) {
            if( vy < 75 ) {
                vy += shipG ;
            }

            if (GamePanel.mouseDown && vy >= -75) {        //ship movement if mouse pressed
                vy += shipLift;
            }

            if ( vy >= Math.tan( Math.PI / 4) * vx ) {
                angle = Math.PI /4 ;
            }
            else if ( vy <= Math.tan( - Math.PI / 4) * vx ) {
                angle = -Math.PI / 4;
            }
            else {
                angle = 0.9 * Math.atan2( vy , vx );
            }

        }

        if(gamemode.equals ("ufo") ) {
//            if(vy < 34 || vy > -34) {    //cube velocity change
            vy += shipG;
//            }

            if(! onSurface ) {

                if (vy < 0 && angle < Math.PI / 7) {
                    angle += 0.02;

                } else if (vy > 0 && angle > -Math.PI / 7) {
                    angle -= 0.02;
                }

            }

        }

        int newOffsetY = Globals.floor - groundLevel;
        if ( Math.abs(newOffsetY - offsetY) > 150 && !gamemode.equals("ship") ) {
            offsetY = Globals.floor - groundLevel;
        }
        else if ( vy > 0 && Math.abs(py - y) > 38) {
            offsetY = Globals.floor - groundLevel - 30;
        }
        if (gamemode.equals("ship")) {
            offsetY = 210;
        }


//        rotation adjustment
        if(onSurface || onCeiling){
            angleAdjust();
        }

        onSurface = false;

        for (int i=0; i<Math.abs(vy); i++) {
            py = y;
            if (vy < 0) {
                y -= 1;
            }
            else {
                y += 1;
            }
            for (Solid s : solids) {
                collideSolid(s);
            }
        }

        for (int j=0; j<vx; j++) {
            px = x;
            x += 1;
            for (Solid s : solids) {
                collideSolid(s);
            }
        }


        for (Spike s : spikes) {
            collideSpike(s);
        }
        for (Portal p : portals) {
            collidePortal(p);
        }


        onCeiling = ceilingCheck();
        prevOnSurface = onSurface;

        groundCheck();

//        System.out.println(angle + " ,  " + onSurface);

        if(changeYdirection) {
            upsideDown();
            changeYdirection = false;
        }

//        System.out.println(angle + " ,  " + onSurface);

        if (!pads.isEmpty()) {
            for (Pad p: pads) {
                collidePad(p);
            }
        }

        //System.out.println(angle + "  ," + onSurface + "     ,reverse +" + reverse);

        if ( ! onSurface && !GamePanel.mouseDown) {
            orbActivate = true;
        }

        if(onSurface) {
            orbActivate = false;
        }

        if (!orbs.isEmpty()) {
            for (Orb o : orbs) {
                collideOrb(o);
            }
        }




    }

    public void groundCheck() {
        if(reverse){
            return;
        }
        if (y + width > Globals.floor) {
            y = Globals.floor - width;
            vy = 0;
            groundLevel = Globals.floor ;
            onSurface = true;
        }
    }

    public boolean ceilingCheck() {
        if(  gamemode.equals("ship") && y < Globals.SHIP_CEILING) {
            y = Globals.SHIP_CEILING;
            vy = 0;
            return true;
        }

        else if (reverse && y< Globals.SHIP_CEILING) {
            y = Globals.SHIP_CEILING;
            vy = 0;
            return true;
        }
        return false;
    }


    public void angleAdjust() {
        int floorR = (int) Math.floor( (angle / (Math.PI /2 )) );
        int next = floorR + 1;
        double incre = 0.3;
        if (angle % (Math.PI / 2) != 0) {
            angle += incre;
        }

        if (angle > (next) * (Math.PI / 2) ) {
            angle = (next) * (Math.PI / 2);
        }

        angle = angle % ( 2 * Math.PI);
    }

    public void collide() {

    }

    public void upsideDown() {
        g *= -1; //gravity

        initY *= -1;
        shipG *= -1;
        shipLift *= -1;
        if (reverse) {
            reverse = false;
        }
        else if (!reverse){
            reverse = true;
        }
    }

    public void collideSolid( Solid solid) {

        Rectangle sHitbox = solid.getRect();
        Rectangle pHitbox = new Rectangle((int)px, (int)py, width, height);

        Rectangle bottom = new Rectangle( (int) solid.getX(), (int) solid.getY() + solid.getHeight() - 1, solid.getWidth(), 1 );
        Rectangle top = new Rectangle((int)solid.getX(),(int) solid.getY(), solid.getWidth(), 1);
        Rectangle side = new Rectangle ( (int) solid.getX(), (int) solid.getY(), 1, solid.getHeight());
        boolean collideUp = false;
        boolean collideDown = false;
        boolean collideX = false;

        if( sHitbox.intersects(getHitbox())) {
            //System.out.println("it's colliding");
            if(getHitbox().intersects(top) ) {
                collideUp = true;
            }
//            if (getHitbox().intersects(top) && (Math.min(solid.getX() + solid.getWidth(), x + width) - Math.max(x, solid.getX()) >= y + height - solid.getY() -10)) {
//                collideUp = true;
//            }
//            if(getHitbox().intersects(top) && !pHitbox.intersects(top)) {
//                collideUp = true;
//            }

            if (getHitbox().intersects(bottom)) {
                collideDown = true;
            }
//            if (getHitbox().intersects(bottom) && !pHitbox.intersects(bottom)) {
//                collideDown = true;
//            }
//            if(getHitbox().intersects(bottom) && (Math.min(solid.getX() + solid.getWidth(), x + width) - Math.max(x, solid.getX()) >= solid.getY() + solid.getHeight() - y +10)) {
//                collideDown = true;
//            }

            if( side.intersects(getHitbox())) {
                collideX = true;
            }
        }


        if (gamemode == "cube" ) {
            if(collideUp && collideDown) {
                dies();
                //System.out.println("collide both");
            }
            else if (collideUp && !reverse && !collideDown) {
                vy = 0;
                y = solid.getY() - height;
                groundLevel = (int) solid.getY();
                onSurface = true;
               // System.out.println(collideX + "    " + collideUp + "    " + collideDown);
            }
            else if( collideDown && reverse && !collideUp) {
                vy = 0;
                y = solid.getY() + solid.getHeight();
                groundLevel = (int) solid.getY() + solid.getHeight();
                onSurface = true;
               // System.out.println(collideX + "    " + collideUp + "    " + collideDown);
                System.out.println( (solid.getY() + solid.getHeight() ) + "    "+ solid.getY() + "    " + y);
            }
            else if (!reverse && (collideDown || collideX)) {
                dies();
               // System.out.println("collideDown");
            }
            else if (reverse && collideUp ) {
                dies();
               // System.out.println("collideUp");
            }
            else if (reverse &&  collideX) {
                dies();
                //System.out.println("collide Side");
               // System.out.println(collideX + "    " + collideUp + "    " + collideDown);
              //  System.out.println(solid.getHeight());
            }



        }
        else {
            if (collideUp) {
                vy = 0;
                y = solid.getY() - height;
                groundLevel = (int) solid.getY();
                onSurface = true;
            }
            else if (collideX) {
                dies();
               // System.out.println("non cube collide");
            }
            else if (collideDown) {
                vy = 0;
            }

        }
    }


    public void collidePad( Pad pad) {
        Rectangle padHitbox = pad.getRect();
        if (getHitbox().intersects(padHitbox) ) {
            vy = -55;
            onSurface = false;
        }
    }

    public void collideOrb( Orb o) {
        Rectangle orbHitbox = o.getHitbox();
        if (getHitbox().intersects(orbHitbox) && orbActivate && GamePanel.mouseDown) {
            vy = -40;
            onSurface = false;
        }
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
            if( portal.getType() == "cube") {
                gamemode = "cube";
            }
            else if (portal.getType() == "ship") {
                gamemode = "ship";
            }
            else if ( portal.getType() == "reverse"){
               upsideDown();
            }

        }

    }
    public void dies() {

//        if (Level.startpos != null) {
//            gamemode = "cube";
//            y = Level.startpos.getY();
//            vy = 0;
//            x = Level.startpos.getX();
//            onSurface = true;
//        }
//        if {
            if (practiceMode) {
                if (Level.checkpoints.isEmpty()) {
                    //            dies();
                    gamemode = "cube";
                    setInitY(-38);
                    y = Globals.floor - height;
                    vy = 0;

                    x = constantX;
                    onSurface = true;
                } else {
                    Checkpoint lastCheckpoint = Level.checkpoints.get(Level.checkpoints.size() - 1);
                    x = lastCheckpoint.getX();
                    y = lastCheckpoint.getY();
                    gamemode = lastCheckpoint.getGamemode();
                    vy = lastCheckpoint.getVy();
                    g = lastCheckpoint.g;
                    initY = lastCheckpoint.initY;
                    shipG = lastCheckpoint.shipG;
                    shipLift = lastCheckpoint.shipLift;
                    reverse=lastCheckpoint.reverse;

//                    vx = lastCheckpoint.getVx();
                    angle = 0;
                    groundLevel = (int) y + width;
                    playerSolids.clear();
                    curSolidIndex = 0;
                }
            } else {
                gamemode = "cube";
                y = Globals.floor - height;
                vy = 0;
                x = constantX;
                onSurface = true;
            }

            // stop all motion - for debugging
//        debugDead = true;

//    vx = 0;
//        }
    }



    public void cubeJump() {
        if (gamemode == "cube" ) {
            if (onSurface  || prevOnSurface) {
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
       // drawHitbox(g);
        drawSprite( g, offsetY);

        //debug hitbox
       // g.drawRect((int)constantX, (int) y + offsetY , width, height);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(248, 248, 4));
        g2.setStroke(new BasicStroke(10)); // Sets the stroke width to 10
      //  g2.drawLine((int) constantX, groundLevel + offsetY, (int) constantX + 100, groundLevel + offsetY);

        g2.setStroke(new BasicStroke(1));


    }

    public void drawSprite( Graphics g, int offsetY) {
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform rot = new AffineTransform();
        rot.rotate(angle,(double) width/2,(double) height/2);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);
        // The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR 	// NEAREST_NEIGHBOR is fastest but lowest quality
        if (gamemode == "cube") {
            g2D.drawImage(icon, rotOp, (int) constantX, (int) y + offsetY);
        }

        else if (gamemode == "ship") {
            g2D.drawImage(shipIcon, rotOp, (int) constantX, (int) y + offsetY);
        }

        else if (gamemode == "ufo") {
            g2D.drawImage(ufoIcon, rotOp, (int) constantX, (int) y + offsetY);
        }
    }

    public void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) x, (int) y , width, height);
    }

    public String getGamemode() {
//        System.out.println(gamemode);
        return gamemode;
    }
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
