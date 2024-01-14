import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
    ArrayList<String> map;
    private int w = 1000;
    private int h = 27;

    ArrayList<Solid> solids;
//    ArrayList<Slab> slabs;
    ArrayList<Spike> spikes;
    ArrayList<Portal> portals;
    ArrayList<Barrier> barriers;
    ArrayList<Orb> orbs;
    ArrayList<Pad> pads;
    public static ArrayList<Checkpoint> checkpoints;
    public int aSolid = 1;
    public int aSpike = 2;
    public int aSlab = 3;
    public int aPortal = 4;

    int[][] mapArr;


    // constructor
    public Level(ArrayList<String> map) {
        this.map = map;

        solids = new ArrayList<Solid>();
//        slabs = new ArrayList<Slab>();
        spikes = new ArrayList<Spike>();
        portals = new ArrayList<Portal>();
        barriers = new ArrayList<Barrier>();
        checkpoints = new ArrayList<Checkpoint>();
        pads = new ArrayList<Pad>();
        orbs = new ArrayList<Orb>();
        mapArr = new int[1000][27];
    }


    public void loadMap(){
        /*
            Table:
            1 - Solids
            2 - Spike (upright)
            3 - Slabs
            4 - small spike (upright)
            5 - Spike (downwards)
            6 - small spike (downwards)
            7 - slab (down)
            10 - ship portal
            11 - cube portal
         */
        int wIndex = 0;
        for (String s: map) {
            BufferedImage pic = Util.loadBuffImage(s);
            int tempw = pic.getWidth();
            int temph = pic.getHeight();

            for (int x = wIndex; x < tempw + wIndex; x++) {
                for (int y = 0; y < temph; y++) {
                    int c = pic.getRGB(x - wIndex, y);
                    int v = 0;
                    if (c == 0xFF0026FF) {
//                    solids[y][x] = new Solid( x*50, y*50, "solid");
                        v = 1;
                    }

                    else if (c == 0xFFFF0000) {
                        v = 2;
                    }

                    else if (c == 0xFF00FFFF) {
                        v = 3;
                    }

                    else if (c == 0xFFFF00DC) {
                        v = 4;
                    }

                    else if (c == 0xFF7F0000) {
                        v = 5;
                    }
                    else if (c == 0xFF7F006E) {
                        v = 6;
                        System.out.println("portal found");
                    }
                    else if (c == 0xFF007F7F) {
                        v = 7;
                    }
                    else if (c == 0xFF00FF21) {
                        v = 10;
                    }
                    else if (c == 0xFF007F0E) {
                        v = 11;
                    }
                    else if ( c == 0xFFFF6A00) {
                        v = 12;
                    }
                    else if ( c == 0xFFB200FF) {
                        v = 13;
                    }
                    else if (c == 0xFFFFD800) {
                        v = 14;
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
        for (int x=0; x<w; x++) {
            for (int y=0; y<h; y++) {
                int target = mapArr[x][y];
                if (target == 1) {
                    Solid s = new Solid(x * Globals.solidWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, "solid");
                    solids.add(s);
                }
                else if (target == 2) {
                    Spike s = new Spike(x * Globals.solidWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, 0, "normal");
                    spikes.add(s);
                }
                else if (target == 3) {
                    Solid s = new Solid(x * Globals.slabWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, "slabUp");
                    solids.add(s);
                }
                else if (target == 4) {
                    Spike s = new Spike(x * Globals.solidWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, 0, "small");
                    spikes.add(s);
                }

                else if (target == 5) {
                    Spike s = new Spike(x * Globals.solidWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, 1, "normal");
                    spikes.add(s);
                }

                else if (target == 6) {
                    Spike s = new Spike(x * Globals.solidWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, 1, "small");
                    spikes.add(s);
                }
                else if (target == 7) {
                    Solid s = new Solid(x * Globals.slabWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, "slabDown");
                    solids.add(s);
                }
                else if (target == 10) {
                    Portal p = new Portal( x * Portal.width, Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, "ship" );
                    portals.add(p);
                    System.out.println("portal made");
                }
                else if (target == 11) {
                    Portal p = new Portal( x * Portal.width, Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, "cube" );
                    portals.add(p);
                    System.out.println("portal made");
                }
                else if (target == 12) {
                    Barrier b = new Barrier(x * Globals.slabWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, Globals.solidHeight, Globals.solidHeight);
                    barriers.add(b);
                }
                else if (target == 13) {
                    Pad p = new Pad(x * Globals.slabWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - 30, Globals.solidWidth, 30);
                    pads.add(p);
                }
                else if ( target == 14) {
                    Orb o = new Orb(x * Globals.slabWidth , Globals.floor - ((h-y-7) * Globals.solidHeight) - Globals.solidHeight, "yes" );
                    orbs.add(o);

                }
            }
        }


//        for (int i=0; i<w; i)
//        Solid s = new Solid ( x*50, y*50, "solid" );
    }

    public void drawLevel() {

    }

    public int[][] getMapArr() {
        return mapArr;
    }

    public int getWidth() {
        return w ;
    }

    public int getHeight() {
        return h ;
    }


    public ArrayList<Solid> getSolids() {
        return solids;
    }
//    public ArrayList<Slab> getSlabs() {
//        return slabs;
//    }
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
    public ArrayList<Portal> getPortals() {return portals;}
    public static ArrayList<Checkpoint> getCheckpoints() {return checkpoints;}
    public void asciiPrint() {
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                System.out.printf("%d", mapArr[x][y]);
            }
            System.out.println();
        }
    }

}
