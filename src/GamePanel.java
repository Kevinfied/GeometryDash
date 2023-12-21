// GamePanel.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;
    Player player;
    Image background;
    ArrayList<Solid> lvl1solids = new ArrayList<Solid>();
    public double stationaryX;

    boolean mouseDown = false;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];

    ArrayList<Solid> solids = new ArrayList<Solid>();
    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();
//        Solid s1 = new Solid(300, 400, "solid");
//        Solid s2 = new Solid(400, 400, "solid");
//        Solid s3 = new Solid(500, 400, "solid");
//        Solid s4 = new Solid(500, 140, "solid");
//        solids.add(s1); solids.add(s2); solids.add(s3); solids.add(s4);
        timer = new Timer(1000/60, this);
        double stationaryX = 300;
        player = new Player(stationaryX, Globals.floor-Solid.height, 75, 75);
        background = new ImageIcon("assets/background/background1.png").getImage();

        Level lvl1 = new Level("assets/mapMaking/map1.png");
        lvl1.loadMap();
        lvl1.makeMap();
        lvl1solids = lvl1.getSolids();
        lvl1.asciiPrint();


        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        move();
        changeGamemode();
        repaint();
    }

    public void move() {
        player.move(solids);
        if(mouseDown) {
            player.thrust();
        }

        for(Solid s: solids) {
            player.collideSolid(s);
        }
        for (Solid s: lvl1solids) {
            player.collideSolid(s);
        }

    }

    public void changeGamemode() {
        if(keys[KeyEvent.VK_1]) {
            player.setGamemode("cube");
            player.setInitY(-30);
        }
        if(keys[KeyEvent.VK_2]) {
            player.setGamemode("ship");
            player.setInitY(-15);
        }

        player.setJumpRotate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);

        g2d.drawImage(background, 0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT, null);

        Graphics ground = (Graphics2D)(g);
        ground.setColor(Color.WHITE);
        ground.fillRect(0, Globals.floor - Solid.height +player.getHeight(), Globals.SCREEN_WIDTH, 1);

        player.draw(g2d);
        int offset = (int) (stationaryX - player.getX());
        for(Solid s: solids) {
            s.draw(g2d, offset);
        }

        for (Solid s: lvl1solids) {
            s.draw(g2d, offset);

            System.out.println(s.getY());
        }



    }



    public void mousePressed(MouseEvent e) {
        mouseDown = true;
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
    public void mouseClicked(MouseEvent e) {}
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

    public void keyTyped(KeyEvent e) {
    }




}