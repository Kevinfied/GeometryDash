import java.awt.image.BufferedImage;


public class Level {
    String map;
    private BufferedImage pic;
    private int w, h;
    Solid[][] solids;
    int[][] mapArr;
    // constructor
    public Level(String map) {
        this.map = map;
        pic = Util.loadBuffImage(map);
        w = pic.getWidth();
        h = pic.getHeight();
        System.out.printf("width: %d\n", w);
        System.out.printf("height: %d\n", h);
        solids = new Solid[w][h];
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

    public void makeMap(int[][] level, int w, int h) {

        Solid[][] solids = new Solid[w][h];


//        for (int i=0; i<w; i)
//        Solid s = new Solid ( x*50, y*50, "solid" );
    }

    public void drawLevel() {

    }

    public int[][] getMapArr() {
        return mapArr;
    }


    public Solid[][] getSolids() {
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
