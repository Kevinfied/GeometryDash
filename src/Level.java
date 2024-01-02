import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
    ArrayList<String> map;
    private int w = 1000;
    private int h = 27;

    ArrayList<Solid> solids;
    ArrayList<Slab> slabs;
    ArrayList<Spike> spikes;
    ArrayList<Portal> portals;
    int[][] mapArr;


    // constructor
    public Level(ArrayList<String> map) {
        this.map = map;


        solids = new ArrayList<Solid>();
        slabs = new ArrayList<Slab>();
        spikes = new ArrayList<Spike>();
        portals = new ArrayList<Portal>();
        mapArr = new int[1000][27];
    }


    public void loadMap(){
        /*
            Table:
            1 - Solids
            2 - Spike (upright)
            3 - Slabs
            4 - Portals
         */
        int wIndex = 0;
        for (String s: map) {
            BufferedImage pic = Util.loadBuffImage(s);
            int tempw = pic.getWidth();
            int temph = pic.getHeight();
            System.out.printf("width: %d\n", tempw);
            System.out.printf("height: %d\n", temph);


            for (int x = wIndex; x < tempw + wIndex; x++) {
                for (int y = 0; y < temph; y++) {
                    int c = pic.getRGB(x - wIndex, y);
                    int v = 0;
                    if (c == 0xFF0026FF) {
//                    solids[y][x] = new Solid( x*50, y*50, "solid");
                        v = 1;
                    } else if (c == 0xFFFF0000) {
                        v = 2;
                    } else if (c == 0xFF00FFFF) {
                        v = 3;
                    } else if (c == 0xFF00FF21) {
                        v = 4;
                    }
                    mapArr[x][y] = v;
                }
            }

            wIndex += tempw;
        }
//        return map;
    }

    public void makeMap() {
        // ground is 400
        // 7 from the bottom is the ground in mapArr

        // h * 50 = bottom

        // h - y
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                int target = mapArr[x][y];
                if (target == 1) {
                    Solid s = new Solid(x*Solid.width , Globals.floor - ((h-y-7) * Solid.height) - Solid.height);
                    solids.add(s);
                }
                else if (target == 2) {
                    Spike s = new Spike(x*Solid.width , Globals.floor - ((h-y-7) * Solid.height) - Solid.height, 0);
                    spikes.add(s);
                }
                else if (target == 3) {
                    Slab s = new Slab(x*Slab.width , Globals.floor - ((h-y-7) * Solid.height) - Solid.height);
                    slabs.add(s);
                }

                else if (target == 4) {
                    Portal p = new Portal( x* Portal.width, Globals.floor - ((h-y-7) * Portal.height) - Portal.height, "toShip" );
                    portals.add(p);
                }

            }

            System.out.println(portals);
        }


//        for (int i=0; i<w; i)
//        Solid s = new Solid ( x*50, y*50, "solid" );
    }

    public void drawLevel() {

    }

    public int[][] getMapArr() {
        return mapArr;
    }


    public ArrayList<Solid> getSolids() {
        return solids;
    }
    public ArrayList<Slab> getSlabs() {
        return slabs;
    }
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
    public ArrayList<Portal> getPortals() {return portals;}

    public void asciiPrint() {
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                System.out.printf("%d", mapArr[x][y]);
            }
            System.out.println();
        }
    }

}
