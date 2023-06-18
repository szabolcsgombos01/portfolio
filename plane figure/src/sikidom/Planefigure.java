package sikidom;

public abstract class Planefigure {

    private String category;
    private double X;
    private double Y;
    private double Length;

    public Planefigure(String category , double X , double Y , double Length)
    {
        this.category = category;
        this.X = X;
        this.Y = Y;
        this.Length = Length;
    }

    public String getCategory() {
        return category;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getLength() {
        return Length;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    public void setLength(double length) {
        Length = length;
    }

    @Override
    public  String toString()
    {
        return  "Plane figure{" + "category: " + category +", X kooridnata: " + X +", Y kooridnata: " + Y + ", oldal hossz: " + Length + '}';
    }
}
