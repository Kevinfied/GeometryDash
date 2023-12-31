//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Util {
    private BufferedImage pixelMap;
    private BufferedImage back;
    private int tileWidth;
    private int tileHeight;
    private HashMap<Integer, Image> tilePics = new HashMap();

    public Image loadImage(String name) {
        return (new ImageIcon(name)).getImage();
    }

    public static BufferedImage loadBuffImage(String name) {
        try {
            return ImageIO.read(new File(name));
        } catch (IOException var3) {
            System.out.println(var3);
            return null;
        }
    }

    public void loadHeader(String name) {
        try {
            Scanner inFile = new Scanner(new File(name));
            this.tileWidth = Integer.parseInt(inFile.nextLine());
            this.tileHeight = Integer.parseInt(inFile.nextLine());
            this.back = this.loadBuffImage(inFile.nextLine());
            this.pixelMap = this.loadBuffImage(inFile.nextLine());
            int numTile = Integer.parseInt(inFile.nextLine());

            for(int i = 0; i < numTile; ++i) {
                int col = Integer.parseInt(inFile.nextLine(), 16);
                this.tilePics.put(col, this.loadImage(inFile.nextLine()));
            }
        } catch (IOException var6) {
            System.out.println(var6);
        }

    }

    public void makeFull() {
        Graphics buffG = this.back.getGraphics();
        int wid = this.pixelMap.getWidth();
        int height = this.pixelMap.getHeight();

        for(int x = 0; x < wid; ++x) {
            for(int y = 0; y < height; ++y) {
                int col = this.pixelMap.getRGB(x, y);
                col &= 16777215;
                if (this.tilePics.containsKey(col)) {
                    Image tile = (Image)this.tilePics.get(col);
                    int offset = this.tileHeight - tile.getHeight((ImageObserver)null);
                    buffG.drawImage(tile, x * this.tileWidth, y * this.tileHeight + offset, (ImageObserver)null);
                }
            }
        }

    }

    public int getTile(int x, int y) {
        int col = this.pixelMap.getRGB(x / this.tileWidth, y / this.tileHeight);
        col &= 16777215;
        return col;
    }

    public Image getBackground() {
        return this.back;
    }

    public Util(String name) {
        this.loadHeader(name);
        this.makeFull();
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }


}
