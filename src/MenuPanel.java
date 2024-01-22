import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    BufferedImage title;
    BufferedImage background;
    BufferedImage startButton;
    Rectangle buttonHitbox;
    BufferedImage backgroundImg = Util.loadBuffImage("assets/background/stereoBG.png");
    BufferedImage groundImg = Util.loadBuffImage("assets/ground/ground1.png");
    Background bg1 = new Background(backgroundImg, groundImg);
    Timer timer = new Timer(1000/60, this);
    int buttonWidth = 250; int buttonHeight = 250;

    boolean hover = false;

    int titleWidth = 1050; int titleHeight = 120;
    public MenuPanel() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();

        title = Util.loadBuffImage("assets/logos/title.png");
        background = Util.loadBuffImage("assets/background/background1.png");
        startButton = Util.loadBuffImage("assets/logos/playButton.png");
        startButton = Util.resize(startButton, buttonWidth, buttonHeight);
        title = Util.resize(title, titleWidth, titleHeight);

//        Timer timer = new Timer(1000/60, this);
//        timer.start();

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

        bg1.mainMenuDraw(g);
//        g.drawImage(background, 0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT,null);
        g.drawImage(title, (Globals.SCREEN_WIDTH - titleWidth) / 2, 75, null);

        if (!hover) {
//            System.out.println("LOLZERS");
            g.drawImage(startButton, Globals.SCREEN_WIDTH / 2 - (buttonWidth / 2), Globals.SCREEN_HEIGHT / 2 - (buttonHeight / 2), null);
        }
        else {
            g.drawImage(startButton, Globals.SCREEN_WIDTH / 2 - (buttonWidth / 2) - 15, Globals.SCREEN_HEIGHT / 2 - (buttonHeight / 2) - 15, buttonWidth + 30, buttonHeight + 30,null);
        }
    }
    public void move() {
        bg1.move();
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

        // Check if the click is within the start button hitbox
        if (getButtonHitbox().contains(mouseX, mouseY)) {
            // Handle start button click
            System.out.println("Start button clicked!");
            MenuFrame.screen = "level select";
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
