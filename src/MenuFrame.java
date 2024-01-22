import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuFrame extends JFrame implements ActionListener {

    static public MenuPanel menu = new MenuPanel();
    static public LevelSelect levelMenu = new LevelSelect();
    Timer timer;
    public static String screen = "menu";
    public MenuFrame() {

        super("Geometry Dash");
        timer = new Timer(1000/60, this);
        timer.start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);

        add(menu);
        add(levelMenu);


        // Add mouse listener to the MenuPanel
        menu.addMouseListener(new MenuMouseListener());
        levelMenu.addMouseListener(new MenuMouseListener());


        setIconImage(Globals.windowIcon.getImage());
        // Set up a timer to trigger actionPerformed
//        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Repaint the menu panel on timer events
//        System.out.println("MenuFrame.actionPerformed");

        if (screen == "menu") {
            menu.move();
            menu.repaint();
        }
        else if (screen == "level select") {
            levelMenu.move();
            levelMenu.repaint();
        }

    }

    class MenuMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }
}
