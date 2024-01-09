import java.awt.*;
public class SquareParticle {
    double x, y, angle, vx, vy, speed;
    double startX, startY;
    int l;
    double maxdist = 20;
    public SquareParticle( double x, double y, double angle, int l, double speed) {
        this.x = x;
        this.startX = x;
        this.y= y;
        this.startY = y;
        this.l = l;
        this.angle = angle;
        this.speed = speed;
        this.vx = speed * Math.cos(angle);
        this.vy = speed * Math.sin(angle);
    }

    public void move() {
        x+=vx;
        y+=vy;


    }

    public void draw( Graphics g, int offsetX, int offsetY) {
        g.fillRect((int)x + offsetX, (int)y + offsetY, l, l);
    }
}
