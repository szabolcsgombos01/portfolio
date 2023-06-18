package utolsomiujsag2.snake;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SnakeGUI {
    private JFrame frame;
    private GameEngine gameArea;
    private JPanel panel;
    public HighScores HighScores;


    public SnakeGUI() {
        frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenuItem highscores = new JMenuItem("Highscores");
        JMenuItem newGame = new JMenuItem("NewGame");
        gameMenu.add(highscores);
        gameMenu.add(newGame);
        newGame.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.restart();
            }

        });
        highscores.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                panel = new JPanel();
                try{
                    HighScores = new HighScores(10);
                    JOptionPane.showMessageDialog(panel, HighScores.highscoresToString(), "Highscores",
                                JOptionPane.PLAIN_MESSAGE);
                                gameArea.restart();
                        } catch (SQLException ex) {
                    Logger.getLogger(SnakeGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
                
                        
                
                
                
                
                
                
            }

        );

        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);

        frame.setPreferredSize(new Dimension(513, 588));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
