package utolsomiujsag2.snake;
import java.awt.Image;
public class Stone extends Sprite{
    public Stone(int x, int y, int width, int height, Image image) {
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