// Main.java

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame implements ActionListener {

    static GamePanel geometryDash = new GamePanel(Globals.map1, Globals.StereoMadnessSound);


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
            geometryDash.mapReload(Globals.map1, Globals.StereoMadnessSound);
            Util.startSound(Globals.lvl1Sound);

            Util.stopSound(Globals.lvl2Sound);
            Util.stopSound(Globals.lvl3Sound);

        } else if (lv == 2) {
            geometryDash.mapReload(Globals.map2, Globals.BaseAfterBaseSounds);
            Util.startSound(Globals.lvl2Sound);

            Util.stopSound(Globals.lvl1Sound);
            Util.stopSound(Globals.lvl3Sound);

        } else if (lv == 3) {
            geometryDash.mapReload(Globals.map3, Globals.JumperSound);
            Util.startSound(Globals.lvl3Sound);

            Util.stopSound(Globals.lvl2Sound);
            Util.stopSound(Globals.lvl1Sound);
        }

        geometryDash.timer.start();
    }

    public static void stopGameSound(){
        Util.stopSound(Globals.lvl1Sound);
        Util.stopSound(Globals.lvl2Sound);
        Util.stopSound(Globals.lvl3Sound);
    }
    public static void startGameSound(int lv) {
        if (lv == 1) {
            Util.startSound(Globals.lvl1Sound);

            Util.stopSound(Globals.lvl2Sound);
            Util.stopSound(Globals.lvl3Sound);

        }

        else if (lv == 2) {
            Util.startSound(Globals.lvl2Sound);

            Util.stopSound(Globals.lvl1Sound);
            Util.stopSound(Globals.lvl3Sound);

        }

        else if (lv == 3) {
            Util.startSound(Globals.lvl3Sound);

            Util.stopSound(Globals.lvl2Sound);
            Util.stopSound(Globals.lvl1Sound);
        }

    }


}
