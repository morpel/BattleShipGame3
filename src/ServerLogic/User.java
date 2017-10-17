package ServerLogic;

public class User implements Comparable{
    private String name;
    private Game myCurrrentGame;
    private Game currentGame;

    public User(String i_Name) {
        name = i_Name;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((User) o).name);
    }

    public String getName() {
        return name;
    }

    public void setCurrentGame(Game game){
        myCurrrentGame = game;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public String[][] getShipsBoard() {
        return currentGame.getLogic().getCurrentPlayer().getMyShipBoard().getBoard();
    }

    public String[][] getAttackingBoard() {
        return currentGame.getLogic().getCurrentPlayer().getMyAttackingBoard().getBoard();
    }
}
