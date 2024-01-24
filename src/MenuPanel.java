/*
    * MenuPanel.java
    *
    * This class is responsible for the main menu and the level selection menu.
 */

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

    public static String screen = "menu"; // This keeps track of which screen the user is on
    public static int targetLevel = 1; // the level that the user is looking at. also used to determine which level is loaded/to load

    // Images
    BufferedImage title;
    BufferedImage background;
    BufferedImage startButton;
    Rectangle buttonHitbox;
    BufferedImage backgroundImg = Util.loadBuffImage("assets/background/stereoBG.png");
    BufferedImage groundImg = Util.loadBuffImage("assets/ground/ground1.png");
    BufferedImage nextButtonImg = Util.loadBuffImage("assets/buttons/rightArrow.png");
    BufferedImage prevButtonImg = Util.loadBuffImage("assets/buttons/leftArrow.png");
    BufferedImage backButtonImg = Util.loadBuffImage("assets/buttons/greenArrow.png");
    BufferedImage groundSquare = Util.resize(Util.loadBuffImage("assets/ground/ground1.png"), 250, 250);
    BufferedImage groundLine = Util.resize(Util.loadBuffImage("assets/ground/groundLine.png"), Globals.SCREEN_WIDTH, 5);

    BufferedImage levelSelectBackground = Util.resize(Util.loadBuffImage("assets/background/gradient.png"), Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
    BufferedImage [] difficultyFaces = new BufferedImage[4];
    BufferedImage easyFace = Util.resize(Util.loadBuffImage("assets/difficultyFaces/easy.png"), 50, 50);
    BufferedImage hardFace = Util.resize(Util.loadBuffImage("assets/difficultyFaces/hard.png"), 50, 50);
    BufferedImage harderFace = Util.resize(Util.loadBuffImage("assets/difficultyFaces/harder.png"), 50, 50);



    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1]; // This array keeps track of which keys are pressed
    public Font fontLocal, fontSys, fontScores, lvlNameFont; // fonts used in the menu

    Background bg1 = new Background(backgroundImg, groundImg); // background object. used to draw the background
    Timer timer = new Timer(1000/60, this); // timer


    int buttonWidth = 250; int buttonHeight = 250; // dimensions of the start button in main menu
    boolean hover = false; // whether or not the mouse is hovering over the start button in main menu
    boolean mouseDown = false; // if mouse is held

    int titleWidth = 1050; int titleHeight = 120; // dimensions of the title image

    int switchButtonWidth = 75; int switchButtonHeight = 100; // dimensions of the (hitboxes) next/prev buttons in level select
    int loadButtonWidth = 600; int loadButtonHeight = 300; // dimensions of the big button that loads the level
    // the bounding boxes of the buttons in level select
    Rectangle nextButtonHitbox = new Rectangle(Globals.SCREEN_WIDTH - 20 - switchButtonWidth, (Globals.SCREEN_HEIGHT/2) - (switchButtonHeight/2), switchButtonWidth, switchButtonHeight);
    Rectangle prevButtonHitbox = new Rectangle(20, (Globals.SCREEN_HEIGHT/2) - (switchButtonHeight/2), switchButtonWidth, switchButtonHeight);
    Rectangle loadLevelHitbox = new Rectangle((Globals.SCREEN_WIDTH/2) - (loadButtonWidth/2), 100, loadButtonWidth, loadButtonHeight);

    // If mouse is hovered over the buttons
    boolean nextButtonHover = false; boolean prevButtonHover = false; boolean loadButtonHover = false;

    public static String [] lvlNames = new String[4]; // Array of level names. Used to display the level name in level select

    // Constructor
    public MenuPanel() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();

        // Load and resize images
        title = Util.loadBuffImage("assets/logos/title.png");
        background = Util.loadBuffImage("assets/background/background1.png");
        startButton = Util.loadBuffImage("assets/logos/playButton.png");
        startButton = Util.resize(startButton, buttonWidth, buttonHeight);
        title = Util.resize(title, titleWidth, titleHeight);


        // FONTS
        fontSys = new Font("Montserat", Font.PLAIN, 32);
        try{
            File fntFile = new File("assets/Fonts/PUSAB.otf");
            fontLocal = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(32f);
            fontScores = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(30f);
            lvlNameFont = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(50f);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        catch(FontFormatException ex){
            System.out.println(ex);
        }


        lvlNames[1] = "Stereo Madness"; lvlNames[2] = "Base After Base"; lvlNames[3] = "Jumper"; // Set level names
        difficultyFaces[1] = easyFace; difficultyFaces[2] = hardFace; difficultyFaces[3] = harderFace; // Set difficulty faces

        // Add listeners
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public Rectangle getButtonHitbox() { // Returns the hitbox of the start button
        return new Rectangle(Globals.SCREEN_WIDTH / 2 - (buttonWidth/2), Globals.SCREEN_HEIGHT/2 - (buttonHeight/2), buttonWidth, buttonHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (screen == "menu") { // If the user is on the main menu
            mainMenuDraw(g);
        }
        else if (screen == "levelSelect") { // If the user is on the level select menu
            levelSelectDraw(g);
        }
    }

    // Draw method for main menu
    public void mainMenuDraw(Graphics g) {
        bg1.mainMenuDraw(g); // draw background
        g.drawImage(title, (Globals.SCREEN_WIDTH - titleWidth) / 2, 75, null); // draw title

        // hover effect for start button
        if (!hover) {
            g.drawImage(startButton, Globals.SCREEN_WIDTH / 2 - (buttonWidth / 2), Globals.SCREEN_HEIGHT / 2 - (buttonHeight / 2), null);
        }
        else {
            g.drawImage(startButton, Globals.SCREEN_WIDTH / 2 - (buttonWidth / 2) - 15, Globals.SCREEN_HEIGHT / 2 - (buttonHeight / 2) - 15, buttonWidth + 30, buttonHeight + 30,null);
        }
    }

    public void levelSelectDraw(Graphics g) {
        g.drawImage(levelSelectBackground, 0, 0, null); // draw background

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



        // draw level name + difficulty face
        Util.drawCenteredString(g, lvlNames[targetLevel], loadLevelHitbox, lvlNameFont);
        g.drawImage(difficultyFaces[targetLevel], (Globals.SCREEN_WIDTH/2) - 25 ,loadLevelHitbox.y + 200, null);

        // GROUND
        for (int i=0; i<5; i++) {
            g.drawImage(groundSquare, (i * 250), Globals.SCREEN_HEIGHT - 150, null);
        }
        g.drawImage(groundLine, 0, Globals.SCREEN_HEIGHT - 150, null);
        g.setColor(new Color(0, 128, 255, 160));
        g.fillRect(0, Globals.SCREEN_HEIGHT - 150, Globals.SCREEN_WIDTH, 150);


        // draw best percentage bar
        int percent = Math.min(((Integer.parseInt(Util.readFile(Globals.scoreFile, targetLevel)) * 100)/ (Level.mapWidth * 75)) , 100);
        g.setColor(new Color(0, 0, 0,  100));
        g.fillRoundRect((Globals.SCREEN_WIDTH/2) - 300,Globals.SCREEN_HEIGHT - 272, 600, 40, 32, 50);
        g.setColor(Color.GREEN);
        g.fillRoundRect((Globals.SCREEN_WIDTH/2) - 300,Globals.SCREEN_HEIGHT - 272, (600/100) * percent, 40, 32, 50);
        Util.drawCenteredString(g, percent + "%", new Rectangle(0, Globals.SCREEN_HEIGHT - 325, Globals.SCREEN_WIDTH, 150), fontScores);
    }


    public void move() {
        bg1.move();
    }

    // Implement KeyListener methods
    public void keyPressed(KeyEvent e) {
        // Handle key press events
        int code = e.getKeyCode();

        if (screen == "menu") { // while in menu
            LevelSelect.curLev = 0;
            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) { // space and enter goes to level selection screen
                screen = "levelSelect";
            }
        }

        else if (screen == "levelSelect") { // while in level select
            if (code == KeyEvent.VK_ESCAPE) { // escape goes back to main menu
                screen = "menu";
            }
            else if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) { // space and enter loads the level
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
                if (getButtonHitbox().contains(mouseX, mouseY)) { // start button
                    // Handle start button click
//                    System.out.println("Start button clicked!");
                    screen = "levelSelect";
                }
            }

            else if (screen == "levelSelect") {
                // Scroll through levels
                if (loadLevelHitbox.contains(mouseX, mouseY)) {
                    ControlCenter.enterGame(targetLevel);
                }
                else if (nextButtonHitbox.contains(mouseX, mouseY)) {
                    targetLevel++;
                }
                else if (prevButtonHitbox.contains(mouseX, mouseY)) {
                    targetLevel--;
                }

                targetLevel = (targetLevel +2 ) % 3 + 1; // wrap around
            }
        }

        mouseDown = true;
    }

    public void mouseMoved(MouseEvent e) {
        // Handle mouse move events
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Checking if the mouse is hovering over buttons
        if (screen == "menu") { // while in menu
            if (getButtonHitbox().contains(mouseX, mouseY)) { // PLAY BUTTON
                // hover
                hover = true;
            } else {
                // not hover
                hover = false;
            }
        }
        else if (screen == "levelSelect") { // while in level select
            if (nextButtonHitbox.contains(mouseX, mouseY)) { // NEXT BUTTON
                nextButtonHover = true;
            }
            else {
                nextButtonHover = false;
            }
            if (prevButtonHitbox.contains(mouseX, mouseY)) { // PREV BUTTON
                prevButtonHover = true;
            }
            else {
                prevButtonHover = false;
            }
            if (loadLevelHitbox.contains(mouseX, mouseY)) { // LOAD LEVEL BUTTON
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

}
