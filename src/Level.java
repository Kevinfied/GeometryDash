import java.awt.image.BufferedImage;
import java.util.*;

public class Level {
    String map;
    private BufferedImage pic;
    private int w, h;
    ArrayList<Solid> solids;
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
        mapArr = new int[w][h];
    }


    public void loadMap(){
        /*
            Table:
            1 - Solids
            2 - 
         */

        for(int x=0; x<w; x++){
            for(int y=0; y<h; y++){
                int c = pic.getRGB(x, y);
                int v=0;
                if (c==0xFF0026FF){
//                    solids[y][x] = new Solid( x*50, y*50, "solid");
                    v = 1;
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
                if (mapArr[x][y] == 1) {
                    Solid s = new Solid(x*50, Globals.floor - ((h-y-7) * 50) - 50, "solid");
                    solids.add(s);
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

    public void asciiPrint() {
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                System.out.printf("%d", mapArr[x][y]);
            }
            System.out.println();
        }
    }

}
