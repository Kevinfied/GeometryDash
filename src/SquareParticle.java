import java.awt.*;
public class SquareParticle {
    double x, y, angle, vx, vy, speed;
    double startX, startY;
    int saturation = 255;
    double l;
    double maxdist;
    public SquareParticle( double x, double y, double angle, double l, double speed, double maxdist) {
        this.x = x;
        this.startX = x;
        this.y= y;
        this.startY = y;
        this.l = l;
        this.angle = angle;
        this.speed = speed;
        this.vx = speed * Math.cos(angle);
        this.vy = speed * Math.sin(angle);
        this.maxdist = maxdist;
    }

    public void move() {
        x+=vx;
        y+=vy;
        l = l* 0.97;


    }

    public void draw( Graphics g, int offsetX, int offsetY) {
        g.setColor( new Color(0, 239, 255, saturation));
        g.fillRect((int)x + offsetX, (int)y + offsetY, (int)l, (int) l);
        saturation = (int) (saturation * 0.87) ;
    }
}
