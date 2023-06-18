package utolsomiujsag2.snake;

import java.awt.Image;

public class Snake extends Sprite {

    private double velx;
    private double vely;
    public Snake(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
    }

    public  void moveLEFT()
    {

        if(velx < 0)
        {

            vely = 0;
            x += velx;

        }
    }
    public  boolean balrament()
    {
        if(velx < 0)
        {
            return true;

        }
        return false;
    }
    public  void moveRIGHT()
    {
        if(velx > 0)
        {
            vely = 0;
            x += velx;

        }
    }
    public  boolean jobbrament()
    {
        if(velx > 0)
        {
            return true;

        }
        return false;
    }
    public  void moveUP()
    {
        if(vely < 0)
        {
            velx = 0;
            y += vely;

        }
    }
    public boolean felment()
    {
        if(vely < 0)
        {
            return true;

        }
        return false;
    }
    public  void moveDOWN()
    {
        if(vely > 0)
        {
            velx = 0;
            y += vely;

        }
    }
    public boolean lement()
    {
        if(vely > 0)
        {
            return true;
        }
        return false;
    }
    public boolean moveUPboolen()
    {

        if(y < 0)
        {
            return false;
        }
        return true;
    }
    public boolean moveDOWNboolen()
    {
        if(y> 500)
        {
            return false;
        }
        return true;
    }
    public boolean moveLEFTboolen()
    {
        if(x < 0)
        {
            return false;
        }
        return true;
    }
    public boolean moveRIGHTboolen()
    {
        if(x+ 25 > 500 )
        {
            return false;
        }
        return true;
    }

    public double getVelx() {
        return velx;
    }
    public  double getVely() { return vely;}

    public void setVelx(double velx) {
        this.velx = velx;
    }
    public void setVely(double vely)  {
        this.vely = vely;
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