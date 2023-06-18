/**
 * Név: Gombos Szabolcs Péter
 * Neptun: DJALET
 */
package negyesjatek;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class BoardGUI {

    private JButton[][] buttons;
    private Board board;
    private JPanel boardPanel;
    private JLabel PlayerColorLabel;





    private int szamlalo = 0;
    private int firstpalyer;
    private int secondplayer;
    private int celladb  ;




    public BoardGUI(int boardSize) {
        board = new Board(boardSize);
        boardPanel = new JPanel();

        celladb = board.getBoardSize()*board.getBoardSize();
        boardPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        buttons = new JButton[board.getBoardSize()][board.getBoardSize()];
        PlayerColorLabel = new JLabel("Az első játékos: PIROS , második játékos: KÉK");
        for (int i = 0; i < board.getBoardSize(); ++i)
        {
            for (int j = 0; j < board.getBoardSize(); ++j)
            {
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(i, j));
                button.setPreferredSize(new Dimension(80, 40));
                buttons[i][j] = button;
                boardPanel.add(button);
                Field field = board.get(i,j);
                    field.setNumber(0);
                    button.setText("0");

            }
        }

    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }
    public JLabel getPlayerColorLabel()
    {
        return PlayerColorLabel;
    }
    class ButtonListener implements ActionListener {
        private int x, y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            szamlalo++;
            Update(x , y);
            System.out.println("A cella értéke: " + board.get(x, y).getNumber());
            System.out.println("A koordináták: (x: " + x + " y: " +  y + ")");
            System.out.println("Kattinások száma: " +szamlalo);
            X_Y_Elhelyzhedes(x , y);
            Counter();
            Kiiratas();
            System.out.println("Első játékos: " + firstpalyer);
            System.out.println("Második játékos: " + secondplayer);
            refresh(x, y);


    }

        /**
         * Refresheli a Boardot
         * @param x koordináta
         * @param y koordináta
         */
    public void refresh(int x, int y) {
        JButton button = buttons[x][y];
        Field field = board.get(x, y);

    }}

    /**
     * A cellák mértetét növeli meg
     * @param X
     * @param Y
     */
    public void Update(Integer X , Integer Y)
    {
        if(board.get(X , Y).getNumber() >= 4)
        {

        }
        else
        {
            int newnumber = (board.get(X , Y).getNumber() +1);
            buttons[X][Y].setText(String.valueOf(newnumber));
            board.get(X, Y).setNumber(newnumber);
        }
    }

    /**
     * Jobb oldali cellaérték növelése
     * @param x koordináta
     * @param y koordináta
     */
    public void Jobbra( Integer x ,Integer y)
    {
        Update(x  , y+1 );
    }

    /**
     * Bal oldali cellaérték növelése
     * @param x koordináta
     * @param y koordináta
     */
    public void Balra( Integer x ,Integer y)
    {
        Update(x  , y-1 );
    }


    /**
     * Felső cellaérték növelése
     * @param x koordináta
     * @param y koordináta
     */
    public void Fel( Integer x ,Integer y)
    {
        Update(x -1 , y);
    }


    /**
     * Alsó cellaérték növelése
     * @param x koordináta
     * @param y koordináta
     */
    public void Le( Integer x ,Integer y)
    {
        Update(x +1 , y );
    }


    /**
     * Az x y elhelyezkedését figyeli
     * @param x koordináta
     * @param y koordináta
     */
    public void X_Y_Elhelyzhedes(int x , int y)
    {
        /**
         * Mikor a 0 , 0 sarokba van kattintás
         */
        if(x == 0 && y == 0)
        {
            Jobbra(x , y);
            Le(x ,y );
        }
        /**
         * Mikor az első sor utolsó cellájában történik kattintás
         */
        else if(x == 0 && y == board.getBoardSize() -1)
        {
            Balra(x , y);
            Le(x , y);
        }
        /**
         * Mikor első oszlop utolso sorban történik kattintás
         */
        else if(x == board.getBoardSize() -1 && y == 0)
        {
            Fel(x , y);
            Jobbra(x ,y);
        }
        /**
         * Mikor utolsó sor utolsó oszlopban történik kattintás
         */
        else if( x == board.getBoardSize()-1 && y == board.getBoardSize() -1)
        {
            Balra(x , y);
            Fel(x , y);
        }
        /**
         * Mikor első sorban történik kattintás
         */
        else if(x == 0)
        {
            Jobbra(x , y);
            Le(x ,y );
            Balra(x , y);
        }
        /**
         * Mikor utolsó sorban történik kattintás
         */
        else if(x == board.getBoardSize() -1)
        {
            Jobbra(x , y);
            Fel(x ,y );
            Balra(x , y);
        }
        /**
         * Mikor első oszlopban történik kattintás
         */
        else if(y == 0)
        {
            Jobbra(x , y);
            Fel(x ,y );
            Le(x , y);
        }
        /**
         * Mikor utolsó oszlopban történik kattintás
         */
        else if(y == board.getBoardSize() -1)
        {
            Le(x , y);
            Fel(x ,y );
            Balra(x , y);
        }
        /**
         * Középső elemek
         */
        else
        {
            Le(x , y);
            Fel(x ,y );
            Balra(x , y);
            Jobbra(x , y);
        }
    }

    /**
     * A játék végén kiírat.
     */
    public void Kiiratas()
    {
        if((celladb - (secondplayer+firstpalyer)) == 0)
        {
            if(firstpalyer > secondplayer)
            {
                JOptionPane.showMessageDialog(boardPanel, "Első játékos nyert", "Congrats!",
                        JOptionPane.PLAIN_MESSAGE);
                reset();
            }
            else
            {
                JOptionPane.showMessageDialog(boardPanel, "Második játékos nyert", "Congrats!",
                        JOptionPane.PLAIN_MESSAGE);
                reset();
            }
        }
    }


    /**
     * Játékot alaphelyzetbe állítja.
     * Visszaállítjuk a cellák számát , színét , kiosztását.
     */
    public void reset(){
        for(int i = 0 ; i< board.getBoardSize() ; i++  )
        {
            for (int j = 0 ; j<  board.getBoardSize() ; j++)
            {
                buttons[i][j].setEnabled(true);
                board.get(i , j).setNumber(0);
                buttons[i][j].setText("0");
                buttons[i][j].setBackground(new java.awt.Color(255, 255, 255));
            }
        }
        szamlalo = 0;
        firstpalyer = 0;
        secondplayer = 0;
    }

    /**
     * A játkéosok pontjait gyűjti.
     */
    public void Counter()
    {
        for (int i= 0; i< board.getBoardSize()  ; i++)
        {
            for (int j = 0; j < board.getBoardSize()  ; j++)
            {
                if(board.get(i, j).getNumber() == 4)
                {
                    /**
                     * Második játékos
                     */
                    if(szamlalo % 2 == 0 )
                    {
                        //buttons[i][j].setBackground(color.white);
                        buttons[i][j].setBackground(new java.awt.Color(84, 209, 241));
                        secondplayer++;
                    }
                    /**
                     * Első játékos
                     */
                    else
                    {
                        buttons[i][j].setBackground(new java.awt.Color(255, 0, 0));
                        firstpalyer++;
                    }
                    buttons[i][j].setEnabled(false);
                    board.get(i , j).setNumber(5);
                    buttons[i][j].setText("VÉGE");

                }

            }
        }
    }
}