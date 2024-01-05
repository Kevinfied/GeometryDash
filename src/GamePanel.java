// GamePanel.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;
    static Player player;

    Background bg = new Background(Util.loadBuffImage("assets/background/stereoBG.png"));
    ArrayList<String>  lvl1map = new ArrayList<String>();
    ArrayList<Solid> lvl1solids = new ArrayList<Solid>();
    ArrayList<Slab> lvl1slabs = new ArrayList<Slab>();
    ArrayList <Spike> lvl1spikes = new ArrayList<Spike>();
    ArrayList <Portal> lvl1portals = new ArrayList<Portal>();
    public double stationaryX = 300;
    private static int offsetX = 0;
    private static int offsetY = 0;

    static boolean mouseDown = false;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];

    ArrayList<Solid> solids = new ArrayList<Solid>();
    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();

        timer = new Timer(1000/60, this);
        double stationaryX = 300;
        player = new Player(stationaryX, Globals.floor-Globals.solidHeight, 75, 75);




//        lvl1map.add("assets/mapMaking/dbugSlab.png");
        for ( int i = 1; i <= 3; i++ ) {
            String s = "assets/mapMaking/stereo" + Integer.toString(i) + ".png" ;
            lvl1map.add(s);
        }


        Level lvl1 = new Level(lvl1map);
        lvl1.loadMap();
        lvl1.makeMap();
        lvl1solids = lvl1.getSolids();
        lvl1slabs = lvl1.getSlabs();
        lvl1spikes = lvl1.getSpikes();
        lvl1portals = lvl1.getPortals();
        lvl1.asciiPrint();


        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        move();
        changeGamemode();
        repaint();
    }

    public void move() {
        bg.move();
        player.move(lvl1solids, lvl1slabs, lvl1spikes);
        if(mouseDown) {
            player.cubeJump();
        }

        for(Solid s: solids) {
            player.collideSolid(s);
        }
        for (Solid s: lvl1solids) {
            player.collideSolid(s);
        }

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
//        int offsetY = (int) (Globals.floor- player.getY());
//        offsetY = (int) (Globals.floor - player.getGroundLevel());

        if (player.getOffsetY() > offsetY ) {
            offsetY += 5;
        }
        if (player.getOffsetY() < offsetY ) {
            offsetY -= 5;
        }

//        System.out.println( player.getOffsetY() + "  " + offsetY);
        int playerOSY = offsetY ;


        player.draw(g2d, playerOSY);

        for (Solid s: lvl1solids) {
            s.draw(g2d, offsetX, offsetY);
        }
        for (Slab s: lvl1slabs) {
            s.draw(g2d, offsetX, offsetY);
        }
        for (Spike s: lvl1spikes) {
            s.drawHitbox(g2d, offsetX, offsetY);
        }
        for (Portal p: lvl1portals) {
            p.draw(g2d, offsetX, offsetY);
        }
        ground.fillRect(0, Globals.floor - Globals.solidHeight +player.getHeight() + offsetY, Globals.SCREEN_WIDTH, 1);


    }



    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        player.ufoJump();
        //give mouse coordinate on panel
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