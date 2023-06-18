package sikidom;

public class Point {
    protected double x;
    protected double y;

    void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    double distance(double z , double k) {
        double dx = Math.abs(x  - z);
        double dy = Math.abs(y  - k);
        return Math.sqrt(dx * dx + dy * dy);
    }
}
