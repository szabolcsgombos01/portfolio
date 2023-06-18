package utolsomiujsag2.snake;

import java.awt.*;

public class Greensquare extends Sprite{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;
    public Greensquare(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

}
