import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PauseScreen extends JFrame implements ActionListener {

    static public PausePanel pausemenu = new PausePanel();
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


class PausePanel extends JPanel implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    Timer timer = new Timer(1000/60, this);
    boolean[] keys = new boolean[KeyEvent.KEY_LAST + 1];

    public int playButtonWidth = 200; public int playButtonHeight = 200;
    public int buttonWidth = 175; public int buttonHeight = 175;
    public Rectangle playButtonHitbox = new Rectangle((Globals.SCREEN_WIDTH/2) - (playButtonWidth/2), 400, playButtonWidth, playButtonHeight);
    public Rectangle menuButtonHitbox = new Rectangle((Globals.SCREEN_WIDTH / 2) + (playButtonWidth / 2) + 175 - (buttonWidth / 2), 400 + 12, buttonWidth, buttonHeight);
    public BufferedImage playButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/play.png"), playButtonWidth, playButtonHeight);
    public BufferedImage menuButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/menu.png"), buttonWidth, buttonHeight);
    public BufferedImage playButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/play.png"), playButtonWidth + 30, playButtonHeight + 30);
    public BufferedImage menuButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/menu.png"), buttonWidth + 30, buttonHeight + 30);
    public BufferedImage practiceButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/practice.png"), buttonWidth, buttonHeight);
    public BufferedImage practiceButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/practice.png"), buttonWidth + 30, buttonHeight + 30);
    public BufferedImage unpracticeButtonImg = Util.resize(Util.loadBuffImage("assets/buttons/unpractice.png"), buttonWidth, buttonHeight);
    public BufferedImage unpracticeButtonHoverImg = Util.resize(Util.loadBuffImage("assets/buttons/unpractice.png"), buttonWidth + 30, buttonHeight + 30);

    public boolean playButtonHover = false;
    public boolean menuButtonHover = false;
    public boolean practiceButtonHover = false;
    Font fontScores, lvlNameFont;
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


//        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(0, 0, 0, 228));
        g.fillRect(0, 0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);

        if (!playButtonHover) {
            g.drawImage(playButtonImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2), 400,  null);
        }
        else {
            System.out.println("HOVERING");
            g.drawImage(playButtonHoverImg, (Globals.SCREEN_WIDTH / 2) - (playButtonWidth / 2) - 15, 400 - 15, null);
        }
        if (!menuButtonHover) {
            g.drawImage(menuButtonImg, (Globals.SCREEN_WIDTH / 2) + (playButtonWidth / 2) + 175 - (buttonWidth / 2), 400 + 12, null);
        }
        else {
            System.out.println("HOVERING");
            g.drawImage(menuButtonHoverImg, (Globals.SCREEN_WIDTH / 2) + (playButtonWidth / 2) + 175 - (buttonWidth / 2) - 15, 400 + 12 - 15, null);
        }

        g.setColor(Color.WHITE);
        Util.drawCenteredString(g, MenuPanel.lvlNames[MenuPanel.targetLevel], new Rectangle(0, 0, Globals.SCREEN_WIDTH, 80), lvlNameFont);


        int percent = ((Integer.parseInt(Util.readFile(Globals.scoreFile, MenuPanel.targetLevel)) * 100)/ (Level.mapWidth * 75));
        g.setColor(new Color(0, 0, 0,  100));
        g.fillRoundRect((Globals.SCREEN_WIDTH/2) - 300, 100, 600, 40, 32, 50);
        g.setColor(Color.GREEN);
        g.fillRoundRect((Globals.SCREEN_WIDTH/2) - 300,100, (600/100) * percent, 40, 32, 50);
        Util.drawCenteredString(g, percent + "%", new Rectangle(0, 82, Globals.SCREEN_WIDTH, 80), fontScores);
    }



    public void move() {

    }

    public void resume() {
        ControlCenter.resumeGame();
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
            ControlCenter.enterGame(MenuPanel.targetLevel);


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
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (playButtonHitbox.contains(mouseX, mouseY)) {
            resume();
        }
        else if (menuButtonHitbox.contains(mouseX, mouseY)) {
            exitToMenu();
        }
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

//        if (practiceButtonHitbox.contains(e.getX(), e.getY())) {
//            practiceButtonHover = true;
//        }
//        else {
//            practiceButtonHover = false;
//        }
    }


}

