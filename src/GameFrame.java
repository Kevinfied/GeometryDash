// Main.java

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFrame extends JFrame implements ActionListener {

//   static GamePanel geometryDash = new GamePanel();


    static GamePanel level1 = new GamePanel(LevelSelect.map1);
    static GamePanel level2 = new GamePanel(LevelSelect.map2);
    static GamePanel level3 = new GamePanel(LevelSelect.map3);

    public GameFrame() {

        super("Geometry Dash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT);
        add(level1);
        add(level2);
        add(level3);
        setIconImage(Globals.windowIcon.getImage());

    }

    public void actionPerformed(ActionEvent e) {
        if (LevelSelect.curLev == 1) {
            level1.move();
            level1.repaint();
        }

        else if (LevelSelect.curLev == 2) {
            level2.move();
            level2.repaint();
        }

        else if (LevelSelect.curLev == 3) {
            level3.move();
            level3.repaint();
        }
//        geometryDash.move();
//        geometryDash.repaint();
    }

    public static void stopTimer() {
        level1.timer.stop();
        level2.timer.stop();
        level3.timer.stop();
    }

    public static void RESET() {
        level1.resetPlayer();
        level2.resetPlayer();
        level3.resetPlayer();
    }

    public static void startTimer(int lv) {
        if (lv == 1) {
            level1.timer.start();
        }
        else if (lv == 2) {
            level2.timer.start();
        }
        else if (lv == 3) {
            level3.timer.start();
        }
    }

}