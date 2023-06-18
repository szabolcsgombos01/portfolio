package utolsomiujsag2.snake;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import javax.swing.ImageIcon;
import java.io.*;

public class Table {
    /**
     * Mátrix leírás:
     * 0-as üres
     * 1-es stone
     * 2-es food
     * 3-as snake
     */
    private Food food;
    private final int FOOD_RADIUS = 25;
    public Greensquare greensquare;
    private final int STONE_WIDTH = 25;
    private final int STONE_HEIGHT = 25;
    public GameEngine gameEngine = null;
    private Image foodImage = new ImageIcon("data/images/food.jpg").getImage();
    private Image stoneImage =  new ImageIcon("data/images/stone.jpg").getImage();
    public int newfooddb = 0;
    public int valtozo = 0;
    public int vegleges = 0;
    ArrayList<Stone> stones;
    ArrayList<Greensquare> greensquares;

    public Table(String tablePath) throws IOException {
        loadtable(tablePath);
    }
    public void loadtable(String tablePath) throws FileNotFoundException, IOException
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader(tablePath));
        stones = new ArrayList<>();
        greensquares = new ArrayList<>();
        String line;
        int j = 0;
        while((line = br.readLine()) != null)
        {
            int i =0;
            for(char blockType : line.toCharArray())
            {
                if(blockType == '1')
                {
                    stones.add(new Stone(i * STONE_WIDTH, j * STONE_HEIGHT, STONE_WIDTH, STONE_HEIGHT, stoneImage));
                  
                }

                i++;
            }
            j++;
            food = new Food(175, 25, FOOD_RADIUS, FOOD_RADIUS, foodImage);
        }
        }
        catch(FileNotFoundException ex){
            System.out.println("Nem létezik a file!");
            System.exit(0);
                }
        
        
    }


    public void draw(Graphics g) {
        for (Stone stone : stones) {
            stone.draw(g);
        }
            food.draw(g);

            for (Greensquare greensquare : greensquares)
            {
                greensquare.draw(g);

            }


    }public void koordcsere(int x , int y)
    {

        if(greensquares.size() > 0) {
            for (int i = greensquares.size()-1; i>0 ; i--) {
                greensquares.set(i ,  greensquares.get(i-1));
                
            }
            Image snakeImage = new ImageIcon("data/images/snake.png").getImage();
            greensquares.set(0 , new Greensquare(x , y , 25 , 25, snakeImage));
            

        }
    }

    /**
     * Kígyó kő ütközés
     * @param snake
     * @return
     */
    public boolean collides(Snake snake) {
        Stone collidedWith = null;
        for (Stone stone : stones) {
            if (snake.collides(stone)) {
                collidedWith = stone;
                break;
            }
        }
        if (collidedWith != null) {
            stones.remove(collidedWith);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Snake alma ütközés + zöldnégyzet töltés
     * @param snake
     * @return
     */
    public boolean collides2(Snake snake) {
        if (snake.collides(food))
        {
            valtozo = 1;
            Image snakeImage = new ImageIcon("data/images/snake.png").getImage();
            Greensquare zoldnegyzetek = new Greensquare(food.x-25, food.y, 25, 25,snakeImage );
            greensquares.add(zoldnegyzetek);
            return true;
        }
        else {
            return false;
        }
    }



    public boolean collides3(Food food) {
        Stone collidedWith = null;
        for (Stone stone : stones) {
            if (food.collides(stone)) {
                collidedWith = stone;
                break;
            }
        }

        if (collidedWith != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Snake greensquares ütközés
     * @param
     * @return
     */
    public boolean collides4(Snake snake) {
        Greensquare collidedWith = null;
        for (Greensquare greensquare : greensquares) {
            if (snake.collides(greensquare)) {
                collidedWith = greensquare;
                break;
            }
        }
        if (collidedWith != null) {
            greensquares.remove(collidedWith);
            return true;
        } else {
            return false;
        }
    }
    /**
     * Új kaja beállítása
     */
    public void newfood()
    {
        newfooddb++;
        int xjo = 0;
        int yjo = 0;
        vegleges = 0;
        boolean veglegesbool = false;
        int resultX = 0;
        int resultY = 0;
        Random r = new Random();
        int lowx = 0;
        int highx =20;
        int lowy = 0;
        int highy = 20;

        while (veglegesbool == false)
        {
            resultX = r.nextInt(highx-lowx) + lowx;
            resultX = resultX*25;
            resultY = r.nextInt(highy-lowy) + lowy;
            resultY = resultY*25;
            for(Stone stone : stones)
            {
                if( resultX == stone.x && resultY == stone.y)
                {
                    vegleges ++;
                    break;
                    
                }

            }
            for(Greensquare greensquare : greensquares)
            {
                if(resultX == greensquare.x&&  resultY == greensquare.y)
                {
                    vegleges ++;
                    break;
                }
            }
            if(vegleges < 1)
            {
                veglegesbool = true;
                food = new Food(resultX, resultY, FOOD_RADIUS, FOOD_RADIUS, foodImage);
                break;
            }
            vegleges =0;
        }
        
        
        

    }

    public ArrayList<Greensquare> getGreensquares(){
        return greensquares;
    }

     public int getNewfooddb(){
        return newfooddb;
    }
}
