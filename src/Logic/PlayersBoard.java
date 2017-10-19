package Logic;

import java.util.ArrayList;

public class PlayersBoard {

    private String[][] board;
    private int boardSize;

    public PlayersBoard(int i_BoardSize) {
        boardSize = i_BoardSize;
        createBoard();
    }

    public PlayersBoard(PlayersBoard toBeCloned) {
        boardSize = toBeCloned.getBoardSize();
        createBoard();
        for (int i=0 ; i<boardSize; i++){
            for (int j = 0; j<boardSize ; j++){
                String tmp = toBeCloned.getBoard()[i][j];
                board[i][j] = new String(tmp);
            }
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public String boardToString() {
        String res = new String();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                res = res.concat(board[i][j]);
            }
            res = res.concat("\n");
        }

        return res;
    }

    public void insertToBoard(Cell cell) {
        board[cell.getWhere().y][cell.getWhere().x] = cell.getSign();
    }

    private void createBoard() {
        board = new String[boardSize][boardSize];
    }

    public void insertMyShips(ArrayList<BoardComponent> myComp) {
        for (BoardComponent comp : myComp) {
            for (Cell cell : comp.getCells()) {
                insertToBoard(cell);
            }
        }
    }

    public String getStrAt(int col, int row) {
        return board[row][col];
    }
}
