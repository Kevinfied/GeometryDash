/*
    Globals.java
    * This class contains global variables used in the game
 */

import javax.sound.sampled.Clip;
import javax.swing.*;

public class Globals {

    public static final int SCREEN_WIDTH = 1250; // WIDTH OF THE SCREEN
    public static final int SCREEN_HEIGHT = 800; // HEIGHT OF THE SCREEN

    public static final int floor = SCREEN_HEIGHT-50; // THE Y VALUE FOR THE FLOOR
    public static final int gridWidth = 75; // WIDTH OF EACH GRID (used in level load)
    public static final int slabWidth = 75; // WIDTH OF SLABS
    public static final int slabHeight = 37; // HEIGHT OF SLABS
    public static final int solidWidth = 75; // WIDTH OF SOLID BLOCKS
    public static final int solidHeight = 75; // HEIGHT OF SOLID BLOCKS

    public static final int SHIP_CEILING =Globals.floor - Globals.solidHeight * 11 + 35; // THE Y VALUE FOR THE CEILING

    // map image and soundtrack locations
    static String map1 = "assets/maps/stereoMadness.png";
    static String map2 = "assets/maps/baseAfterBase.png";
    static String map3 = "assets/maps/jumper.png";
    static String MainMenuSound = "soundTrack/MainMenu.wav";
    static String StereoMadnessSound = "soundTrack/StereoMadness.wav";
    static String BaseAfterBaseSounds = "soundTrack/BaseAfterBase.wav";
    static String JumperSound = "soundTrack/Jumper.wav";


    // SOUNDS
    public static Clip lvl1Sound = Util.getSound(Globals.StereoMadnessSound);
    public static Clip lvl2Sound = Util.getSound(BaseAfterBaseSounds);
    public static Clip lvl3Sound = Util.getSound(JumperSound);
    public static Clip MenuMusic = Util.getSound(Globals.MainMenuSound);

    public static String scoreFile = "src/scores"; // FILE TO STORE THE HIGHSCORES

    // THE BEST SCORE FOR EACH LEVEL
    public static int lvl1TopScore = Integer.parseInt(Util.readFile(Globals.scoreFile, 1) );
    public static int lvl2TopScore = Integer.parseInt(Util.readFile(Globals.scoreFile, 2) );
    public static int lvl3TopScore = Integer.parseInt(Util.readFile(Globals.scoreFile, 3 ));


    public static ImageIcon windowIcon = new ImageIcon("assets/logos/GeometryDash.png"); // the icon for the window lol
}