import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class LevelSelect extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    BufferedImage L1;
    BufferedImage L2;
    BufferedImage L3;

    static String map1 = "assets/maps/stereoMadness.png";
    static String map2 = "assets/maps/baseAfterBase.png";
    static String map3 = "assets/maps/jumper.png";

    Rectangle nextLev = new Rectangle(700, 350, 200, 200);
    Rectangle prevLev = new Rectangle(100, 350, 200, 200);


    public int playButtonWidth = 400; public int playButtonHeight = 300;
    Rectangle playButton = new Rectangle((Globals.SCREEN_WIDTH/2) - (playButtonWidth/2), (Globals.SCREEN_HEIGHT/2) - (playButtonHeight/2), playButtonWidth, playButtonHeight);
    public static int curLev = 1;

    BufferedImage groundImg = Util.loadBuffImage("assets/ground/ground1.png");

    Timer timer = new Timer(1000/60, this);
    int buttonWidth = 250; int buttonHeight = 250;

    boolean hover = false;

    int titleWidth = 1050; int titleHeight = 120;
    public LevelSelect() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();

//        title = Util.loadBuffImage("assets/logos/title.png");
//        background = Util.loadBuffImage("assets/background/background1.png");
//        startButton = Util.loadBuffImage("assets/logos/playButton.png");
//        startButton = Util.resize(startButton, buttonWidth, buttonHeight);
//        title = Util.resize(title, titleWidth, titleHeight);

        Timer timer = new Timer(1000/60, this);
        timer.start();

        // Add listeners
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public Rectangle getButtonHitbox() {
        return new Rectangle(Globals.SCREEN_WIDTH / 2 - (buttonWidth/2), Globals.SCREEN_HEIGHT/2 - (buttonHeight/2), buttonWidth, buttonHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);



        g.setColor(Color.BLUE);


        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);


        g.setColor(Color.WHITE);
        g.drawRect(700, 350, 200, 200);
        g.drawRect(100, 350, 200, 200);
        g.drawRect((Globals.SCREEN_WIDTH/2) - (playButtonWidth/2), (Globals.SCREEN_HEIGHT/2) - (playButtonHeight/2), playButtonWidth, playButtonHeight);


    }
    public void move() {

    }

    // Implement KeyListener methods
    public void keyPressed(KeyEvent e) {
        // Handle key press events
    }

    public void keyReleased(KeyEvent e) {
        // Handle key release events
    }

    public void keyTyped(KeyEvent e) {
        // Handle key typed events
    }

    // Implement ActionListener method
    public void actionPerformed(ActionEvent e) {
        // Handle timer events
    }

    // Implement MouseListener methods
    public void mouseClicked(MouseEvent e) {
        // Handle mouse click events
    }

    public void mousePressed(MouseEvent e) {
        // Handle mouse click events
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println(curLev);

        // Check if the click is within the start button hitbox
        if (playButton.contains(mouseX, mouseY)) {
            // Handle start button click
            System.out.println("Start button clicked!");
            ControlCenter.enterGame(curLev);
        }

        else if (nextLev.contains(mouseX, mouseY) && curLev!=3) {
            curLev +=1;
            System.out.println(curLev);
        }

        else if (prevLev.contains(mouseX, mouseY) && curLev!=1) {
            curLev -=1;
            System.out.println(curLev);
        }
        // Handle mouse press events
    }

    public void mouseReleased(MouseEvent e) {
        // Handle mouse release events
    }

    public void mouseEntered(MouseEvent e) {
        // Handle mouse enter events
    }

    public void mouseExited(MouseEvent e) {
        // Handle mouse exit events
    }

    // Implement MouseMotionListener methods
    public void mouseDragged(MouseEvent e) {
        // Handle mouse drag events
    }

    public void mouseMoved(MouseEvent e) {
        // Handle mouse move events

        int mouseX = e.getX();
        int mouseY = e.getY();
//        System.out.println("hi there");
        if (getButtonHitbox().contains(mouseX, mouseY)) {
            // hover
//            System.out.println("HOVERING");
            hover = true;
        } else {
            // not hover
            hover = false;
//            System.out.println("not hovering");
        }
    }
}
