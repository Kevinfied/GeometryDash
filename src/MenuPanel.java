import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    BufferedImage title;
    BufferedImage background;
    BufferedImage startButton;
    Rectangle buttonHitbox;
    Timer timer = new Timer(1000/60, this);

    public MenuPanel() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();

        title = Util.loadBuffImage("assets/title.png");
        background = Util.loadBuffImage("assets/background/background1.png");
        startButton = Util.loadBuffImage("assets/logos/playButton.png");
        startButton = Util.resize(startButton, 300, 300);
        buttonHitbox = new Rectangle(475, 500, 300, 100);

//        Timer timer = new Timer(1000/60, this);
//        timer.start();

        // Add listeners
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(title, 300, 100, null);
        g.drawImage(startButton, 475, 500, null);
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
        if (buttonHitbox.contains(mouseX, mouseY)) {
            // Handle start button click
            System.out.println("Start button clicked!");
            ControlCenter.enterGame();
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
    }
}
