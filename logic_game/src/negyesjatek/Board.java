/**
 * Név: Gombos Szabolcs Péter
 * Neptun: DJALET
 */

package negyesjatek;




import java.awt.Point;

/**
 *
 * @author pinter
 */
public class Board {

    private Field[][] board;
    private final int boardSize;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        board = new Field[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; ++i) {
            for (int j = 0; j < this.boardSize; ++j) {
                board[i][j] = new Field();
            }
        }
    }

    public Field get(int x, int y) {
        return board[x][y];
    }



    public int getBoardSize() {
        return boardSize;
    }

}
