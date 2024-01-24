// Player.java
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
controlled by mouse. Interact with all other game objects. Has 2 gamemode, can reverse it's Y vectors
 */

public class Player{

    //sprites
    Image deathAnimation = new ImageIcon("assets/deathEffects/deathEffect.gif").getImage();
    private final BufferedImage shipIcon;
    private final BufferedImage ufoIcon;
    private final BufferedImage icon;


    private double x, y;
    public double constantX;
    private double px, py;
    private int width, height;
    private int groundLevel;
    private int offsetY = 0;
    public int deathTimeCounter = 0;


    // player's movement vector
    public double g = 5.19; //gravity
    private double vy = 0;
    private double vx = 22;
    public double initY = -41.55;
    public double shipG = 1.2;  //ship has different uy vectors
    public double shipLift = -2.008 * shipG;


    // rotation
    private double angle = 0;
    private double jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    public boolean reverse = false;


    //player's state : gamemode: cube or ship , reversed or upright, onsurface or in air in current frame and previous frame, can jump on orbs or not
    private String gamemode;
    public boolean changeYdirection = false;
    public boolean onSurface = true;
    public boolean prevOnSurface = true;
    public boolean onCeiling = false;
    public boolean orbActivate = false;


    //mode: practice or real game play
    public static boolean practiceMode;
    private boolean debugDead = false; // for debugging.

    // if player has beat the level
    public boolean win = false;

    // Font
    Font titleFont;

