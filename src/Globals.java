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
    static String map1 = "assets/maps/stereoMadness.png";
    static String map2 = "assets/maps/baseAfterBase.png";
    static String map3 = "assets/maps/jumper.png";


    public static ImageIcon windowIcon = new ImageIcon("assets/logos/GeometryDash.png");
}
