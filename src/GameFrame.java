// Main.java

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFrame extends JFrame implements ActionListener {

//   static GamePanel geometryDash = new GamePanel();


    static GamePanel geometryDash = new GamePanel (Globals.map1);;

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

    public static void stopTimer() {
        geometryDash.timer.stop();
    }

    public static void RESET() {
        geometryDash.resetPlayer();
    }

    public static void startTimer(int lv) {

        if (lv == 1) {
            geometryDash.mapReload(Globals.map1);
        }
        else if (lv == 2) {
            geometryDash.mapReload(Globals.map2);
        }
        else if (lv == 3) {
            geometryDash.mapReload(Globals.map3);
        }

        geometryDash.timer.start();
    }

}