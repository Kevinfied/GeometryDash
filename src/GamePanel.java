// GamePanel.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;

    Player player;

    Image background;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];
    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();
        timer = new Timer(1000/60, this);
        player = new Player(0, 350, 50, 50);
        background = new ImageIcon("assets/background/background1.png").getImage();








        timer.start();
    }

    public void move() {
        player.move();

    }

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

    public void mouseClicked(MouseEvent e) {
//        System.out.println("hi");

    }
    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        System.out.println("mouse down");
        if (player.getGamemode() == "cube") {
            player.thrust();
        }


    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {

    }
    public void mouseDragged(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)(g);
//        g2d.setColor(Color.WHITE);
//        g2d.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        g2d.drawImage(background, 0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT, null);


        Graphics ground = (Graphics2D)(g);
        ground.setColor(Color.WHITE);
        ground.fillRect(0, 325+player.getHeight()/2, Globals.SCREEN_WIDTH, 1);



        player.draw(g2d);
//        player.drawHitbox(g2d);
//        player.drawSpriteBound(g2d);
    }
}