    public Player(double x, double y, int width, int height) { //constructor
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

        try{
            File fntFile = new File("assets/Fonts/PUSAB.otf");

            titleFont = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(90f);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        catch(FontFormatException ex){
            System.out.println(ex);
        }

    }


    public void move(ArrayList<Solid> solids, ArrayList<Spike> spikes, ArrayList<Portal> portals, ArrayList<Pad> pads, ArrayList<Orb> orbs) {

        if (debugDead) {
            return;
        }

        if (x >= (Level.mapWidth*75)) { // player wins after reaching the end of the map
            win = true;

            // Update score

            if (!practiceMode) {
                String s;
                int score = (int) x;
                if (MenuPanel.targetLevel == 1) {
                    Globals.lvl1TopScore = score;
                    s = Integer.toString(score) + "\n" + Integer.toString(Globals.lvl2TopScore) + "\n" + Integer.toString(Globals.lvl3TopScore);
                    Util.writeFile(Globals.scoreFile, s);
                } else if (MenuPanel.targetLevel == 2) {
                    Globals.lvl2TopScore = score;
                    s = Integer.toString(Globals.lvl1TopScore) + "\n" + Integer.toString(score) + "\n" + Integer.toString(Globals.lvl3TopScore);
                    Util.writeFile(Globals.scoreFile, s);
                } else if (MenuPanel.targetLevel == 3) {
                    Globals.lvl3TopScore = score;
                    s = Integer.toString(Globals.lvl1TopScore) + "\n" + Integer.toString(Globals.lvl2TopScore) + "\n" + Integer.toString(score);
                    Util.writeFile(Globals.scoreFile, s);
                }
            }

            return; // no more movement
        }

        //movement for player
        gamemodeMovement();

        //calculate the offset for drawing objects other than player
        adjustOffset();


//        rotation adjustment: if player's rotation is not a multiple of pi/2
        if(onSurface || onCeiling){
            angleAdjust();
        }

        //set on surface to false, then check through all solids and ground to determine if lpayer is on surface
        onSurface = false;

        //normal collision
        collide(solids, spikes, portals);


        onCeiling = ceilingCheck();
        prevOnSurface = onSurface;

        //check if player is on the ground;
        groundCheck();

        //reverse the player's y vectors if changeYdirection is true
        if(changeYdirection) {
            upsideDown();
            changeYdirection = false;
        }

        // special collisions for pad
        if (!pads.isEmpty()) {
            for (Pad p: pads) {
                collidePad(p);
            }
        }

        //if player in air and mouse is pressed, then player can jump on orbs
        if ( ! onSurface && !GamePanel.mouseDown) {
            orbActivate = true;
        }

        //orbs are deactivated when player is on ground
        if(onSurface) {
            orbActivate = false;
        }

        // collision for orbs
        if (!orbs.isEmpty()) {
            for (Orb o : orbs) {
                collideOrb(o);
            }
        }


    }

    public void gamemodeMovement(){

        //cube movement: rotate by a certain amount when in air
        //add gravity to vy
        if(gamemode.equals ("cube") ) {
            jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY );
            vy += g;
            if(!onSurface) {     //cube rotation
                angle += jumpRotate;
            }
        }

        //ship movement
        if(gamemode.equals( "ship" ) ) {

            vy += shipG ;

            if (GamePanel.mouseDown ) {        //vy magnitude increase if mouse pressed
                vy += shipLift;
            }

            //ship's rotation are restricted within [ -pi/4, pi/4]
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

    }


    // offsetY is used for the drawing of other objects
    public void adjustOffset(){
        int newOffsetY = Globals.floor - groundLevel;
        // offset from previous and current frame has to differ by 120 unit for the current offset to adjust
        //prevent frequent screen movement
        if ( Math.abs(newOffsetY - offsetY) > 120 && !gamemode.equals("ship") ) {
            offsetY = Globals.floor - groundLevel;
        }
        else if ( vy > 0 && Math.abs(py - y) > 38) {
            offsetY = Globals.floor - groundLevel - 30;
        }

        //when in ship mode, offsetY is fixed
        if (gamemode.equals("ship")) {
            offsetY = 220;
        }

        // if the player won't be displaying within the height of the screen, adjust offsetY accordingly
        if( y + offsetY > Globals.SCREEN_HEIGHT ) {
            offsetY -= 100;
        }
        else if (y + offsetY < 0 ) {
            offsetY += 100;
        }
    }

    //only check if player's on ground if it's not in reverse mode.
    public void groundCheck() {
        if(reverse){
            return;
        }
        if (y + width > Globals.floor) { //Set groundLevel to the ground, player is onsurface
            y = Globals.floor - width;
            vy = 0;
            groundLevel = Globals.floor ;
            onSurface = true;
        }
    }

    //only check if player is touching the ceiling when it's in ship mode or reverse mode
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


    //angle adjustment when player is touching the ceiling or ground
    public void angleAdjust() {
        int floorR = (int) Math.floor( (angle / (Math.PI /2 )) );
        int ceilR = floorR + 1;
        double incre = 0.3;
        //add to player's rotation so it will adjust to the nearest multiple of pi/2 that is more than the current angle
        if ( gamemode == "cube") {
            if (angle % (Math.PI / 2) != 0) {
                angle += incre;
            }

            if (angle > (ceilR) * (Math.PI / 2)) {
                angle = (ceilR) * (Math.PI / 2);
            }

        }

        //ship is opposite of cube for angle adjustment
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

    public void collide(ArrayList<Solid> solids, ArrayList<Spike> spikes, ArrayList<Portal> portals) { //normal collision for solid, spikes and portals
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
    }

//reverse the Y vector
    public void upsideDown() {
        reverse = true;
        g = -5.19; //gravity
        initY = 41.55;
        shipG = -1.2;
        shipLift = -2.008 * shipG;
        jumpRotate = (double) ( Math.PI * g ) / ( 2 * initY );
    }

    //reset Y vectors
    public void upright() {
        reverse = false;
        g = 5.19; //gravity
        initY = -41.55;
        shipG = 1.2;
        shipLift = -2.008 * shipG;
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY );
    }

    //
    public void collideSolid( Solid solid) {
        //check which side(s) of the cube is the player colliding: right, up, down

        Rectangle sHitbox = solid.getRect();

        Rectangle bottom = new Rectangle( (int) solid.getX(), (int) solid.getY() + solid.getHeight() - 1, solid.getWidth(), 1 );
        Rectangle top = new Rectangle((int)solid.getX(),(int) solid.getY(), solid.getWidth(), 1);
        Rectangle side = new Rectangle ( (int) solid.getX(), (int) solid.getY(), 1, solid.getHeight());
        boolean collideUp = false;
        boolean collideDown = false;
        boolean collideX = false;

        //check which side is colliding with the player

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

        //cube and solid collisions
        if (gamemode == "cube" ) {
            if(collideUp && collideDown) {
                dies();
            }
            else if (collideUp && !reverse) {
                vy = 0;
                y = solid.getY() - height;
                groundLevel = (int) solid.getY();
                onSurface = true;
            }
            else if( collideDown && reverse ) {
                vy = 0;
                y = solid.getY() + solid.getHeight();
                groundLevel = (int) solid.getY() + solid.getHeight();
                onSurface = true;
            }

            else if (!reverse && (collideDown || collideX)) {
                dies();
            }
            else if (reverse && collideUp ) {
                dies();
            }
            else if (collideX) {
                dies();
            }



        }

        //ship collision with solid, only dies when collide with
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


    //pad collision make player jump, increase vy
    public void collidePad( Pad pad) {
        Rectangle padHitbox = pad.getRect();
        if (getHitbox().intersects(padHitbox) ) {
            vy = -55;
            onSurface = false;
        }
    }

    //orb sollision also make player jump, increase vy
    public void collideOrb( Orb o) {
        Rectangle orbHitbox = o.getHitbox();
        if (getHitbox().intersects(orbHitbox) && orbActivate && GamePanel.mouseDown) {
            vy = -40;
            onSurface = false;
        }
    }

    //spikes kill player
    public void collideSpike(Spike spike) {
        Rectangle spikeHitbox = spike.getHitbox();
        if (getHitbox().intersects(spikeHitbox)) {
            dies();
        }
    }

 //   portal change player's gamemode according to their type
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

    //
    public void dies() {
            GameFrame.stopGameSound();
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

                if (MenuPanel.targetLevel == 1) {
                    if (score > Globals.lvl1TopScore) {
                        Globals.lvl1TopScore = score;
                        s =Integer.toString(score) + "\n" +  Integer.toString(Globals.lvl2TopScore) + "\n" + Integer.toString(Globals.lvl3TopScore);
                        Util.writeFile(Globals.scoreFile, s ) ;
                    }
                }
                else if (MenuPanel.targetLevel == 2) {
                    if ( score > Globals.lvl2TopScore) {
                        Globals.lvl2TopScore = score;
                        s = Integer.toString(Globals.lvl1TopScore) + "\n" +Integer.toString(score) +"\n" + Integer.toString(Globals.lvl3TopScore);
                        Util.writeFile(Globals.scoreFile, s ) ;
                        deathTimeCounter = 10;
                    }
                }
                else if (MenuPanel.targetLevel == 3) {
                    if ( score > Globals.lvl3TopScore) {
                        Globals.lvl3TopScore = score;
                        s = Integer.toString(Globals.lvl1TopScore) + "\n" + Integer.toString(Globals.lvl2TopScore) + "\n" + Integer.toString(score) ;
                        Util.writeFile(Globals.scoreFile, s ) ;
                        deathTimeCounter = 10;
                    }
                }

                vx = 0;
                vy = 0;

                deathTimeCounter = 10;

            }

    }

    //reset the player x, y, and all vectors and state to their initial state
    public void restart() {
        GameFrame.startGameSound(MenuPanel.targetLevel);
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

    //cube can jump when it's on a surface: ground (upright), ceiling(reverse), solid
    public void cubeJump() {
        if (gamemode == "cube" ) {
            if (onSurface  || prevOnSurface) {
                vy = initY; //initial y-velocity when jumping
            }
        }
        onSurface = false;
    }


    //player's hit box
    public Rectangle getHitbox() {
        return new Rectangle((int) x, (int) y, width, height);
    }


    //draw player's sprtie. But if it died, play the death animation
    public void draw(Graphics g, int offsetY) {

        if (deathTimeCounter > 0) {
            g.drawImage(deathAnimation, (int) (constantX) - ((200 - width)/2), (int) (y + offsetY) - ((200-height)/2), 200, 200, null);
            return;
        }
        drawSprite( g, offsetY);

        if (win) {
            Util.drawCenteredString(g, "Level Complete", new Rectangle(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT), titleFont);
        }
    }

    //draw sprite according to gamemode of the player
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

    }

//    public void drawHitbox(Graphics g) {
//        g.setColor(Color.RED);
//        g.drawRect((int) x, (int) y , width, height);
//    }

    //getter and setter methods for access to protected variables
    public String getGamemode() {
        return gamemode;
    }
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
    public int getOffsetY() {return offsetY; }
    public int getGroundLevel() { return groundLevel; }
    public void setAngle( double n ) { angle = n ;}
    public void setInitY( double n ) { initY = n;}
    public void setJumpRotate(){
        jumpRotate = (double) ( -Math.PI * g ) / ( 2 * initY ); // add to angle when jump
    }


}
