// GamePanel.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;
    static Player player;
    boolean pressFlag = false;
    Background bg = new Background(Util.loadBuffImage("assets/background/stereoBG.png"));
    ArrayList<String>  lvl1map = new ArrayList<String>();
    ArrayList<Solid> lvl1solids = new ArrayList<Solid>();
    ArrayList <Spike> lvl1spikes = new ArrayList<Spike>();
    ArrayList <Portal> lvl1portals = new ArrayList<Portal>();
    ArrayList <Orb> lvl1orbs = new ArrayList<Orb>();
    ArrayList <Checkpoint> checkPoints = new ArrayList<Checkpoint>();
    ArrayList <Pad> lvl1pads = new ArrayList<Pad>();
    ArrayList <SquareParticle> playerSquareParticles = new ArrayList<SquareParticle>();
    ArrayList<ArrayList<SquareParticle>> padParticles = new ArrayList<ArrayList<SquareParticle>>();
    Level lvl1 = new Level(lvl1map);
    ArrayList <SquareParticle> shipSquareParticles = new ArrayList<SquareParticle>();
    public double stationaryX = 300;
    private static int offsetX = 0;
    private static int offsetY = 0;
    public static String screen;
    static boolean mouseDown = false;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];


    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();

        timer = new Timer(1000/25, this);

        screen = "main menu";

        double stationaryX = 300;
        player = new Player(stationaryX, Globals.floor-Globals.solidHeight, 75, 75);


//        for ( int i = 1; i <= 3; i++ ) {
//            String s = "assets/mapMaking/stereo" + Integer.toString(i) + ".png" ;
//            lvl1map.add(s);
//        }
        lvl1map.add("assets/maps/empty.png");
//        lvl1map.add("assets/maps/dbugBarrier.png");
        lvl1.loadMap();
        lvl1.makeMap();
        lvl1solids = lvl1.solids;
        lvl1spikes = lvl1.spikes;
        lvl1portals = lvl1.portals;
        lvl1pads = lvl1.pads;
        lvl1orbs = lvl1.orbs;
        lvl1.asciiPrint();

        if (!lvl1pads.isEmpty()) {
            for (Pad p: lvl1pads) {
                padParticles.add(new ArrayList<SquareParticle>());
            }
        }



        timer.start();
    }

    public void actionPerformed(ActionEvent e) {

        if (screen == "main menu") {
            if (keys[KeyEvent.VK_ENTER]) {
                screen = "game";
            }

        }

        else if (screen == "game") {
            move();

            // for cosmetics
            create();
            destroy();

            changeGamemode();
        }

        repaint();
    }

    public void move() {
        bg.move();
        player.move(lvl1solids, lvl1spikes, lvl1portals, lvl1pads, lvl1orbs);
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

    }

    public void create() {
        Random rand = new Random();
        double min = 0; // Minimum value (pi/2)
        double max = 3 *Math.PI /2;     // Maximum value (pi)
        if (playerSquareParticles.size() < 100) {
            if( player.getGamemode().equals("cube") && player.onSurface == true) {
                int l = rand.nextInt(4) + 4;
                playerSquareParticles.add ( new SquareParticle( player.getX(), player.getY() + player.getHeight() - 5 + rand.nextInt(5), Math.random() * (-Math.PI) ,l, l,0.2* player.getVX(), 20));
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
                lis.add(new SquareParticle(lvl1pads.get(i).getX()+rand.nextInt(Globals.solidWidth), lvl1pads.get(i).getY() + lvl1pads.get(i).getHeight(), Math.PI/2, l, l,-10, rand.nextInt(50) + 100 ));
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

        if(keys[KeyEvent.VK_4]) {
            player.changeYdirection = true;;
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (screen == "main menu") {

            Graphics2D menu = (Graphics2D) (g);
            menu.drawImage(Util.loadBuffImage("assets/background/stereoBG.png"), 0, 0, null);

        }

        if (screen == "game") {
            Graphics2D g2d = (Graphics2D) (g);
            bg.draw(g2d);


            Graphics ground = (Graphics2D) (g);
            ground.setColor(Color.WHITE);

            Graphics debug = (Graphics2D) (g);
            debug.setColor(Color.RED);
            debug.fillRect((int) 300, Globals.floor, 1, 100);


            offsetX = (int) (stationaryX - player.getX());
            int adj = 200;
            if(player.reverse) {
                adj += 100;
            }

            if (player.getOffsetY() > offsetY + adj) {
                offsetY += 5;
            }
            if (player.getOffsetY() < offsetY + adj) {
                offsetY -= 5;
            }

//        offsetY = 0;

//        System.out.println( player.getOffsetY() + "  " + offsetY);
            int playerOSY = offsetY;

            player.draw(g2d, playerOSY);

            for (Solid s : lvl1solids) {
                s.draw(g2d, offsetX, offsetY);
            }
            for (Spike s : lvl1spikes) {
                s.draw(g2d, offsetX, offsetY);
            }
            for (Portal p : lvl1portals) {
                p.draw(g2d, offsetX, offsetY);
            }
            if (!lvl1pads.isEmpty()) {
                for (Pad p : lvl1pads) {
                    p.draw(g, offsetX, offsetY);
                }
            }
            if (!lvl1orbs.isEmpty()) {
                for (Orb o : lvl1orbs) {
                    o.draw(g, offsetX, offsetY);
                }
            }

//        System.out.print("[");
            for (Checkpoint c : Level.checkpoints) {
                c.draw(g2d, offsetX, offsetY);
//            System.out.print(c.toString() + ", ");
            }
//        System.out.println("]");
            for (int i = 0; i < playerSquareParticles.size(); i++) {
                SquareParticle s = playerSquareParticles.get(i);
                s.draw(g2d, offsetX, offsetY);
            }
            for (int i = 0; i < shipSquareParticles.size(); i++) {
                SquareParticle s = shipSquareParticles.get(i);
                s.draw(g2d, offsetX, offsetY);
            }

            for (int i = 0; i < padParticles.size(); i++) {
                ArrayList<SquareParticle> lis = padParticles.get(i);
                for (int j = lis.size() - 1; j >= 0; j--) {
                    SquareParticle s = lis.get(j);
                    s.draw(g2d, offsetX, offsetY);
                }
            }

            ground.fillRect(0, Globals.floor - Globals.solidHeight + player.getHeight() + offsetY, Globals.SCREEN_WIDTH, 1);

            if (player.getGamemode().equals("ship")) {
                g.fillRect(0, Globals.SHIP_CEILING + offsetY, Globals.SCREEN_WIDTH, 1);
            }
        }
    }


    public void mousePressed(MouseEvent e) {
        mouseDown = true;
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

        if (code == KeyEvent.VK_P) {
            if (keys[code] == false) {
                player.practiceMode = true;
            }
        }

        if (code == KeyEvent.VK_Z) {
            if (keys[code] == false) {
                if (player.practiceMode) {
                    Level.checkpoints.add(new Checkpoint(player.getX(), player.getY(), player.getVX(), player.getVY(), player.getGamemode()));
                }
            }
        }

        if (code == KeyEvent.VK_X) {
            if (keys[code] == false){
//                System.out.println("HIHIHIHIHIHIHI");
                if (player.practiceMode) {
                    if (!Level.checkpoints.isEmpty()) {
                        Level.checkpoints.remove(Level.checkpoints.size() - 1);
                    }
                }
            }
        }

        keys[code] = true;


    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        keys[code] = false;

    }

    public void keyTyped(KeyEvent e) {}

    public static void setOffsetX( int n ) { offsetX = n ; }
    public static void setOffsetY( int n ) { offsetY = n; }
}