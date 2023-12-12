public class Vector {
    private int x;
    private int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector v) {
        x += v.x;
        y += v.y;
    }

    public void minus(Vector v) {
        x -= v.getX();
        y -= v.getY();
    }

    public void scale(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public int magnitude() {
        return (int) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setY( int newY) { y = newY; }
    public void setX( int newX) { x = newX; }

}
