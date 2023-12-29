import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
    String map;
    private BufferedImage pic;
    private int w, h;
    ArrayList<Solid> solids;
    ArrayList<Slab> slabs;
    ArrayList<Spike> spikes;
    int[][] mapArr;


    // constructor
    public Level(String map) {
        this.map = map;
        pic = Util.loadBuffImage(map);
        w = pic.getWidth();
        h = pic.getHeight();
        System.out.printf("width: %d\n", w);
        System.out.printf("height: %d\n", h);
        solids = new ArrayList<Solid>();
        slabs = new ArrayList<Slab>();
        spikes = new ArrayList<Spike>();
        mapArr = new int[w][h];
    }


    public void loadMap(){
        /*
            Table:
            1 - Solids
            2 - Spike (upright)
            3 - Slabs
         */

        for(int x=0; x<w; x++){
            for(int y=0; y<h; y++){
                int c = pic.getRGB(x, y);
                int v=0;
                if (c==0xFF0026FF){
//                    solids[y][x] = new Solid( x*50, y*50, "solid");
                    v = 1;
                }
                else if (c == 0xFFFF0000) {
                    v = 2;
                }

                else if (c == 0xFF00FFFF) {
                    v = 3;
                }
//                if(c==0xFFFFFF00){
//                    v = 2;
//                }
                mapArr[x][y]=v;
            }
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


    public ArrayList<Solid> getSolids() {
        return solids;
    }
    public ArrayList<Slab> getSlabs() {
        return slabs;
    }
    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public void asciiPrint() {
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                System.out.printf("%d", mapArr[x][y]);
            }
            System.out.println();
        }
    }

}
