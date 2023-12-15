// GamePanel.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer;
    Player player;
    Image background;

    boolean mouseDown = false;
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];
    public GamePanel() {
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        requestFocus();
        timer = new Timer(1000/60, this);
        player = new Player(25, 400, 50, 50);
        background = new ImageIcon("assets/background/background1.png").getImage();



        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        move();
        changeGamemode();
        repaint();
    }

    public void move() {
        player.move();
        if(mouseDown) {
            player.thrust();
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
        ground.fillRect(0, 400+player.getHeight()/2, Globals.SCREEN_WIDTH, 1);

        player.draw(g2d);

    }



    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        //give mouse coordinate on panel
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
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