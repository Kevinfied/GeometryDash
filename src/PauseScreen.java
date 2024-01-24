/*
    PauseScreen.java

    This contains two classes, the frame and panel for the pause screen.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


// This is the frame for the pause screen
// nothing much happens besides being the frame
public class PauseScreen extends JFrame implements ActionListener {
    static public PausePanel pausemenu = new PausePanel(); // the panel for the pause screen
    Timer timer = new Timer(1000/60, this);
    public PauseScreen() {
        super("Geometry Dash - GAME PAUSED");
        timer.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(pausemenu);
        pausemenu.timer.start();
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


// PausePanel
class PausePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {
    Timer timer = new Timer(1000/60, this);
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1]; // key pressed/released
    public int playButtonWidth = 200; public int playButtonHeight = 200; // dimensions of the play button (resume)
    public int buttonWidth = 175; public int buttonHeight = 175; // dimensions of the other buttons

    // The bouding boxes for the buttons
    public Rectangle playButtonHitbox = new Rectangle((Globals.SCREEN_WIDTH/2) - (playButtonWidth/2), 400, playButtonWidth, playButtonHeight);
    public Rectangle menuButtonHitbox = new Rectangle((Globals.SCREEN_WIDTH / 2) + (playButtonWidth / 2) + 175 - (buttonWidth / 2), 400 + 12, buttonWidth, buttonHeight);
    public Rectangle practiceButtonHitbox = new Rectangle((Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 175 - (buttonWidth / 2), 400 + 12, buttonWidth, buttonHeight);

    // The images for the buttons
    public BufferedImage playButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/play.png"), playButtonWidth, playButtonHeight);
    public BufferedImage menuButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/menu.png"), buttonWidth, buttonHeight);
    public BufferedImage playButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/play.png"), playButtonWidth + 30, playButtonHeight + 30);
    public BufferedImage menuButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/menu.png"), buttonWidth + 30, buttonHeight + 30);
    public BufferedImage practiceButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/practice.png"), buttonWidth, buttonHeight);
    public BufferedImage practiceButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/practice.png"), buttonWidth + 30, buttonHeight + 30);
    public BufferedImage unpracticeButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/unpractice.png"), buttonWidth, buttonHeight);
    public BufferedImage unpracticeButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/unpractice.png"), buttonWidth + 30, buttonHeight + 30);


    // Booleans for if the mouse is hovering over the buttons
    public boolean playButtonHover = false;
    public boolean menuButtonHover = false;
    public boolean practiceButtonHover = false;

    // Boolean for if the mouse is pressed
    boolean mousePressed = false;

    // Fonts
    Font fontScores, lvlNameFont;


    // Constructor
    public PausePanel() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseMotionListener(this);
        timer.start();
        // Load the fonts
        try{
            File fntFile = new File("assets/Fonts/PUSAB.otf");
            fontScores = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(30f);
            lvlNameFont = Font.createFont(Font.TRUETYPE_FONT, fntFile).deriveFont(50f);
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        catch(FontFormatException ex){
            System.out.println(ex);
        }
    }



    public void paint(Graphics g) {
        super.paint(g);


        // background
        g.setColor(new Color(0, 0, 0, 228));
        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);

        // Draw the buttons
        // If the mouse is hovering over the hitbox for the buttons, draw the bigger version for the effect
        if (!playButtonHover) {
            g.drawImage(playButtonImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2), 400,  null);
        }
        else {
            g.drawImage(playButtonHoverImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 15, 400 - 15, null);
        }
        if (!menuButtonHover) {
            g.drawImage(menuButtonImg, (Globals.SCREEN_WIDTH / 2) + (playButtonWidth / 2) + 175 - (buttonWidth / 2), 400 + 12, null);
        }
        else {
            g.drawImage(menuButtonHoverImg, (Globals.SCREEN_WIDTH / 2) + (playButtonWidth / 2) + 175 - (buttonWidth / 2) - 15, 400 + 12 - 15, null);
        }
        if (practiceButtonHover) {
            if (Player.practiceMode) {
                g.drawImage(unpracticeButtonHoverImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 175 - (buttonWidth / 2) - 15, 400 + 12 - 15, null);
            }
            else {
                g.drawImage(practiceButtonHoverImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 175 - (buttonWidth / 2) - 15, 400 + 12 - 15, null);
            }
        }
        else {
            if (Player.practiceMode) {
                g.drawImage(unpracticeButtonImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 175 - (buttonWidth / 2), 400 + 12, null);
            }
            else {
                g.drawImage(practiceButtonImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 175 - (buttonWidth / 2), 400 + 12, null);
            }
        }


        // Drawing the level name and the high score percentage
        g.setColor(Color.WHITE);
        Util.drawCenteredString(g, MenuPanel.lvlNames[MenuPanel.targetLevel], new Rectangle(0, 0, Globals.SCREEN_WIDTH, 80), lvlNameFont);
        int percent = Math.min(((Integer.parseInt(Util.readFile(Globals.scoreFile, MenuPanel.targetLevel)) * 100)/ (Level.mapWidth * 75)), 100);
        g.setColor(new Color(0, 0, 0,  100));
        g.fillRoundRect((Globals.SCREEN_WIDTH/2) - 300, 100, 600, 40, 32, 50);
        g.setColor(Color.GREEN);
        g.fillRoundRect((Globals.SCREEN_WIDTH/2) - 300,100, (600/100) * percent, 40, 32, 50);
        Util.drawCenteredString(g, percent + "%", new Rectangle(0, 82, Globals.SCREEN_WIDTH, 80), fontScores);
    }



    public void move() {
    }

    public void resume() { // used to resume the level
        ControlCenter.resumeGame();
    }


    public void exitToMenu() { // used to exit to the level select screen
        ControlCenter.toMainMenu();
    }

    public void restartLevel() { // restarts the current level
        ControlCenter.enterGame(MenuPanel.targetLevel);
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // pressing space resumes the game and pressing escape exits to the level select screen
        if (code == KeyEvent.VK_SPACE) {
            ControlCenter.enterGame(MenuPanel.targetLevel);
        }
        else if (code == KeyEvent.VK_ESCAPE) {
            ControlCenter.toMainMenu();
        }
        keys[code] = true;
    }


    public void keyReleased(KeyEvent e) { // checking for key releases
        int code = e.getKeyCode();
        keys[code] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // mouse position
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (!mousePressed) {
            if (playButtonHitbox.contains(mouseX, mouseY)) { // resume game button
                System.out.println("PLAYBUTTONPRESSED");
                resume();
            }
            else if (menuButtonHitbox.contains(mouseX, mouseY)) { // menu button
                exitToMenu();
            }
            else if (practiceButtonHitbox.contains(mouseX, mouseY)) { // toggling pratice mode button
                System.out.println("PRESSED");
                if (Player.practiceMode == true) {
                    System.out.println("unpracticing");
                    restartLevel();
                    Player.practiceMode = false;
                } else {
                    Player.practiceMode = true;
                    resume();
                }
            }
        }
        mousePressed = true; // MOUSE IS PRESSED!!!
    }

    @Override
    public void mouseReleased(MouseEvent e) { // MOUSE IS RELEASED!!!!!!!
        mousePressed = false;
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


        // only used to check for mouse hover over the buttons
        if (playButtonHitbox.contains(e.getX(), e.getY())) {
            playButtonHover = true;
        }
        else {
            playButtonHover = false;
        }
        if (menuButtonHitbox.contains(e.getX(), e.getY())) {
            menuButtonHover = true;
        }
        else {
            menuButtonHover = false;
        }
        if (practiceButtonHitbox.contains(e.getX(), e.getY())) {
            practiceButtonHover = true;
        }
        else {
            practiceButtonHover = false;
        }
    }


}

