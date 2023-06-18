
package utolsomiujsag2.snake;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Databases {

    
    
    public Databases(){
        
    }
    
    public void Add(int score){
        
            
        try {
            String Name = JOptionPane.showInputDialog("Enter your name");

            HighScores highScores = new HighScores(10);
            System.out.println(highScores.getHighScores());
            highScores.putHighScore(Name, score);
            System.out.println(highScores.getHighScores());
        } catch (SQLException ex) {
            Logger.getLogger(Databases.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    
}
