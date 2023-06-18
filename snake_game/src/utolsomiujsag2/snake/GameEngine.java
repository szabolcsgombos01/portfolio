package utolsomiujsag2.snake;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameEngine extends JPanel {
    private final int FPS = 5;
    private final int SNAKE_Y = 300;
    private final int SNAKE_WIDTH = 25;
    private final int SNAKE_HEIGHT = 25;
    private final int SNAKE_MOVEMENT = 25;
    private int novekedes = 1;
    private Image background;
    private boolean paused = false;
    private int tableNum = 4;
    private Table table;
    private Snake snake;
    private Greensquare greensquare;
    private Snake fosnake;
    private Timer newFrameTimer;
    public int fosnakeXXXX;
    public int fosnakeYYYY;
    public int valtozo =0;
    public Databases database;
    
    public  GameEngine(){
        super();
        database = new Databases();
        background = new ImageIcon("data/images/background.png").getImage();

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVelx(-SNAKE_MOVEMENT);
                fosnake.setVely(0);
                
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVelx(-SNAKE_MOVEMENT);
                fosnake.setVely(0);
                
            }
        });

        /**
         * Jobbra mozgás
         */
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVelx(SNAKE_MOVEMENT);
                fosnake.setVely(0);
              
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVelx(SNAKE_MOVEMENT);
                fosnake.setVely(0);
                //snake.moveRIGHT();
            }
        });

        /**
         * Le mozgás
         */
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVely(SNAKE_MOVEMENT);
                fosnake.setVelx(0);
            
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVely(SNAKE_MOVEMENT);
                fosnake.setVelx(0);
         
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVely(-SNAKE_MOVEMENT);
                fosnake.setVelx(0);

            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                fosnake.setVely(-SNAKE_MOVEMENT);
                fosnake.setVelx(0);

            }
        });
        /**
         *Megállítás.
         */
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();

    }
    /**
     * Játék leállítás
     */
    public void restart() {
        try {
            table = new Table("data/levels/level0" + tableNum + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image snakeImage = new ImageIcon("data/images/snakehead.png").getImage();
        fosnake = new Snake(125, 25, SNAKE_WIDTH, SNAKE_HEIGHT, snakeImage);

    }


    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 513, 588, null);
        table.draw(grphcs);
        fosnake.draw(grphcs);
       
    }
    class NewFrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                fosnake.moveRIGHT();
                fosnake.moveLEFT();
                fosnake.moveUP();
                fosnake.moveDOWN();

                valtozo ++;
                if (table.collides(fosnake)) {
                    database.Add(novekedes-1);
                    restart();
                }
                if(table.collides2(fosnake))
                {
                    table.newfood();
                    novekedes++;

                }
                if(table.collides4(fosnake))
                {
                    database.Add(novekedes-1);
                    restart();
                }
                if (!fosnake.moveUPboolen()) {
                    database.Add(novekedes-1);
                    restart();
                }
                if (!fosnake.moveDOWNboolen()) {
                    database.Add(novekedes-1);
                    restart();
                }
                if (!fosnake.moveRIGHTboolen()) {
                    database.Add(novekedes-1);
                    restart();
                }
                if (!fosnake.moveLEFTboolen()) {
                    database.Add(novekedes-1);
                    restart();
                }

            }
           repaint();
            if(fosnake.jobbrament())
                {

                        table.koordcsere((fosnake.x-25), fosnake.y);
                        repaint();
                }
                if(fosnake.balrament())
                {
                        table.koordcsere((fosnake.x+25), fosnake.y);
                        repaint();
                }
                if(fosnake.felment())
                {

                        table.koordcsere(fosnake.x, (fosnake.y+25));
                        repaint();

                }
                if(fosnake.lement())
                {
                        table.koordcsere(fosnake.x, (fosnake.y-25));
                        repaint();
                }

        }

    }

    public int getX() {
        return fosnakeXXXX;
    }
    public int getY() {
        return fosnakeYYYY;
    }
    public Snake getFosnake()
    {
        return fosnake;
    }
    public int getNovekedes(){
        return novekedes;
    }
   
}
