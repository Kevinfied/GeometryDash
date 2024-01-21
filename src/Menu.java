import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;


public class Menu extends JPanel implements ActionListener{


    BufferedImage title;
    BufferedImage background;
    BufferedImage startButton;
    Rectangle buttonHitbox;

    public Menu() {
        super();
        setLayout(null);
        setPreferredSize(new Dimension(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT));
        setFocusable(true);
        requestFocus();

        title = Util.loadBuffImage("assets/logos/title.png");
        background = Util.loadBuffImage("assets/logos/background.png");
        startButton = Util.loadBuffImage("assets/logos/startButton.png");
        startButton = Util.resize(startButton, 300, 100);
        buttonHitbox = new Rectangle(475, 500, 300, 100);

        Timer timer = new Timer(1000/60, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.drawImage(title, 300, 100, null);
        g.drawImage(startButton, 475, 500, null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
