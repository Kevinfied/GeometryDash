import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;



public class PauseScreen extends JFrame implements ActionListener {

    static public PausePanel pausemenu = new PausePanel();
    Timer timer = new Timer(1000/60, this);

    public PauseScreen() {
        super("Geometry Dash - GAME PAUSED");
        timer.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(pausemenu);
        setIconImage(Globals.windowIcon.getImage());
        pausemenu.addMouseListener(new MenuMouseListener());

    }

    public void actionPerformed (ActionEvent e) {
        pausemenu.move();
        pausemenu.repaint();
    }

    class MenuMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

}


class PausePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    Timer timer = new Timer(1000/60, this);
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];

    public int playButtonWidth = 200; public int playButtonHeight = 200;
    public int buttonWidth = 100; public int buttonHeight = 100;
    public Rectangle playButtonHitbox = new Rectangle((Globals.SCREEN_WIDTH/2) - (playButtonWidth/2), 400, playButtonWidth, playButtonHeight);
    public BufferedImage playButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/play.png"), playButtonWidth, playButtonHeight);


    public PausePanel() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

//        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(0, 0, 0, 228));
        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);

        g.drawImage(playButtonImg, (Globals.SCREEN_WIDTH/2) - (playButtonWidth/2), 400, playButtonWidth, playButtonHeight, null);
    }

    public void move() {

    }

    public void resume() {
        ControlCenter.enterGame();
    }


    public void exitToMenu() {
        ControlCenter.toMainMenu();
    }


    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_SPACE) {
            ControlCenter.enterGame();


        }
        else if (code == KeyEvent.VK_ESCAPE) {
            ControlCenter.toMainMenu();
        }

        keys[code] = true;
    }


    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        keys[code] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}

