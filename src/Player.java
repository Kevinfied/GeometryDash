import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player{

    private boolean debugDead = false;
    public boolean orbActivate = false;
    public boolean changeYdirection = false;
    Image deathAnimation = new ImageIcon("assets/deathEffects/deathEffect.gif").getImage();
    boolean dead = false;

    private double x, y;
    public double constantX;
    private double px, py;

    private int width, height;
    private int groundLevel;
    private int offsetY = 0;
    public int deathTimeCounter = 0;
    // vector
    public double g = 5.2; //gravity
    private double vy = 0;
    private double vx = 22;
    public double initY = -41.55;
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

    public static boolean practiceMode;
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

        py = y;
        px = x;

        if(gamemode.equals ("cube") ) {
            jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY );
            vy += g;
            if(!onSurface) {     //cube rotation
                angle += jumpRotate;
            }
        }

        if(gamemode.equals( "ship" ) ) {

            vy += shipG ;

            if (GamePanel.mouseDown ) {        //ship movement if mouse pressed
                vy += shipLift;
            }

            if ( vy >= Math.tan( Math.PI / 4) * vx ) {
                angle = Math.PI /4 ;
            }
            else if ( vy <= Math.tan( - Math.PI / 4) * vx ) {
                angle = -Math.PI / 4;
            }
            else if(!onSurface || !onCeiling){
                angle = 0.9 * Math.atan2( vy , vx );
            }

        }


        int newOffsetY = Globals.floor - groundLevel;
        if ( Math.abs(newOffsetY - offsetY) > 120 && !gamemode.equals("ship") ) {
            offsetY = Globals.floor - groundLevel;
        }
        else if ( vy > 0 && Math.abs(py - y) > 38) {
            offsetY = Globals.floor - groundLevel - 30;
        }
        if (gamemode.equals("ship")) {
            offsetY = 220;
        }

        if( y + offsetY > Globals.SCREEN_HEIGHT ) {
            offsetY -= 100;
        }
        else if (y + offsetY < 0 ) {
            offsetY += 100;
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


        if(changeYdirection) {
            upsideDown();
            changeYdirection = false;
        }


        if (!pads.isEmpty()) {
            for (Pad p: pads) {
                collidePad(p);
            }
        }


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
        int ceilR = floorR + 1;
        double incre = 0.3;
        if ( gamemode == "cube") {
            if (angle % (Math.PI / 2) != 0) {
                angle += incre;
            }

            if (angle > (ceilR) * (Math.PI / 2)) {
                angle = (ceilR) * (Math.PI / 2);
            }

        }
        else if (gamemode == "ship") {
            incre *= -1;
            if (angle % (Math.PI / 2) != 0) {
                angle += incre;
            }

            if (angle < (floorR) * (Math.PI / 2)) {
                angle = (floorR) * (Math.PI / 2);
            }

        }
        angle = angle % (2 * Math.PI);
    }

    public void collide() {

    }

    public void upsideDown() {
        reverse = true;
        g = -5.2; //gravity
        initY = 41.55;
        shipG = -1.2;
        shipLift = -2.008 * shipG;
        jumpRotate = (double) ( Math.PI * g ) / ( 2 * initY );
    }

    public void upright() {
        reverse = false;
        g = 5.2; //gravity
        initY = -41.55;
        shipG = 1.2;
        shipLift = -2.008 * shipG;
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY );
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

            if (getHitbox().intersects(top) && (Math.min(solid.getX() + solid.getWidth(), x + width) - Math.max(x, solid.getX()) >= y + height - solid.getY() -10)) {
                collideUp = true;
            }

            if(getHitbox().intersects(bottom) && (Math.min(solid.getX() + solid.getWidth(), x + width) - Math.max(x, solid.getX()) >= solid.getY() + solid.getHeight() - y -10)) {
                collideDown = true;
            }

            if( side.intersects(getHitbox())) {
                collideX = true;
            }
        }


        if (gamemode == "cube" ) {
            if(collideUp && collideDown) {
                dies();
            }
            else if (collideUp && !reverse && !collideDown) {
                vy = 0;
                y = solid.getY() - height;
                groundLevel = (int) solid.getY();
                onSurface = true;
            }
            else if( collideDown && reverse && !collideUp) {
                vy = 0;
                y = solid.getY() + solid.getHeight();
                groundLevel = (int) solid.getY() + solid.getHeight();
                onSurface = true;
             //   System.out.println( (solid.getY() + solid.getHeight() ) + "    "+ solid.getY() + "    " + y);
            }
            else if (!reverse && (collideDown || collideX)) {
                dies();
            }
            else if (reverse && collideUp ) {
                dies();
            }
            else if (reverse &&  collideX) {
                dies();
            }



        }
        else {
            if (collideUp) {
                vy = 0;
                y = solid.getY() - height;
                groundLevel = (int) solid.getY();
                onSurface = true;
            }
            else if (collideDown) {
                vy = 0;
            }
            else if (collideX) {
                dies();
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
            else if (portal.getType() == "upright") {
                upright();
            }

        }

    }
    public void dies() {
            if(deathTimeCounter > 0) {
                return;
            }

            if (practiceMode) {
                if (Level.checkpoints.isEmpty()) {
                   restart();
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
                    reverse= lastCheckpoint.reverse;
                    angle = 0;
                    groundLevel = (int) y + width;
                }
            }
            else {

                int score = (int) x ;
                String s;

                System.out.println("current level" + MenuPanel.targetLevel);

                if (MenuPanel.targetLevel == 1) {
                    if (score > Globals.lvl1TopScore) {
                        Globals.lvl1TopScore = score;
                        s =Integer.toString(score) + "\n" +  Integer.toString(Globals.lvl2TopScore) + "\n" + Integer.toString(Globals.lvl3TopScore);
                        Util.writeFile(Globals.scoreFile, s ) ;
                       System.out.println("new score");
                    }
                }
                else if (MenuPanel.targetLevel == 2) {
                    if ( score > Globals.lvl2TopScore) {
                        Globals.lvl2TopScore = score;
                        s = Integer.toString(Globals.lvl1TopScore) + "\n" +Integer.toString(score) +"\n" + Integer.toString(Globals.lvl3TopScore);
                        Util.writeFile(Globals.scoreFile, s ) ;
                        deathTimeCounter = 10;
                        System.out.println("new score");
                    }
                }
                else if (MenuPanel.targetLevel == 3) {
                    if ( score > Globals.lvl3TopScore) {
                        Globals.lvl3TopScore = score;
                        s = Integer.toString(Globals.lvl1TopScore) + "\n" + Integer.toString(Globals.lvl2TopScore) + "\n" + Integer.toString(score) ;
                        Util.writeFile(Globals.scoreFile, s ) ;
                        deathTimeCounter = 10;
                        System.out.println("new score");
                    }
                }

                vx = 0;
                vy = 0;

                deathTimeCounter = 10;

                System.out.println(Globals.lvl1TopScore);
                System.out.println(Globals.lvl2TopScore);
                System.out.println(Globals.lvl3TopScore);

            }

    }

    public void restart() {
        gamemode = "cube";
        upright();
        initY = -41.55;
        y = Globals.floor - height;
        vy = 0;
        vx = 22;
        reverse = false;
        x = constantX;
        onSurface = true;
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
        if (deathTimeCounter > 0) {
            g.drawImage(deathAnimation, (int) (constantX), (int) (y + offsetY), 100, 100, null);
            return;
        }
        drawSprite( g, offsetY);


    }

    public void drawSprite( Graphics g, int offsetY) {
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform rot = new AffineTransform();
        rot.rotate(angle,(double) width/2,(double) height/2);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);
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
    public void setInitY( double n ) { initY = n;}
    public void setJumpRotate(){
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    }


}
