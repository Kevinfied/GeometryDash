// Main.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Main extends JFrame {

    GamePanel geometryDash = new GamePanel();

    public Main() {

        super("Geometry Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(geometryDash);
        setVisible(true);



    }

    public static void main(String[] args) {
        Main geometryDash = new Main();
    }
}