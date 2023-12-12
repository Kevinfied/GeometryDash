public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    public void minus(Vector v) {
        x -= v.getX();
        y -= v.getY();
    }

    public void scale(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setY( int newY) { y = newY; }
    public void setX( int newX) { x = newX; }

}
