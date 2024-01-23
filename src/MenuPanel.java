import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Set;
public class MenuPanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    public static String screen = "menu";
    public static int targetLevel = 1;
    BufferedImage title;
    BufferedImage background;
    BufferedImage startButton;
    Rectangle buttonHitbox;
    BufferedImage backgroundImg = Util.loadBuffImage("assets/background/stereoBG.png");
    BufferedImage groundImg = Util.loadBuffImage("assets/ground/ground1.png");

    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];
    public Font fontLocal, fontSys, fontScores, lvlNameFont;

    Background bg1 = new Background(backgroundImg, groundImg);
    Timer timer = new Timer(1000/60, this);
    int buttonWidth = 250; int buttonHeight = 250;

    boolean hover = false;
    boolean mouseDown = false;

    int titleWidth = 1050; int titleHeight = 120;

    int switchButtonWidth = 75; int switchButtonHeight = 100;
    int loadButtonWidth = 600; int loadButtonHeight = 300;
    Rectangle nextButtonHitbox = new Rectangle(Globals.SCREEN_WIDTH - 20 - switchButtonWidth, (Globals.SCREEN_HEIGHT/2) - (switchButtonHeight/2), switchButtonWidth, switchButtonHeight);
    Rectangle prevButtonHitbox = new Rectangle(20, (Globals.SCREEN_HEIGHT/2) - (switchButtonHeight/2), switchButtonWidth, switchButtonHeight);

    Rectangle loadLevelHitbox = new Rectangle((Globals.SCREEN_WIDTH/2) - (loadButtonWidth/2), 100, loadButtonWidth, loadButtonHeight);
    boolean nextButtonHover = false; boolean prevButtonHover = false; boolean loadButtonHover = false;
    BufferedImage nextButtonImg = Util.loadBuffImage("assets/buttons/rightArrow.png");
    BufferedImage prevButtonImg = Util.loadBuffImage("assets/buttons/leftArrow.png");
    BufferedImage backButtonImg = Util.loadBuffImage("assets/buttons/greenArrow.png");
    BufferedImage groundSquare = Util.resize(Util.loadBuffImage("assets/ground/ground1.png"), 250, 250);
    BufferedImage groundLine = Util.resize(Util.loadBuffImage("assets/ground/groundLine.png"), Globals.SCREEN_WIDTH, 5);

    BufferedImage levelSelectBackground = Util.resize(Util.loadBuffImage("assets/background/gradient.png"), Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
    String [] lvlNames = new String[4];
    BufferedImage [] difficultyFaces = new BufferedImage[4];
    BufferedImage easyFace = Util.resize(Util.loadBuffImage("assets/difficultyFaces/easy.png"), 50, 50);
    BufferedImage hardFace = Util.resize(Util.loadBuffImage("assets/difficultyFaces/hard.png"), 50, 50);
    BufferedImage harderFace = Util.resize(Util.loadBuffImage("assets/difficultyFaces/harder.png"), 50, 50);

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

        // FONTS
        fontSys = new Font("Montserat", Font.PLAIN, 32);
        try{
            File fntFile = new File("assets/Fonts/PUSAB.otf");
            fontLocal = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(32f);
            fontScores = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(60f);
            lvlNameFont = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(50f);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        catch(FontFormatException ex){
            System.out.println(ex);
        }


        lvlNames[1] = "Stereo Madness"; lvlNames[2] = "Base After Base"; lvlNames[3] = "Jumper";
        difficultyFaces[1] = easyFace; difficultyFaces[2] = hardFace; difficultyFaces[3] = harderFace;

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

        if (screen == "menu") {
            mainMenuDraw(g);
        }
        else if (screen == "levelSelect") {
            levelSelectDraw(g);
        }
    }

    public void mainMenuDraw(Graphics g) {
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

    public void levelSelectDraw(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);

        g.drawImage(levelSelectBackground, 0, 0, null);
        // buttons
        if (nextButtonHover) {
            g.drawImage(Util.resize(nextButtonImg, switchButtonWidth + 20, switchButtonHeight + 20), nextButtonHitbox.x - 10, nextButtonHitbox.y - 10, null);
        }
        else {
            g.drawImage(Util.resize(nextButtonImg, switchButtonWidth, switchButtonHeight), nextButtonHitbox.x, nextButtonHitbox.y, null);
        }

        if (prevButtonHover) {
            g.drawImage(Util.resize(prevButtonImg, switchButtonWidth + 20, switchButtonHeight + 20), prevButtonHitbox.x - 10, prevButtonHitbox.y - 10, null);
        }
        else {
            g.drawImage(Util.resize(prevButtonImg, switchButtonWidth, switchButtonHeight), prevButtonHitbox.x, prevButtonHitbox.y, null);
        }

        if (loadButtonHover) {
            g.setColor(new Color(0, 0, 0,  100));
            g.fillRoundRect(loadLevelHitbox.x, loadLevelHitbox.y, loadLevelHitbox.width, loadLevelHitbox.height, 50, 30);
        }
        else {
            g.setColor(new Color(0, 0, 0,  50));
            g.fillRoundRect(loadLevelHitbox.x, loadLevelHitbox.y, loadLevelHitbox.width, loadLevelHitbox.height, 50, 30);
        }


        drawCenteredString(g, lvlNames[targetLevel], loadLevelHitbox, lvlNameFont);
        g.drawImage(difficultyFaces[targetLevel], (Globals.SCREEN_WIDTH/2) - 25 ,loadLevelHitbox.y + 200, null);

        for (int i=0; i<5; i++) {
            g.drawImage(groundSquare, (i * 250), Globals.SCREEN_HEIGHT - 150, null);
        }

        g.drawImage(groundLine, 0, Globals.SCREEN_HEIGHT - 150, null);
        g.setColor(new Color(0, 128, 255, 160));
        g.fillRect(0, Globals.SCREEN_HEIGHT - 150, Globals.SCREEN_WIDTH, 150);

    }
    public void move() {
        bg1.move();
    }

    // Implement KeyListener methods
    public void keyPressed(KeyEvent e) {
        // Handle key press events
        int code = e.getKeyCode();

        if (screen == "menu") {
            LevelSelect.curLev = 0;

            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                screen = "levelSelect";
            }

        }
        else if (screen == "levelSelect") {
            if (code == KeyEvent.VK_ESCAPE) {
                screen = "menu";
            }
            else if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                ControlCenter.enterGame(targetLevel);
            }


        }


        keys[code] = true;
    }

    public void keyReleased(KeyEvent e) {
        // Handle key release events

        int code = e.getKeyCode();
        keys[code] = false;
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
        if (!mouseDown) {
            if (screen == "menu") {
                if (getButtonHitbox().contains(mouseX, mouseY)) {
                    // Handle start button click
                    System.out.println("Start button clicked!");
                    screen = "levelSelect";
                }
            }

            else if (screen == "levelSelect") {
                if (loadLevelHitbox.contains(mouseX, mouseY)) {
                    ControlCenter.enterGame(targetLevel);
                } else if (nextButtonHitbox.contains(mouseX, mouseY)) {
                    targetLevel++;
                } else if (prevButtonHitbox.contains(mouseX, mouseY)) {
                    targetLevel--;
                }

                if (targetLevel > 3) {
                    targetLevel = 1;
                }
                if (targetLevel < 1) {
                    targetLevel = 3;
                }

            }
        }



        mouseDown = true;
    }

    public void mouseMoved(MouseEvent e) {
        // Handle mouse move events

        int mouseX = e.getX();
        int mouseY = e.getY();
//        System.out.println("hi there");

        if (screen == "menu") {
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
        else if (screen == "levelSelect") {
            if (nextButtonHitbox.contains(mouseX, mouseY)) {
                nextButtonHover = true;
            }
            else {
                nextButtonHover = false;
            }

            if (prevButtonHitbox.contains(mouseX, mouseY)) {
                prevButtonHover = true;
            }
            else {
                prevButtonHover = false;
            }

            if (loadLevelHitbox.contains(mouseX, mouseY)) {
                loadButtonHover = true;
            }
            else {
                loadButtonHover = false;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        // Handle mouse release events
        mouseDown = false;
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


    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }
}
