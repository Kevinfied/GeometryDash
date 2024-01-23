import javax.sound.sampled.Clip;
import javax.swing.*;

public class Globals {

    public static final int SCREEN_WIDTH = 1250;
    public static final int SCREEN_HEIGHT = 800;

//    public static final int floor = SCREEN_HEIGHT-150;
    public static final int floor = SCREEN_HEIGHT-50;
    public static final int gridWidth = 75;
    public static final int slabWidth = 75;
    public static final int slabHeight = 37;
    public static final int solidWidth = 75;
    public static final int solidHeight = 75;

    public static final int SHIP_CEILING =Globals.floor - Globals.solidHeight * 11 + 35;
//    static String map1 = "assets/maps/stereoMadness.png";
    static String map1 = "assets/maps/stereoMadness.png";
    static String map2 = "assets/maps/baseAfterBase.png";
    static String map3 = "assets/maps/jumper.png";
    static String MainMenuSound = "soundTrack/MainMenu.wav";
    static String StereoMadnessSound = "soundTrack/StereoMadness.wav";
    static String BaseAfterBaseSounds = "soundTrack/BaseAfterBase.wav";
    static String JumperSound = "soundTrack/Jumper.wav";

    public static Clip lvl1Sound = Util.getSound(Globals.StereoMadnessSound);
    public static Clip lvl2Sound = Util.getSound(BaseAfterBaseSounds);
    public static Clip lvl3Sound = Util.getSound(JumperSound);
    public static Clip MenuMusic = Util.getSound(Globals.MainMenuSound);
    public static String scoreFile = "src/scores";

    public static int lvl1TopScore = Integer.parseInt(Util.readFile(Globals.scoreFile, 1) );
    public static int lvl2TopScore = Integer.parseInt(Util.readFile(Globals.scoreFile, 2) );
    public static int lvl3TopScore = Integer.parseInt(Util.readFile(Globals.scoreFile, 3 ));





    public static ImageIcon windowIcon = new ImageIcon("assets/logos/GeometryDash.png");
}
