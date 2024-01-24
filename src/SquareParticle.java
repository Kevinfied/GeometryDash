// SquareParticle.java
import java.awt.*;
public class SquareParticle { // a visual effect: s small square getting smaller and slowly fades out. Used for cube, ship, pad Daisy
    //coordinate and dimensions and angle or displacement
    double x, y, angle;
    double width, height;

    //vector and components
    double vx, vy, speed;

    //starting coordinate use to calculate the distance it had traveled
    double startX, startY;

    //color saturation
    int saturation = 255;
    //when reached maximum distance, remove it from the arraylist in game pannel that control all the particles
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

    //moves the particle
    public void move() {
        x+=vx;
        y+=vy;

        //the dimension gets smaller gradually
        width = width* 0.97;
        height = height * 0.97;

    }

    //draw it
    public void draw( Graphics g, int offsetX, int offsetY) {
        g.setColor( new Color(0, 239, 255, saturation));
        g.fillRect((int)x + offsetX, (int)y + offsetY, (int)width, (int) height);
        saturation = (int) (saturation * 0.87) ; //the color slowly fades out
    }

}
