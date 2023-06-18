/**
 * Név: Gombos Szabolcs Péter
 * Neptun: DJALET
 */
package negyesjatek;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Tomi
 */
public class NegyesjatekGUI {
    private JFrame frame;
    private BoardGUI boardGUI;

    private final int INITIAL_BOARD_SIZE = 5;

    public NegyesjatekGUI() {
        frame = new JFrame("4-es játék");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE);

        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.NORTH);


        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);
        int[] boardSizes = new int[]{3, 5, 7};
        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem((boardSize) + "x" + (boardSize));
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    frame.getContentPane().remove(boardGUI.getPlayerColorLabel());
                    boardGUI = new BoardGUI(boardSize);
                    frame.getContentPane().add(boardGUI.getPlayerColorLabel() , BorderLayout.NORTH);
                    frame.getContentPane().add(boardGUI.getBoardPanel(),
                            BorderLayout.CENTER);

                    frame.pack();
                }
            });
        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

}
