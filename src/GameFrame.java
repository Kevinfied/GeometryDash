// Main.java

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame implements ActionListener {

    static GamePanel geometryDash = new GamePanel(Globals.map1, Globals.StereoMadnessSound);
    public static Clip lvl1Sound;
    public static Clip lvl2Sound;
    public static Clip lvl3Sound;

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
            lvl1Sound = playSound(Globals.StereoMadnessSound);
            stopSound(lvl2Sound);
            stopSound(lvl3Sound);

        } else if (lv == 2) {
            geometryDash.mapReload(Globals.map2, Globals.BaseAfterBaseSounds);
            lvl2Sound = playSound(Globals.BaseAfterBaseSounds);
            stopSound(lvl3Sound);
            stopSound(lvl1Sound);
        } else if (lv == 3) {
            geometryDash.mapReload(Globals.map3, Globals.JumperSound);
            lvl3Sound = playSound(Globals.JumperSound);
            stopSound(lvl2Sound);
            stopSound(lvl1Sound);
        }

        geometryDash.timer.start();
    }

    public static void stopSound(Clip music) {
        if (music != null) {
            music.stop();
            music.close();
        }
    }

    public static Clip playSound(String soundFilePath) {
        try {
            // Get the audio input stream from the file
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath));

            // Get a Clip (a data line that can be used for playback)
            Clip music = AudioSystem.getClip();

            // Open the audioInputStream to the clip
            music.open(audioInputStream);

            // Start playing the sound
            music.start();

            return music;

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
