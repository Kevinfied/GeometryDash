// Main.java

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JFrame implements ActionListener {

    GamePanel geometryDash = new GamePanel();
    Menu menu = new Menu();

    public Main() {

        super("Geometry Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(geometryDash);
//        pack();
        setVisible(true);



    }

    public void actionPerformed(ActionEvent e) {
        geometryDash.move();
        geometryDash.repaint();
    }

    public static void main(String[] args) {
        Main geometryDash = new Main();
    }
}