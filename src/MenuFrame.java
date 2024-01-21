import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuFrame extends JFrame implements ActionListener {

    static public MenuPanel menu = new MenuPanel();

    public MenuFrame() {
        super("Geometry Dash");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(menu);
        // Add mouse listener to the MenuPanel
        menu.addMouseListener(new MenuMouseListener());

        // Set up a timer to trigger actionPerformed
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Repaint the menu panel on timer events
        menu.repaint();
    }

    class MenuMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }
}
