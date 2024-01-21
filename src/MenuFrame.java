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
            // Handle mouse click events
            int mouseX = e.getX();
            int mouseY = e.getY();

            // Check if the click is within the start button hitbox
            if (menu.buttonHitbox.contains(mouseX, mouseY)) {
                // Handle start button click
                System.out.println("Start button clicked!");
                ControlCenter.enterGame();
            }
        }
    }
}
