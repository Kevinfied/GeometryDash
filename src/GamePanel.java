// GamePanel.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;
    static Player player;

    Background bg = new Background(Util.loadBuffImage("assets/background/stereoBG.png"));
    ArrayList<String>  lvl1map = new ArrayList<String>();
    ArrayList<Solid> lvl1solids = new ArrayList<Solid>();
    ArrayList <Spike> lvl1spikes = new ArrayList<Spike>();
    ArrayList <Portal> lvl1portals = new ArrayList<Portal>();
    ArrayList <Checkpoint> checkPoints = new ArrayList<Checkpoint>();
    ArrayList <SquareParticle> playerSquareParticles = new ArrayList<SquareParticle>();
    Level lvl1 = new Level(lvl1map);
    public double stationaryX = 300;
    private static int offsetX = 0;
    private static int offsetY = 0;

    static boolean mouseDown = false;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];


    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();

        timer = new Timer(1000/62, this);

        double stationaryX = 300;
        player = new Player(stationaryX, Globals.floor-Globals.solidHeight, 75, 75);


//        for ( int i = 1; i <= 3; i++ ) {
//            String s = "assets/mapMaking/stereo" + Integer.toString(i) + ".png" ;
//            lvl1map.add(s);
//        }
        lvl1map.add("assets/maps/stereoMadness.png");
//        lvl1map.add("assets/maps/dbugBarrier.png");
        lvl1.loadMap();
        lvl1.makeMap();
        lvl1solids = lvl1.solids;
//        lvl1slabs = lvl1.getSlabs();
        lvl1spikes = lvl1.spikes;
        lvl1portals = lvl1.portals;
        lvl1.asciiPrint();



        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        move();

        // for cosmetics
        create();
        destroy();

        changeGamemode();
        repaint();
    }

    public void move() {
        bg.move();
        player.move(lvl1solids, lvl1spikes, lvl1portals);
        if(mouseDown) {
            player.cubeJump();
        }

        if (! playerSquareParticles.isEmpty()) {
            for (SquareParticle s: playerSquareParticles) {
                s.move();
            }
        }
    }

    public void create() {
        if (playerSquareParticles.size() < 100) {
            if( player.getGamemode().equals("cube") && player.onSurface == true) {
                Random rand = new Random();
                double min = 0; // Minimum value (pi/2)
                double max = Math.PI;     // Maximum value (pi)

                playerSquareParticles.add ( new SquareParticle( player.getX(), player.getY() + player.getWidth(), min + Math.random() * (max - min) ,rand.nextInt(6) + 4,-4));
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
        System.out.println(playerSquareParticles);
    }

    public void changeGamemode() { //debug stuff
        if(keys[KeyEvent.VK_1]) {
            player.setGamemode("cube");
            player.setInitY(-38);
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
        if(keys[KeyEvent.VK_A]) {
            player.setVX( 0 );
        }
        else if(keys[KeyEvent.VK_D]) {
            player.setVX( 15 );
        }

        player.setJumpRotate();

        if (keys[KeyEvent.VK_SPACE]) {
            player.ufoJump();
        }

        if (keys[KeyEvent.VK_W]) {
            player.ufoJump();
        }

        if (keys[KeyEvent.VK_P]) {
            player.practiceMode = true;
        }

        if (keys[KeyEvent.VK_Z]) {
            if (player.practiceMode) {
                Level.checkpoints.add(new Checkpoint((int) player.getX(), (int) player.getY(), player.getGamemode()));
            }
        }
        if (keys[KeyEvent.VK_X]) {

            if (player.practiceMode) {
                if (!Level.checkpoints.isEmpty()) {
                    Level.checkpoints.remove(checkPoints.size() - 1);
                }
            }
        }


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
        bg.draw(g2d);


        Graphics ground = (Graphics2D)(g);
        ground.setColor(Color.WHITE);

        Graphics debug = (Graphics2D)(g);
        debug.setColor(Color.RED);
        debug.fillRect((int) 300, Globals.floor, 1, 100);


        offsetX = (int) (stationaryX - player.getX());

        if (player.getOffsetY() > offsetY + 100 ) {
            offsetY += 5;
        }
        if (player.getOffsetY() < offsetY + 100 ) {
            offsetY -= 5;
        }

//        System.out.println( player.getOffsetY() + "  " + offsetY);
        int playerOSY = offsetY ;

        player.draw(g2d, playerOSY);

        for (Solid s: lvl1solids) {
            s.draw(g2d, offsetX, offsetY);
        }
        for (Spike s: lvl1spikes) {
            s.draw(g2d, offsetX, offsetY);
        }
        for (Portal p: lvl1portals) {
            p.draw(g2d, offsetX, offsetY);
        }

        for (Checkpoint c: Level.checkpoints) {
            c.draw(g2d, offsetX, offsetY);
        }

        for (int i = 0; i< playerSquareParticles.size(); i++) {
           SquareParticle s = playerSquareParticles.get(i);
           s.draw(g2d, offsetX, offsetY);
        }

        ground.fillRect(0, Globals.floor - Globals.solidHeight +player.getHeight() + offsetY, Globals.SCREEN_WIDTH, 1);

        if( player.getGamemode().equals( "ship" )) {
            g.fillRect(0, Globals.SHIP_CEILING + offsetY , Globals.SCREEN_WIDTH, 1 );
        }

    }


    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        player.ufoJump();
        //get mouse coordinate on panel
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
    }
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        player.ufoJump();
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        keys[code] = true;
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        keys[code] = false;
    }

    public void keyTyped(KeyEvent e) { }

    public static void setOffsetX( int n ) { offsetX = n ; }
    public static void setOffsetY( int n ) { offsetY = n; }
}