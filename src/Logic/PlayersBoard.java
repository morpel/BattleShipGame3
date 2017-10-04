package Logic;

import java.util.ArrayList;

public class PlayersBoard {

    private String[][] board;
    private int boardSize;
    private int overallBoardSize;

    public PlayersBoard(int i_BoardSize) {
        boardSize = i_BoardSize;
        overallBoardSize = (boardSize + 1) * 2;
        createBoard();
    }

    public PlayersBoard(PlayersBoard toBeCloned) {
        boardSize = toBeCloned.getBoardSize();
        overallBoardSize = (boardSize + 1) * 2;
        createBoard();
        for (int i=0 ; i<overallBoardSize ; i++){
            for (int j = 0; j<overallBoardSize ; j++){
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

        for (int i = 0; i < overallBoardSize; i++) {
            for (int j = 0; j < overallBoardSize; j++) {
                res = res.concat(board[i][j]);
            }
            res = res.concat("\n");
        }

        return res;
    }

    public void insertToBoard(Cell cell) {
        board[2 * cell.getWhere().y][2 * cell.getWhere().x] = cell.getSign();
    }

    private void createBoard() {
        board = new String[overallBoardSize][overallBoardSize];
        createHeaders();
        createLinesAndCols();
    }

    private void createLinesAndCols() {
        for (int i = 2; i < (boardSize + 1) * 2; i = i + 2) {
            for (int j = 2; j < (boardSize + 1) * 2; j = j + 2) {
                board[i][j] = " ";
                board[i][j + 1] = "|";
            }
        }
        for (int i = 3; i < (boardSize + 1) * 2; i = i + 2) {
            for (int j = 2; j < (boardSize + 1) * 2; j++) {
                board[i][j] = "-";
            }
        }
    }

    public void insertMyShips(ArrayList<BoardComponent> myComp) {
        for (BoardComponent comp : myComp) {
            for (Cell cell : comp.getCells()) {
                insertToBoard(cell);
            }
        }
    }

    private void createHeaders() {
        int indexer = 0;
        board[0][0] = "  ";
        board[1][0] = "--";
        board[0][1] = "|";
        board[1][1] = "*";
        for (int i = 2; i < (boardSize + 1) * 2; i = i + 2) {
            board[0][i] = String.format("%c", (char) ('A' + indexer));
            board[0][i + 1] = "|";
            board[1][i] = "-";
            board[1][i + 1] = "|";
            board[i][0] = String.format("%d", indexer + 1);
            if (indexer < 9)
                board[i][0] = board[i][0].concat(" ");
            board[i + 1][0] = "--";
            board[i][1] = "|";
            board[i + 1][1] = "|";
            indexer++;
        }
    }

    public String getStrAt(int col, int row) {
        return board[row * 2][col * 2];
    }
}
