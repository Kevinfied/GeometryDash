// GamePanel.java

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;

import java.io.File;


class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer = new Timer(1000/50, this);
    static Player player;
    BufferedImage groundLinePic = Util.resize(Util.loadBuffImage("assets/ground/ground1.png"), Globals.SCREEN_WIDTH, 5);
    Background bg = new Background(Util.loadBuffImage("assets/background/stereoBG.png"), Util.loadBuffImage("assets/ground/ground1.png"));
    //    ArrayList<String>  lvl1map = new ArrayList<String>();
    static String lvl1map;
    String levelSoundTrack;
    static Level lvl;

    ArrayList<Solid> lvlSolids = new ArrayList<Solid>();
    ArrayList <Spike> lvlSpikes = new ArrayList<Spike>();
    ArrayList <Portal> lvlPortals = new ArrayList<Portal>();
    ArrayList <Orb> lvlOrbs = new ArrayList<Orb>();
    ArrayList <Checkpoint> checkPoints = new ArrayList<Checkpoint>();
    ArrayList <Pad> lvlPads = new ArrayList<Pad>();
    ArrayList <SquareParticle> playerSquareParticles = new ArrayList<SquareParticle>();
    ArrayList<ArrayList<SquareParticle>> padParticles = new ArrayList<ArrayList<SquareParticle>>();
    ArrayList<ArrayList<SquareParticle>> portalParticles = new ArrayList<ArrayList<SquareParticle>>();
    ArrayList <SquareParticle> shipSquareParticles = new ArrayList<SquareParticle>();
    public double stationaryX = 300;
    private static int offsetX = 0;
    private static int offsetY = 0;
    static boolean mouseDown = false;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];



    public GamePanel( String mapString, String soundTrack) {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();
        mapReload(mapString, soundTrack);
    }

    public void actionPerformed(ActionEvent e) {

        move();

        // for cosmetics
        create();
        destroy();

        changeGamemode();

        repaint();

    }

    public void mapReload(String mapString, String soundTrack) {

        lvl = new Level(mapString);
        levelSoundTrack = soundTrack;

        double stationaryX = 300;
        player = new Player(stationaryX, Globals.floor-Globals.solidHeight, 75, 75);
        lvlSolids = new ArrayList<Solid>();
        lvlSpikes = new ArrayList<Spike>();
        lvlPortals = new ArrayList<Portal>();
        lvlOrbs = new ArrayList<Orb>();
        checkPoints = new ArrayList<Checkpoint>();
        lvlPads = new ArrayList<Pad>();
        playerSquareParticles = new ArrayList<SquareParticle>();
        padParticles = new ArrayList<ArrayList<SquareParticle>>();
        portalParticles = new ArrayList<ArrayList<SquareParticle>>();
        shipSquareParticles = new ArrayList<SquareParticle>();

        ArrayList <Ground> grounds = new ArrayList<Ground>();


        lvl1map = mapString;
        lvlSolids = lvl.getSolids();
        lvlSpikes = lvl.getSpikes();
        lvlPortals = lvl.getPortals();
        lvlPads = lvl.getPads();
        lvlOrbs = lvl.getOrbs();
        grounds = lvl.grounds;


        if (!lvlPads.isEmpty()) {
            for (Pad p: lvlPads) {
                padParticles.add(new ArrayList<SquareParticle>());
            }
        }

        if (!lvlPortals.isEmpty()) {
            for (Portal p: lvlPortals) {
                portalParticles.add(new ArrayList<SquareParticle>());
            }
        }

    }

    public void move() {
        if (player.deathTimeCounter > 0) {
            player.deathTimeCounter -- ;
            if(player.deathTimeCounter == 0) {
                player.restart();
            }
            return;
        }
        bg.move();
        player.move(lvlSolids, lvlSpikes, lvlPortals, lvlPads, lvlOrbs);
        if(mouseDown) {
            player.cubeJump();
        }

        if (! playerSquareParticles.isEmpty()) {
            for (SquareParticle s: playerSquareParticles) {
                s.move();
            }
        }

        if (! shipSquareParticles.isEmpty()) {
            for (SquareParticle s: shipSquareParticles) {
                s.move();
            }
        }

        for (int i = 0; i<padParticles.size(); i++) {
            ArrayList<SquareParticle> lis = padParticles.get(i);
            for (int j = lis.size()-1; j>=0; j--) {
                SquareParticle s = lis.get(j);
                s.move();
            }
        }

        for (int i = 0; i<portalParticles.size(); i++) {
            ArrayList<SquareParticle> lis = portalParticles.get(i);
            for (int j = lis.size()-1; j>=0; j--) {
                SquareParticle s = lis.get(j);
                s.move();
            }
        }

    }

    public void create() {
        Random rand = new Random();
        double min = 0; // Minimum value (pi/2)
        double max = 3 *Math.PI /2;     // Maximum value (pi)
        if (playerSquareParticles.size() < 100) {
            if( player.getGamemode().equals("cube") && player.onSurface == true) {
                int l = rand.nextInt(4) + 4;
                if ( !player.reverse) {
                    playerSquareParticles.add(new SquareParticle(player.getX(), player.getY() + player.getHeight() - 5 + rand.nextInt(5), Math.random() * (-Math.PI), l, l, 0.2 * player.getVX(), 20));
                }
                else if ( player.reverse) {
                    playerSquareParticles.add(new SquareParticle(player.getX(), player.getY() - 5 + rand.nextInt(5), Math.random() * (Math.PI), l, l, 0.2 * player.getVX(), 20));
                }
            }

            if (player.getGamemode().equals("ship")) {
                int l = rand.nextInt(3) + 4;
                playerSquareParticles.add ( new SquareParticle( player.getX(), rand.nextInt(player.getWidth()) + player.getY(), min + Math.random() * (max - min) , l, l,-2, 50));
            }
        }

        if ( player.getGamemode().equals("ship")) {
            if (shipSquareParticles.size() < 800) {
                int l= rand.nextInt(7) + 4;
                shipSquareParticles.add(new SquareParticle(rand.nextInt(1000) + player.getX() - 200, rand.nextInt(1000) + player.getY() - 400, min + Math.random() * (max - min) ,l,l,-2, 100 ));
            }
        }

        for (int i = 0; i<padParticles.size(); i++) {
            ArrayList lis = padParticles.get(i);
            if(lis.size() < 700){
                int l = rand.nextInt(7) + 4;
                lis.add(new SquareParticle(lvlPads.get(i).getX()+rand.nextInt(Globals.solidWidth), lvlPads.get(i).getY() + lvlPads.get(i).getHeight(), Math.PI/2, l, l,-10, rand.nextInt(50) + 70 ));
            }
        }


        for (int i = 0; i<portalParticles.size(); i++) {
            ArrayList lis = portalParticles.get(i);
            if(lis.size() < 700){
                int l = rand.nextInt(10) + 4;
                int portalY = lvlPortals.get(i).getY();
                int portalH = lvlPortals.get(i).getHeight();
                int portalX = lvlPortals.get(i).getX();
                int py = portalY - 20 + rand.nextInt(portalH + 20);
                int midy = (2* portalY + portalH ) /2;
                // trig actually useful...for once
                lis.add(new SquareParticle(portalX-rand.nextInt(100), py,  Math.asin((double) 2* ((midy) - py) / portalH ) , l, l,5, rand.nextInt(50) + 60 ));

            }
        }


    }

    public void destroy() {
        for (int i = playerSquareParticles.size() - 1 ; i>=0; i--) {
            SquareParticle s = playerSquareParticles.get(i);
           if (Math.pow(s.x - s.startX, 2) + Math.pow(s.y-s.startY, 2) > Math.pow(s.maxdist, 2)) {
               playerSquareParticles.remove(i);
           }
        }
        for (int i = shipSquareParticles.size() - 1 ; i>=0; i--) {
            SquareParticle s = shipSquareParticles.get(i);
            if (Math.pow(s.x - s.startX, 2) + Math.pow(s.y-s.startY, 2) > Math.pow(s.maxdist, 2)) {
                shipSquareParticles.remove(i);
            }
        }

        for (int i = 0; i<padParticles.size(); i++) {
            ArrayList<SquareParticle> lis = padParticles.get(i);
            for (int j = lis.size()-1; j>=0; j--) {
                SquareParticle s = lis.get(j);
                if (Math.pow(s.x - s.startX, 2) + Math.pow(s.y-s.startY, 2) > Math.pow(s.maxdist, 2)) {
                    lis.remove(j);
                }
            }
        }

        for (int i = 0; i<portalParticles.size(); i++) {
            ArrayList<SquareParticle> lis = portalParticles.get(i);
            for (int j = lis.size()-1; j>=0; j--) {
                SquareParticle s = lis.get(j);
                if (Math.pow(s.x - s.startX, 2) + Math.pow(s.y-s.startY, 2) > Math.pow(s.maxdist, 2)) {
                    lis.remove(j);
                }
            }
        }

    }

    public void changeGamemode() { //debug stuff
        if(keys[KeyEvent.VK_1]) {
            player.setGamemode("cube");
            player.setInitY(-41.5);
        }
        if(keys[KeyEvent.VK_2]) {
            player.setGamemode("ship");
            player.setAngle( 0 );
        }
        if(keys[KeyEvent.VK_3]) {
            player.setGamemode("ufo");
            player.setInitY( -12 );
            player.setAngle( 0 );
        }

        if(keys[KeyEvent.VK_4]) {
            player.changeYdirection = true;;
        }

    }

    public static void resetPlayer() {
        player.setGamemode("cube");
        player.upright();
        player.initY = -41.55;
//
//        player.y = Globals.floor - player.height;
        player.setY(Globals.floor - player.getHeight());
        player.setVY(0);
        player.reverse = false;
        player.setX((int) player.constantX);
        player.onSurface = true;
        Player.practiceMode = false;
        Level.checkpoints.clear();
    }

    public static void setPlayerPracticemode(boolean b) {
        player.practiceMode = b;
    }

    public static boolean getPlayerPracticemode() {
        return player.practiceMode;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) (g);


            Graphics ground = (Graphics2D) (g);
            ground.setColor(Color.WHITE);

            Graphics debug = (Graphics2D) (g);
            debug.setColor(Color.RED);
            debug.fillRect((int) 300, Globals.floor, 1, 100);


            offsetX = (int) (stationaryX - player.getX());
            int adj = 250;

            bg.draw(g2d, offsetY);
            if (player.reverse) {
                adj += 200;
            }

            if (player.getOffsetY() > offsetY + adj) {
                offsetY += 5;
            }
            if (player.getOffsetY() < offsetY + adj) {
                offsetY -= 5;
            }

            int playerOSY = offsetY;

            player.draw(g2d, playerOSY);

            for (Solid s : lvlSolids) {
                s.draw(g2d, offsetX, offsetY, player);
            }
            for (Spike s : lvlSpikes) {
                s.draw(g2d, offsetX, offsetY, player);
            }
            for (Portal p : lvlPortals) {
                p.draw(g2d, offsetX, offsetY);
            }

            if (!lvlPads.isEmpty()) {
                for (Pad p : lvlPads) {
                    p.draw(g, offsetX, offsetY);
                }
            }

            if (!lvlOrbs.isEmpty()) {
                for (Orb o : lvlOrbs) {
                    o.draw(g, offsetX, offsetY);
                }
            }

            for (Checkpoint c : Level.checkpoints) {
                c.draw(g2d, offsetX, offsetY);
            }

            for (int i = 0; i < playerSquareParticles.size(); i++) {
                SquareParticle s = playerSquareParticles.get(i);
                s.draw(g2d, offsetX, offsetY);
            }
            for (int i = 0; i < shipSquareParticles.size(); i++) {
                SquareParticle s = shipSquareParticles.get(i);
                s.draw(g2d, offsetX, offsetY);
            }

            for (int i = 0; i < padParticles.size(); i++) {
                if (!Util.onScreen(player, lvlPads.get(i).getX())) {
                    continue;
                }
                ArrayList<SquareParticle> lis = padParticles.get(i);
                for (int j = lis.size() - 1; j >= 0; j--) {
                    SquareParticle s = lis.get(j);
                    s.draw(g2d, offsetX, offsetY);
                }
            }

            for (int i = 0; i < portalParticles.size(); i++) {
                if (!Util.onScreen(player, lvlPortals.get(i).getX())) {
                    continue;
                }
                ArrayList<SquareParticle> lis = portalParticles.get(i);
                for (int j = lis.size() - 1; j >= 0; j--) {
                    SquareParticle s = lis.get(j);
                    s.draw(g2d, offsetX, offsetY);
                }
            }
    }

    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        //get mouse coordinate on panel
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
//        System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");

    }

    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_UP) {
            mouseDown = true;
        }


        if (code == KeyEvent.VK_P) {
            if (keys[code] == false) {
                player.practiceMode = true;
            }
        }

        if (code == KeyEvent.VK_Z) {
            if (keys[code] == false) {
                if (player.practiceMode) {
                    boolean b = false;
                    if(player.reverse) {
                        b = true;
                    }
                    Level.checkpoints.add(new Checkpoint(player.getX(), player.getY(), player.getVX(), player.getVY(),player.g, player.initY, player.shipG, player.shipLift, player.getGamemode(), b));
                }
            }
        }

        if (code == KeyEvent.VK_X) {
            if (keys[code] == false){
                if (player.practiceMode) {
                    if (!Level.checkpoints.isEmpty()) {
                        Level.checkpoints.remove(Level.checkpoints.size() - 1);
                    }
                }
            }
        }


        if (code == KeyEvent.VK_ESCAPE) {
            if (keys[code] == false) {

                ControlCenter.pauseGame();

            }
        }



        keys[code] = true;
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_UP) {
            mouseDown = false;
        }
        keys[code] = false;

    }


    public void keyTyped(KeyEvent e) {}

    public static void setOffsetX( int n ) { offsetX = n ; }
    public static void setOffsetY( int n ) { offsetY = n; }


}