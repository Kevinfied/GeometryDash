// GameFrame.java

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/*
GameFrame is where GamePanel gets displayed. It controls music playing and stoppping for each game level and loading the game
-Daisy
 */

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

    public static void RESET() {//reset the player
        geometryDash.resetPlayer();
    }

    //start the level timer, reload map to reset the game
    public static void startTimer(int lv) {
        if (lv == 1) {
            geometryDash.mapReload(Globals.map1, Globals.StereoMadnessSound);

        } else if (lv == 2) {
            geometryDash.mapReload(Globals.map2, Globals.BaseAfterBaseSounds);

        } else if (lv == 3) {
            geometryDash.mapReload(Globals.map3, Globals.JumperSound);
        }

        geometryDash.timer.start();
    }

    //stop all sound
    public static void stopGameSound(){
        Util.stopSound(Globals.lvl1Sound);
        Util.stopSound(Globals.lvl2Sound);
        Util.stopSound(Globals.lvl3Sound);
    }


    //start the level sound
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
