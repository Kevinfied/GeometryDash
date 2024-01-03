import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class smallSpike {

    public static int width = Globals.gridWidth;

    public static int height = Globals.gridWidth / 2;

    private int orientation;

    private int x, y;

    // constructor
    public smallSpike(int x, int y, int orientation) {

        this.x = x;
        this.y = y;
        this.orientation = orientation;

    }

    // hitbox



}