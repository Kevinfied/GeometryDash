// GamePanel.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Set;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;

    Player player;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];
    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        requestFocus();
        timer = new Timer(1000/60, this);
        timer.start();

        player = new Player(100, 100, 50, 50);

    }

    public void move() {

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

    }
    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

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
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 800, 600);

        player.drawHitbox(g2d);
        player.drawSpriteBound(g2d);
    }
}
