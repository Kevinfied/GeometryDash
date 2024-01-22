// Main.java

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFrame extends JFrame implements ActionListener {

    static GamePanel geometryDash = new GamePanel();

    public GameFrame() {

        super("Geometry Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(geometryDash);
        setIconImage(Globals.windowIcon.getImage());

    }

    public void actionPerformed(ActionEvent e) {
        geometryDash.move();
        geometryDash.repaint();
    }
    public void control(ActionEvent e) {
        setVisible(false);
    }

//    public static void main(String[] args) {
//        GameFrame geometryDash = new GameFrame();
//    }

}