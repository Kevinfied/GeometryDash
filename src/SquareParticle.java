import java.awt.*;
public class SquareParticle {
    double x, y, angle, vx, vy, speed;
    double startX, startY;
    int saturation = 255;
    double width, height;
    double maxdist;
    public SquareParticle( double x, double y, double angle, double width, double height, double speed, double maxdist) {
        this.x = x;
        this.startX = x;
        this.y= y;
        this.startY = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.speed = speed;
        this.vx = speed * Math.cos(angle);
        this.vy = speed * Math.sin(angle);
        this.maxdist = maxdist;
    }

    public void move() {
        x+=vx;
        y+=vy;
        width = width* 0.97;
        height = height * 0.97;



    }

    public void draw( Graphics g, int offsetX, int offsetY) {
        g.setColor( new Color(0, 239, 255, saturation));
        g.fillRect((int)x + offsetX, (int)y + offsetY, (int)width, (int) height);
        saturation = (int) (saturation * 0.87) ;
    }
}
